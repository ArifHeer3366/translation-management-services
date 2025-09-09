package com.example.translation.service.serviceImpl;

import com.example.translation.dto.ExportDto;
import com.example.translation.repository.TranslationValueRepository;
import com.example.translation.service.ExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {

    private final TranslationValueRepository valueRepo;
    private final ObjectMapper mapper;
    private final CacheManager cacheManager;

    public ExportServiceImpl(TranslationValueRepository valueRepo,
                             ObjectMapper mapper,
                             CacheManager cacheManager) {
        this.valueRepo = valueRepo;
        this.mapper = mapper;
        this.cacheManager = cacheManager;
    }


    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Map<String, String>>> exportLocaleJson(
            String locale, String namespace, String ifNoneMatch) {

        String ns = (namespace == null || namespace.isEmpty() || namespace.equals("*")) ? null : namespace;
        String cacheKey = (ns == null ? "*" : ns) + ":" + locale;
        Cache cache = cacheManager.getCache("export-json");

        // Check cached ETag
        String etag = Optional.ofNullable(cache.get(cacheKey + ":etag", String.class)).orElse(null);
        if (etag != null && etag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(etag).build();
        }

        // Fetch all rows into list
        List<ExportDto> all = valueRepo.streamByLocale(locale, ns);

        // Transform into nested map
        Map<String, Map<String, String>> namespaces = new HashMap<>();
        for (ExportDto dto : all) {
            namespaces.computeIfAbsent(dto.getNamespace(), k -> new LinkedHashMap<>())
                    .put(dto.getKey(), dto.getContent());
        }

        // Generate and cache new ETag
        String newEtag = UUID.randomUUID().toString();
        if (cache != null) {
            cache.put(cacheKey + ":etag", newEtag);
        }

        return ResponseEntity.ok()
                .eTag(newEtag)
                .body(namespaces);
    }

    @Override
    public void evictCacheFor(String locale, String namespace) {
        String cacheKey = (namespace == null ? "*" : namespace) + ":" + locale;
        Cache cache = cacheManager.getCache("export-json");
        if (cache != null) {
            cache.evict(cacheKey + ":etag");
        }
    }

}