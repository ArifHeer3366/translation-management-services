package com.example.translation.service.serviceImpl;

import com.example.translation.dto.TranslationCreateDTO;
import com.example.translation.dto.TranslationRequest;
import com.example.translation.dto.TranslationResponse;
import com.example.translation.entity.Tag;
import com.example.translation.entity.Translation;
import com.example.translation.entity.TranslationKey;
import com.example.translation.entity.TranslationValue;
import com.example.translation.repository.TagRepository;
import com.example.translation.repository.TranslationKeyRepository;
import com.example.translation.repository.TranslationRepository;
import com.example.translation.repository.TranslationValueRepository;
import com.example.translation.service.ExportService;
import com.example.translation.service.TranslationService;
import com.example.translation.util.TranslationValueSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final TranslationKeyRepository keyRepo;
    private final TranslationValueRepository valueRepo;
    private final ExportService exportService;
    private final TagRepository tagRepository;
    private final TranslationRepository translationRepository;

    public TranslationServiceImpl(TranslationKeyRepository keyRepo,
                                  TranslationValueRepository valueRepo,
                                  ExportService exportService,
                                  TagRepository tagRepository,
                                  TranslationRepository translationRepository) {
        this.keyRepo = keyRepo;
        this.valueRepo = valueRepo;
        this.exportService = exportService;
        this.tagRepository = tagRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    @Transactional
    public TranslationValue createOrUpdate(TranslationRequest request) {
        // Find or create the key
        TranslationKey key = keyRepo.findByNamespaceAndKey(
                request.getNamespace(), request.getKey()
        ).orElseGet(() -> {
            TranslationKey k = new TranslationKey();
            k.setNamespace(request.getNamespace());
            k.setKey(request.getKey());
            return keyRepo.save(k);
        });

        // Handle tags
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            Set<Tag> tagSet = new HashSet<>();
            for (String tagName : request.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag() {{ setName(tagName); }}));
                tagSet.add(tag);
            }
            key.setTags(tagSet);
            keyRepo.save(key); // persist updated tags
        }

        // Find existing value
        TranslationValue value = valueRepo.findByKeyIdAndLocale(key.getId(), request.getLocale())
                .orElseGet(() -> {
                    TranslationValue v = new TranslationValue();
                    v.setKey(key);
                    v.setLocale(request.getLocale());
                    return v;
                });

        // Update content
        value.setContent(request.getContent());
        value.setUpdatedAt(Instant.now());

        TranslationValue saved = valueRepo.save(value);

        // Evict cache for this locale + namespace
        exportService.evictCacheFor(request.getLocale(), request.getNamespace());

        return saved;
    }

    public Page<TranslationValue> search(String namespace, String key, String locale, String q,
                                         int page, int size) {
        Specification<TranslationValue> spec = TranslationValueSpecification.filter(namespace, key, locale, q);
        return valueRepo.findAll(spec, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public TranslationResponse createTranslation(TranslationCreateDTO dto) {
        Translation translation = getOrCreateTranslation(dto);
        translation.setTags(getOrCreateTags(dto));
        TranslationKey translationKey = getOrCreateTranslationKey(dto);

        updateTranslationValues(translation, dto, translationKey);

        Translation savedTranslation = translationRepository.save(translation);
        return mapToDTO(savedTranslation);
    }

// ----------------- helper methods -----------------

    private Translation getOrCreateTranslation(TranslationCreateDTO dto) {
        return translationRepository.findByNamespaceAndKey(dto.getNamespace(), dto.getKey())
                .orElseGet(() -> {
                    Translation t = new Translation();
                    t.setNamespace(dto.getNamespace());
                    t.setKey(dto.getKey());
                    return t;
                });
    }

    private Set<Tag> getOrCreateTags(TranslationCreateDTO dto) {
        return dto.getTags().stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build())))
                .collect(Collectors.toSet());
    }

    private TranslationKey getOrCreateTranslationKey(TranslationCreateDTO dto) {
        return keyRepo.findByNamespaceAndKey(dto.getNamespace(), dto.getKey())
                .orElseGet(() -> keyRepo.save(
                        TranslationKey.builder()
                                .namespace(dto.getNamespace())
                                .key(dto.getKey())
                                .build()
                ));
    }

    private void updateTranslationValues(Translation translation, TranslationCreateDTO dto, TranslationKey key) {
        if (translation.getValues() == null) {
            translation.setValues(new HashSet<>());
        } else {
            translation.getValues().clear();
        }

        for (Map.Entry<String, String> entry : dto.getValues().entrySet()) {
            TranslationValue value = TranslationValue.builder()
                    .locale(entry.getKey())
                    .content(entry.getValue())
                    .translation(translation)
                    .key(key)
                    .build();
            translation.getValues().add(value);
        }
    }

    @Override
    public List<TranslationResponse> getAllTranslations() {
        return translationRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private TranslationResponse mapToDTO(Translation translation) {
        Map<String, String> valuesMap = translation.getValues().stream()
                .collect(Collectors.toMap(TranslationValue::getLocale, TranslationValue::getContent));
        Set<String> tags = translation.getTags().stream().map(Tag::getName).collect(Collectors.toSet());

        return new TranslationResponse(
                translation.getId(),
                translation.getNamespace(),
                translation.getKey(),
                tags,
                valuesMap
        );
    }
}

