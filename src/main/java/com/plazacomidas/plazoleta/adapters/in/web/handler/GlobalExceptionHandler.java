package com.plazacomidas.plazoleta.adapters.in.web.handler;

import com.plazacomidas.plazoleta.adapters.in.web.dto.ErrorResponseDto;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.domain.exception.ApiError;
import com.plazacomidas.plazoleta.domain.exception.InvalidRestaurantException;
import com.plazacomidas.plazoleta.domain.exception.RestaurantException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.List;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final List<String> CUSTOM_MESSAGES = List.of(
            RestaurantConstants.MSG_USER_NOT_OWNER,
            RestaurantConstants.MSG_USER_VALIDATION_FAILED,
            RestaurantConstants.MSG_USER_NOT_OWNER,
            RestaurantConstants.MSG_USER_VALIDATION_FAILED
    );

    @ExceptionHandler(RestaurantException.class)
    public ResponseEntity<ErrorResponseDto> handleRestaurantException(
            RestaurantException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.INVALID_OWNER;
        String path = request.getRequestURI();
        ZonedDateTime timestamp = ZonedDateTime.now();

        log.warn(error.getLogFormat(), error.getErrorCode(), path, timestamp, ex.getMessage());

        ErrorResponseDto response = ErrorResponseDto.builder()
                .statusCode(error.getHttpStatus().value())
                .errorCode(error.getErrorCode())
                .description(getErrorDescription(ex, error))
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }

    @ExceptionHandler(InvalidRestaurantException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRestaurantException(
            InvalidRestaurantException ex,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        ZonedDateTime timestamp = ZonedDateTime.now();

        ApiError error = switch (ex.getMessage()) {
            case RestaurantConstants.MSG_NAME_ONLY_NUMBERS -> ApiError.INVALID_NAME;
            case RestaurantConstants.MSG_NIT_NUMERIC -> ApiError.INVALID_NIT;
            case RestaurantConstants.MSG_PHONE_FORMAT -> ApiError.INVALID_PHONE;
            case RestaurantConstants.MSG_INVALID_ADDRESS -> ApiError.INVALID_ADDRESS;
            case RestaurantConstants.MSG_INVALID_LOGO_URL -> ApiError.INVALID_LOGO_URL;
            case RestaurantConstants.MSG_MISSING_FIELDS -> ApiError.MISSING_FIELDS;
            default -> ApiError.INVALID_RESTAURANT_FIELD;
        };

        log.warn(error.getLogFormat(), error.getErrorCode(), path, timestamp, ex.getMessage());

        ErrorResponseDto response = ErrorResponseDto.builder()
                .statusCode(error.getHttpStatus().value())
                .errorCode(error.getErrorCode())
                .description(error.getDescription())
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnexpectedException(
            Exception ex,
            HttpServletRequest request) {

        ApiError error = ApiError.SYSTEM_ERROR;
        String path = request.getRequestURI();
        ZonedDateTime timestamp = ZonedDateTime.now();

        log.error(error.getLogFormat(), error.getErrorCode(), path, timestamp, ex);

        ErrorResponseDto response = ErrorResponseDto.builder()
                .statusCode(error.getHttpStatus().value())
                .errorCode(error.getErrorCode())
                .description(getErrorDescription(ex, error))
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }




    private String getErrorDescription(Exception ex, ApiError error) {
        return CUSTOM_MESSAGES.contains(ex.getMessage())
                ? ex.getMessage()
                : error.getDescription();
    }
}
