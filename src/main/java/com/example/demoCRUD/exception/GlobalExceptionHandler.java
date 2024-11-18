package com.example.demoCRUD.exception;

import com.example.demoCRUD.dto.request.ApiRespon;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class) // tao messge error cho phan user default
    ResponseEntity<ApiRespon> handlingRuntimeException(RuntimeException exception){
        ApiRespon apiRespon = new ApiRespon();
        apiRespon.setCode(1001);
        apiRespon.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiRespon);
    }

    @ExceptionHandler(value = AppException.class) // tao messge error cho phan user
    ResponseEntity<ApiRespon> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiRespon apiRespon = new ApiRespon();
        apiRespon.setCode(errorCode.getCode());
        apiRespon.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiRespon);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class) //tao messge error cho phan ueser
    ResponseEntity<ApiRespon> handlingValidation(MethodArgumentNotValidException exception){
        String enumkey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;


        try{
            errorCode = ErrorCode.valueOf(enumkey);
        }catch (IllegalArgumentException e){


        }

        ApiRespon apiRespon = new ApiRespon();
        apiRespon.setCode(errorCode.getCode());
        apiRespon.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiRespon);
    }
}
