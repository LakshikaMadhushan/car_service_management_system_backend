package com.esoft.carservice.configuration.exception;


import com.esoft.carservice.dto.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import static com.esoft.carservice.constant.ResponseCodes.INTERNAL_SERVER_ERROR;
import static com.esoft.carservice.constant.ResponseMessages.UNEXPECTED_ERROR_OCCURRED;



@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<CommonResponse> handleAnyException(Exception ex, WebRequest webRequest) {
        ex.printStackTrace();
        return new ResponseEntity<>(new CommonResponse(INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_OCCURRED),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<CommonResponse> handleCustomException(CustomException ex, WebRequest webRequest) {
        ex.printStackTrace();
        return new ResponseEntity<>(new CommonResponse(ex.getStatus(), ex.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<CommonResponse> handleServiceException(ServiceException ex, WebRequest webRequest) {
        ex.printStackTrace();
        return new ResponseEntity<>(new CommonResponse(ex.getStatus(), ex.getMessage()), HttpStatus.OK);
    }

}
