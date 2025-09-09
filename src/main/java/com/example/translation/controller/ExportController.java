package com.example.translation.controller;

import com.example.translation.service.ExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/{locale}")
    public ResponseEntity<Map<String, Map<String, String>>> export(
            @PathVariable String locale,
            @RequestParam(required = false) String namespace,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch
    ) {
        return exportService.exportLocaleJson(locale, namespace,ifNoneMatch);
    }
}

