package com.stefan.DailyTest;

public enum HandleResultEnum {
    SUCCESS(200, "执行成功"),
    FAIL(500, "执行失败"),
    ERROR_UNABLE_TO_ACCESS(404, "未找到可执行脚本！")
    ;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    HandleResultEnum(int status, String msg) {
        this.code = status;
        this.msg = msg;
    }
}
