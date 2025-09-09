package com.example.translation.service;

import io.micrometer.common.lang.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Map;

public interface ExportService {
    ResponseEntity<Map<String, Map<String, String>>> exportLocaleJson(
            String locale, String namespace,String ifNoneMatch);
    void evictCacheFor(String locale, @Nullable String namespace);
}
