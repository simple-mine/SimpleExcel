package com.huangjun.util.excel.exception;

/**
 * @Author: huangjun
 * @Date: 2022/1/18 10:29
 * @Version 1.0
 */
public class ExcelException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected String resultCode;
    /**
     * 错误信息
     */
    protected String resultMessage;

    public ExcelException() {
        super();
    }

    public ExcelException(String resultMessage) {
        super(resultMessage);
        this.resultMessage = resultMessage;
    }
}
