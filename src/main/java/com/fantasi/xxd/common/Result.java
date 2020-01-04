package com.fantasi.xxd.common;

/**
 * @author xxd
 * @date 2019/12/27 14:03
 */
public class Result<T> {

    public Result() {
    }

    public Result(ICommonError iCommonError) {
        this.success = false;
        this.code = iCommonError.getCode();
        this.message = iCommonError.getMessage();
    }

    public Result(T data) {
        this.success = true;
        this.data = data;
    }

    public Result(int code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
    }

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    protected T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    protected int code;
    protected String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {

        return super.toString();
    }
}
