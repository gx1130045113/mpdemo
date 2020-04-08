package com.fantasi.xxd.common;

/**
 * @author xxd
 * @date 2019/12/27 14:10
 */
public interface ICommonError {

    /**
     * 获取错误代码
     *
     * @return 错误编码
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
