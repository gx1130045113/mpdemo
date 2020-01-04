package com.fantasi.xxd.common;

/**
 * @author xxd
 * @date 2019/12/27 14:05
 */
public enum WebErrorEnum implements ICommonError{
    LoginError(1001, "用户名或密码错误"),
    OutOfDateError(1005, "用户已过期");

    private int code;
    private String message;

    WebErrorEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
