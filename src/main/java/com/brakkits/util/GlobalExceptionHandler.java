package com.brakkits.util;

import com.brakkits.data.DTO;
import com.brakkits.util.DataNotFoundException;
import com.brakkits.util.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * Ali Cooper
 * brakkits
 * CST-451
 * 4/12/2020
 *
 * Will Be globally used for handling exceptions
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    private DTO<HttpStatus> backendError = new DTO<>();

    /**
     * handle auth issue
     * @param ex Exception
     * @param request WebRequest
     * @return ResponseEntity<DTO<String>>
     */
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<DTO<String>> handleValidationException(Exception ex, WebRequest request) {
        DTO<String> validationError = new DTO<>();
        validationError.setStatusCode(HttpStatus.CONFLICT.value());
        validationError.setData("Validation Error");
        validationError.setMessage("Please fix specified fields");

        return new ResponseEntity<DTO<String>>(validationError, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    /**
     * Failsafe catch all
     * @param ex Exception
     * @param request WebRequest
     * @return ResponseEntity<DTO<HttpStatus>>
     */
    @ResponseBody
    @ExceptionHandler( Exception.class )
    public DTO<HttpStatus> handleAll(Exception ex, WebRequest request) {

        backendError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        backendError.setData(HttpStatus.INTERNAL_SERVER_ERROR);
        backendError.setMessage("Internal Server Error");

        return backendError;
    }

    /**
     * handle data not found
     * @param ex Exception
     * @param request WebRequest
     * @return ResponseEntity<DTO<HttpStatus>>
     */
    @ResponseBody
    @ExceptionHandler({DataNotFoundException.class})
    public DTO<HttpStatus> handleDataMissing(Exception ex, WebRequest request) {

        backendError.setStatusCode(HttpStatus.NOT_FOUND.value());
        backendError.setData(HttpStatus.NOT_FOUND);
        backendError.setMessage("Data Not Found");

        return backendError;
    }

    /**
     * handle auth issue
     * @param ex Exception
     * @param request WebRequest
     * @return ResponseEntity<DTO<HttpStatus>>
     */
    @ExceptionHandler({SecurityException.class} )
    @ResponseBody
    public DTO<HttpStatus> handleAuthDenial(Exception ex, WebRequest request) {

        backendError.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        backendError.setData(HttpStatus.UNAUTHORIZED);
        backendError.setMessage("Unauthorized User");

        return backendError;
    }

    /**
     * handle auth issue
     * @param ex Exception
     * @param request WebRequest
     * @return ResponseEntity<DTO<HttpStatus>>
     */
    @ExceptionHandler({InvalidDataException.class} )
    @ResponseBody
    public DTO<HttpStatus> handleInvalidData(Exception ex, WebRequest request) {

        backendError.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        backendError.setData(HttpStatus.UNAUTHORIZED);
        backendError.setMessage(ex.getMessage());

        return backendError;
    }

}
