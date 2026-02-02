package com.taeyoung.recipe.recipe_backend.global.exception;

public class AlreadyLinkedAccountException extends RuntimeException {
    public AlreadyLinkedAccountException() {
        super("이미 연동된 계정입니다.");
    }
}