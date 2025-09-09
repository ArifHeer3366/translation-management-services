package com.example.translation.service;

import com.example.translation.dto.TranslationCreateDTO;
import com.example.translation.dto.TranslationRequest;
import com.example.translation.dto.TranslationResponse;
import com.example.translation.entity.TranslationValue;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TranslationService {
    TranslationValue createOrUpdate(TranslationRequest req);

    Page<TranslationValue> search(String namespace, String key, String locale, String q,
                                  int page, int size);

    List<TranslationResponse> getAllTranslations();

    TranslationResponse createTranslation(TranslationCreateDTO dto);
}
