package com.lpz.graph.gateway.service;

import com.lpz.graph.gateway.common.param.req.ConnectReq;
import com.lpz.graph.gateway.common.param.req.ExecReq;
import com.lpz.graph.gateway.common.param.req.InitializeGqlReq;
import com.lpz.graph.gateway.common.param.req.NeighborhoodReq;
import com.lpz.graph.gateway.common.param.resp.ConnectBo;
import com.lpz.graph.gateway.common.param.resp.Response;
import com.lpz.graph.gateway.common.param.resp.ResponseData;
import com.lpz.graph.gateway.common.param.resp.SessionBo;

import java.util.HashMap;

/**
 * @Author: lpz
 * @Date: 2022-02-09 11:08
 */
public interface GraphService {

    /**
     * @param req
     * @return
     */
    Response<ConnectBo> connect(ConnectReq req);

    /**
     * @param req
     * @return
     */
    Response<ResponseData> exec(SessionBo sessionBo, ExecReq req);

    /**
     * @param session
     * @return
     */
    Response disconnect(SessionBo session);

    /**
     * @param session
     * @param req
     * @return
     */
    Response initializeGql(SessionBo session, InitializeGqlReq req, HashMap<String, String> tagVertex) throws Exception;

    /**
     * @param session
     * @param req
     * @return
     */
    Response neighborhood(SessionBo session, NeighborhoodReq req) throws Exception;


}
