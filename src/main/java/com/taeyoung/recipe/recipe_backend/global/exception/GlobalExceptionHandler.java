package com.taeyoung.recipe.recipe_backend.global.exception;

import com.taeyoung.recipe.recipe_backend.dto.exception.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 NOT FOUND: 단일 조회 실패
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto("ENTITY_NOT_FOUND", e.getMessage()));
    }

    // 400 BAD REQUEST: 잘못된 파라미터
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("INVALID_PARAMETER", e.getMessage()));
    }

    // 409 CONFLICT: DB 제약 조건 위반 / 외래키 제약 조건(유니크, NOT NULL)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrity(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto("DATA_INTEGRITY_VIOLATION", e.getMessage()));
    }

    // 500 INTERNAL SERVER ERROR: 기타 런타임 예외
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntime(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto("INTERNAL_SERVER_ERROR", e.getMessage()));
    }

    // 401 UNAUTHORIZED: username 또는 password가 일치하지 않을 때(로그인 전용(Authentication), 암호화된 password)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticationException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDto("INVALID_CREDENTIALS", e.getMessage()));
    }

    // 401 UNAUTHORIZED: 이메일 검증(.equals(), Authentication)
    @ExceptionHandler(EmailNotMatchException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailNotMatch(EmailNotMatchException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDto("EMAIL_NOT_MATCH", e.getMessage()));
    }

    // 403 FORBIDDEN: 작성자가 아닌 사용자가 수정 또는 삭제를 시도할 때 발생(.equals())
    @ExceptionHandler(IdNotMatchException.class)
    public ResponseEntity<ErrorResponseDto> handleIdNotMatch(IdNotMatchException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDto("FORBIDDEN", e.getMessage()));
    }

    // 409 CONFLICT: 이미 사용 중인 username일 때 발생(exists)
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUsername(DuplicateUsernameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto("DUPLICATE_USERNAME", e.getMessage()));
    }

    // 409 CONFLICT: 이미 사용 중인 email일 때 발생(exists)
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateEmail(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto("DUPLICATE_EMAIL", e.getMessage()));
    }

    // 409 CONFLICT: 레시피 제목 중복
    @ExceptionHandler(DuplicateRecipeTitleException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateRecipeTitle(DuplicateRecipeTitleException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto("DUPLICATE_RECIPE_TITLE", e.getMessage()));
    }

    // 409 CONFLICT: 이미 다른 계정과 연동되어 있을 때 발생
    @ExceptionHandler(AlreadyLinkedAccountException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyLinked(AlreadyLinkedAccountException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto("ALREADY_LINKED", e.getMessage()));
    }

    // 409 CONFLICT: 이미 연동 해제된 계정을 다시 해제하려고 할 때 발생
    @ExceptionHandler(AlreadyUnlinkedAccountException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyUnlinked(AlreadyUnlinkedAccountException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto("ALREADY_UNLINKED", e.getMessage()));
    }
}