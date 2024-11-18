package com.example.demoCRUD.exception;

public enum ErrorCode {
    USER_EXISTED(1002,"user_exited"),
    USERNAME_VALIDAL(1003, "Username must be at 4 characters"),
    PASSWORD_VALIDAL(1004, "Password must be at 8 characters"),

    INVALID_KEY(1000, "INVALID KEY"),
    USERNAME_NOT_VALIDAL(1005, "Username not validal"),
    UNAUTHENTICATED(1006, "authenticated")
    ;


    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
