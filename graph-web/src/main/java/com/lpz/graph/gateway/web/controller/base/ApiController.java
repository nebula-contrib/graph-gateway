package com.lpz.graph.gateway.web.controller.base;

import com.lpz.graph.gateway.common.constant.ErrorCode;
import com.lpz.graph.gateway.common.param.resp.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * ApiResultEST API 公共控制器
 * </p>
 */
@Slf4j
public class ApiController {

    /**
     * <p>
     * 请求成功
     * </p>
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return
     */
    protected <T> Response<T> ok(T data) {
        return Response.buildSuccess(data);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param code 提示内容
     * @return
     */
    protected Response<Object> fail(ErrorCode code) {
        return Response.buildFailure(code);
    }

}
