package com.taeyoung.recipe.recipe_backend.global.exception;

import org.springframework.security.core.AuthenticationException;

public class EmailNotMatchException extends AuthenticationException {
    public EmailNotMatchException(String msg) {
        super(msg);
    }
}