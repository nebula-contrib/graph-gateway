package com.lpz.graph.gateway.common.param.resp;

import com.lpz.graph.gateway.common.constant.ErrorCode;
import com.lpz.graph.gateway.common.constant.ErrorCodeI;
import com.lpz.graph.gateway.common.exception.BizException;
import lombok.Data;

/**
 * @ClassName Response
 * @Description
 **/
@Data
public class Response<T> extends DTO {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private T data;

    public Response() {
    }


    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(ErrorCodeI errorCodeI) {
        this.code = errorCodeI.getCode();
        this.message = errorCodeI.getMessage();
    }


    /**
     * @param errCode
     * @param errMessage
     * @return
     */
    public static Response buildFailure(Integer errCode, String errMessage) {
        Response response = new Response();
        response.setCode(errCode);
        response.setMessage(errMessage);
        return response;
    }

    /**
     * @param errorCodeI
     * @return
     */
    public static Response buildFailure(ErrorCodeI errorCodeI) {
        Response response = new Response();
        response.setCode(errorCodeI.getCode());
        response.setMessage(errorCodeI.getMessage());
        return response;
    }

    /**
     * @param bizException
     * @return
     */
    public static Response buildFailure(BizException bizException) {
        Response response = new Response();
        response.setCode(bizException.getErrCode());
        response.setMessage(bizException.getErrMessage());
        return response;
    }

    /**
     * @return
     */
    public static Response buildSuccess() {
        Response response = new Response(ErrorCode.SUCCESSED);
        return response;
    }


    /**
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> buildSuccess(T data) {
        Response<T> response = new Response(ErrorCode.SUCCESSED);
        response.setData(data);
        return response;
    }

}
