package com.lpz.graph.gateway.common.param.resp;

import java.util.Collection;

/**
 * @ClassName MultiResponse
 * @Description
 **/
public class MultiResponse<T> extends Response {
    private long total;
    private Collection<T> data;

    public MultiResponse() {
    }

    /**
     * @param data
     * @param total
     * @param <T>
     * @return
     */
    public static <T> MultiResponse<T> of(Collection<T> data, long total) {
        MultiResponse<T> multiResponse = new MultiResponse();
        multiResponse.setData(data);
        multiResponse.setTotal(total);
        return multiResponse;
    }

    /**
     * @param data
     * @param <T>
     * @return
     */
    public static <T> MultiResponse<T> ofWithoutTotal(Collection<T> data) {
        return of(data, 0L);
    }

    /**
     * @return
     */
    public long getTotal() {
        return this.total;
    }

    /**
     * @param total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public Collection<T> getData() {
        return this.data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    /**
     * @param errCode
     * @param errMessage
     * @return
     */
    public static MultiResponse buildFailure(Integer errCode, String errMessage) {
        MultiResponse response = new MultiResponse();
        response.setCode(errCode);
        response.setMessage(errMessage);
        return response;
    }

    /**
     * @return
     */
    public static MultiResponse buildSuccess() {
        MultiResponse response = new MultiResponse();
        return response;
    }
}

