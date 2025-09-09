package com.example.translation.config;

import com.example.translation.dto.TranslationCreateDTO;
import com.example.translation.service.TranslationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class TranslationDataLoader {

    private final TranslationService translationService;

    public TranslationDataLoader(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostConstruct
    public void loadTestData() {
        for (int i = 1; i <= 100000; i++) {
            TranslationCreateDTO dto = new TranslationCreateDTO();
            dto.setNamespace("ns" + (i % 100));
            dto.setKey("key" + i);
            dto.setTags(Set.of("tag" + (i % 10)));
            dto.setValues(Map.of("en", "Hello " + i, "fr", "Bonjour " + i, "es", "Hola " + i));

            translationService.createTranslation(dto);
        }
    }
}
