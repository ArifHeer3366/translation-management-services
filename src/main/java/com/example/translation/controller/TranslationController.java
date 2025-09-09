package com.example.translation.controller;

import com.example.translation.dto.TranslationRequest;
import com.example.translation.dto.TranslationResponse;
import com.example.translation.entity.TranslationValue;
import com.example.translation.service.TranslationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public ResponseEntity<TranslationValue> createOrUpdate(
            @Valid @RequestBody TranslationRequest request
    ) {
        TranslationValue saved = translationService.createOrUpdate(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TranslationValue>> searchTranslations(
            @RequestParam(required = false) String namespace,
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<TranslationValue> results = translationService.search(namespace, key, locale, q, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping
    public ResponseEntity<List<TranslationResponse>> getAll() {
        return ResponseEntity.ok(translationService.getAllTranslations());
    }

    @GetMapping("/export")
    public ResponseEntity<List<TranslationResponse>> exportJson() {
        return ResponseEntity.ok(translationService.getAllTranslations());
    }

}

