package com.cszt.cloud.http;

public class NotSupportedException extends Exception {

    /**
     * @Fields: serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String errorMsg;

    public NotSupportedException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
