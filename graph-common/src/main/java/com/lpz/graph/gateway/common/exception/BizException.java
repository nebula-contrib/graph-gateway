package com.lpz.graph.gateway.common.exception;

import com.lpz.graph.gateway.common.constant.ErrorCode;
import com.lpz.graph.gateway.common.constant.ErrorCodeI;

/**
 * @ClassName BizException
 * @Description
 **/
public class BizException extends RuntimeException {
    private Integer errCode;
    private String errMessage;


    /**
     * @param errCode
     * @param errMessage
     */
    public BizException(ErrorCodeI errCode, String errMessage) {
        super(errMessage, (Throwable) null, false, false);
        this.transErrorCodeI(errCode);
        this.setErrMessage(errMessage);
    }

    /**
     * @param errCode
     */
    public BizException(ErrorCodeI errCode) {
        super(errCode.getMessage(), (Throwable) null, false, false);
        this.transErrorCodeI(errCode);
    }

    /**
     * @param errMessage
     * @param e
     */
    public BizException(String errMessage, Throwable e) {
        super(errMessage, e, true, true);
        this.transErrorCodeI(ErrorCode.BIZ_ERROR);
        this.setErrMessage(errMessage);
    }

    /**
     * @param errCode
     * @param errMessage
     */
    public BizException(Integer errCode, String errMessage) {
        super(errMessage, (Throwable) null, false, false);
        this.setErrCode(errCode);
        this.setErrMessage(errMessage);
    }

    /**
     * @param errCodeI
     */
    public void transErrorCodeI(ErrorCodeI errCodeI) {
        this.errCode = errCodeI.getCode();
        this.errMessage = errCodeI.getMessage();
    }

    public Integer getErrCode() {
        return this.errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

}
