package com.taeyoung.recipe.recipe_backend.global.exception;

public class AlreadyUnlinkedAccountException extends RuntimeException {
  public AlreadyUnlinkedAccountException() {
    super("이미 연동 해제된 계정입니다.");
  }
}