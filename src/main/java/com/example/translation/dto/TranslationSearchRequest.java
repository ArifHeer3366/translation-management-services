package com.example.translation.dto;

import java.util.Set;

public record TranslationSearchRequest(
        String namespace,
        String key,
        String locale,
        String q,
        Set<String> tags,
        int page,
        int size
) {}
