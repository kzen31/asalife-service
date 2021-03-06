package com.asaproject.asalife.domains.models.responses;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class ValidationErrorResponse<T> extends ErrorResponse {
    private final HashMap<String, T> fields;

    public ValidationErrorResponse(HashMap<String, T> fields) {
        super("Validation Error");
        this.fields = fields;
    }
}
