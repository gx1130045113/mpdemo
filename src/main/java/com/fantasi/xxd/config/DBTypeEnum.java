package com.fantasi.xxd.config;

/**
 * @author xxd
 * @date 2019/12/27 14:17
 */
public enum DBTypeEnum {

    db1Source("db1Source"),
    db2Source("db2Source")
    ;
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
