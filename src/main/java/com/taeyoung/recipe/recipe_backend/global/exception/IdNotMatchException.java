package com.taeyoung.recipe.recipe_backend.global.exception;

public class IdNotMatchException extends RuntimeException {
    public IdNotMatchException(String message) {
        super(message);
    }
}