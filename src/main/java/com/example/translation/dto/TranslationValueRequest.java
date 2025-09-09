package com.example.translation.dto;

import jakarta.validation.constraints.NotBlank;

public class TranslationValueRequest {

    @NotBlank
    private String locale;

    @NotBlank
    private String value;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
