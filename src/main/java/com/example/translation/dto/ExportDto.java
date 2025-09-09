package com.example.translation.dto;

public class ExportDto {
    private final String namespace;
    private final String key;
    private final String locale;
    private final String content;

    public ExportDto(String namespace, String key, String locale, String content) {
        this.namespace = namespace;
        this.key = key;
        this.locale = locale;
        this.content = content;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getKey() {
        return key;
    }

    public String getLocale() {
        return locale;
    }

    public String getContent() {
        return content;
    }
}
