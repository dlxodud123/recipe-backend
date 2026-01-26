package com.taeyoung.recipe.recipe_backend.global.exception;

public class DuplicateRecipeItemException extends RuntimeException {
    public DuplicateRecipeItemException(String message) {
        super(message);
    }
}
