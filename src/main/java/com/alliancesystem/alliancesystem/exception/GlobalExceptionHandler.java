package com.alliancesystem.alliancesystem.exception;

import com.alliancesystem.alliancesystem.exception.alliancerelated.*;
import com.alliancesystem.alliancesystem.exception.liferelated.*;
import com.alliancesystem.alliancesystem.exception.userrelated.AuthenticatedUserNotFoundException;
import com.alliancesystem.alliancesystem.exception.userrelated.UserNotEnoughCoinException;
import com.alliancesystem.alliancesystem.exception.userrelated.UserNotFoundException;
import com.alliancesystem.alliancesystem.exception.userrelated.UsernameAlreadyExistsException;
import com.alliancesystem.alliancesystem.util.CustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticatedUserNotFoundException.class)
    public ResponseEntity<?> authenticatedUserNotFoundExceptionHandler(AuthenticatedUserNotFoundException exception) {
        return CustomErrorResponse.sendError(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class, GiftedLifeNotFoundException.class,
            LifeRequestNotFoundException.class, AllianceNotFoundException.class})
    public ResponseEntity<?> notFoundExceptionHandler(RuntimeException exception) {
        return CustomErrorResponse.sendError(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class, InsufficientTimeElapsedException.class,
            SelfGiftException.class, UserAlreadyInSendersException.class,
            UserMaximumGiftedLifeException.class, UserMaximumLifeException.class,
            AllianceHasMaximumMemberException.class, AllianceInvitationRequiredException.class,
            AllianceLeaderLeaveException.class, AllianceNameAlreadyExistsException.class,
            AllianceNoInvitationRequiredException.class, UserAlreadyHasAllianceException.class,
            UserHasNoAllianceException.class, UserHasNoJoinRequestException.class,
            UserLevelInsufficientException.class, UserNotInSameAllianceException.class,
            UserOnBlockedListException.class, UserSelfRemoveAllianceException.class,
            UserNotEnoughCoinException.class})
    public ResponseEntity<?> badRequestExceptionHandler(RuntimeException exception) {
        return CustomErrorResponse.sendError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserInvalidAuthorizationException.class)
    public ResponseEntity<?> forbiddenExceptionHandler(RuntimeException exception) {
        return CustomErrorResponse.sendError(HttpStatus.FORBIDDEN, exception.getMessage());
    }


}
