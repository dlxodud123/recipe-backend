package com.taeyoung.recipe.recipe_backend.global.exception;

public class DuplicateRecipeTitleException extends RuntimeException {
    public DuplicateRecipeTitleException(String message) {
        super(message);
    }
}
