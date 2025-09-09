package com.example.translation.dto;

import java.util.Map;
import java.util.Set;

public record TranslationUpdateRequest(
        String namespace,
        String key,
        Map<String, String> values,
        Set<String> tags
) {}
