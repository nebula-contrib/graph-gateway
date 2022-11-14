package com.lpz.graph.gateway.web.controller;

import com.lpz.graph.gateway.common.constant.ErrorCode;
import com.lpz.graph.gateway.common.constant.SessionConstants;
import com.lpz.graph.gateway.common.exception.BizException;
import com.lpz.graph.gateway.common.param.req.ConnectReq;
import com.lpz.graph.gateway.common.param.req.ExecReq;
import com.lpz.graph.gateway.common.param.req.InitializeGqlReq;
import com.lpz.graph.gateway.common.param.req.NeighborhoodReq;
import com.lpz.graph.gateway.common.param.resp.ConnectBo;
import com.lpz.graph.gateway.common.param.resp.NeighborhoodResp;
import com.lpz.graph.gateway.common.param.resp.Response;
import com.lpz.graph.gateway.common.param.resp.ResponseData;
import com.lpz.graph.gateway.common.param.resp.SessionBo;
import com.lpz.graph.gateway.service.GraphService;
import com.lpz.graph.gateway.web.config.core.DisplayNameProperties;
import com.lpz.graph.gateway.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 接口列表：
 * /api/db/connect
 * /api/db/exec
 * /api/db/disconnect
 *
 * @Author: lpz
 * @Date: 2022-02-09 11:07
 */
@Slf4j
@RestController
@RequestMapping("/api/db")
@Api(value = "图数据操作API", description = "图数据操作API接口")
public class GraphDBController extends BaseController {

    @Autowired
    private GraphService graphService;

    @Autowired
    private DisplayNameProperties displayNameProperties;


    /**
     * 数据库连接
     */
    @PostMapping("/connect")
    @ApiOperation(value = "数据库连接", notes = "数据库连接接口", response = Response.class)
    public Response<ConnectBo> connect(@Validated @RequestBody ConnectReq req) {
        //
        Response result = graphService.connect(req);
        if (ErrorCode.SUCCESSED.getCode().equals(result.getCode())) {
            ConnectBo data = (ConnectBo) result.getData();
            setCookie(getResponse(), SessionConstants.SESSION_KEY, data.getNsid());
        }
        return result;
    }


    /**
     * 数据库gql执行
     */
    @PostMapping("/exec")
    @ApiOperation(value = "数据库gql执行", notes = "数据库gql执行接口", response = Response.class)
    public Response<ResponseData> exec(@Validated @RequestBody ExecReq req) {

        Response result;
        try {
            SessionBo session = getSession(request);
            result = graphService.exec(session, req);
        } catch (BizException e) {
            return Response.buildFailure(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.buildFailure(ErrorCode.FAILED.getCode(), e.getMessage());
        }
        return result;
    }


    /**
     * 数据库断开连接
     */
    @PostMapping("/disconnect")
    @ApiOperation(value = "数据库断开连接", notes = "数据库断开连接接口", response = Response.class)
    public Response<ConnectBo> disconnect(HttpServletRequest request) {

        Response result;
        try {
            SessionBo sessionBo = getSession(request);
            log.info("logout success, username:{}, sessionId：{}", sessionBo.getUsername(), sessionBo.getNsid());
            // 移除session
            result = graphService.disconnect(sessionBo);
        } catch (BizException e) {
            return Response.buildFailure(e);
        }
        return result;
    }


    /**
     * 图数据可视化初始页面，图数据子图初始化查询。
     * 随机查询5个节点子图数据，展示其节点、边关系；节点展示节点id，边展示边类型，默认用力导向布局。
     */
    @PostMapping("/initializeGql")
    @ApiOperation(value = "随机查询5个节点子图数据", notes = "随机查询5个节点子图数据接口", response = Response.class)
    public Response<ResponseData> initializeGql(@Validated @RequestBody InitializeGqlReq req) {

        Response result;
        try {
            SessionBo session = getSession(request);
            //
            HashMap<String, String> tagVertex = displayNameProperties.getSpaceName().getTagVertex();
            result = graphService.initializeGql(session, req, tagVertex);
        } catch (BizException e) {
            return Response.buildFailure(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.buildFailure(ErrorCode.FAILED.getCode(), e.getMessage());
        }
        return result;
    }


    /**
     * 前端过滤，多条件查询
     * 1、获取数据库包含的点，”节点标签“。如：SHOW TAGS;
     * # 1.1、获取数据库包含的边，如：show edges;
     * 2、查询标签，获取标签字段，”节点属性“。如：desc tag `player`;
     * 3、根据标签字段，显示可选操作。“操作项”。
     * int类型对应“等于、大于、小于、不小于、不大于、不等于”
     * string类型“等于、不等于、包含”
     * 4、根据条件筛选符合条件节点。“输入值”
     *
     * 前端页面节点筛选查询，不涉及后端接口？
     *
     * @param req
     * @return
     */
//    @PostMapping("/filterQuery")
//    public Response<List<String>> filterQuery(@RequestBody FilterQueryReq req) {
//
//        return null;
//    }


    /**
     * 数据库连接
     */
    @PostMapping("/displayNames")
    @ApiOperation(value = "获取tag展示名称", notes = "获取tag展示名称displayNames接口", response = Response.class)
    public Response<ConnectBo> displayNames() {

        Response result = Response.buildSuccess();
        try {
            getSession(request);
            HashMap<String, String[]> names = displayNameProperties.getSpaceName().getTagNames();
            result.setData(names);
        } catch (BizException e) {
            return Response.buildFailure(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.buildFailure(ErrorCode.FAILED.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * 查询共同邻居
     */
    @PostMapping("/neighborhood")
    @ApiOperation(value = "查询共同邻居", notes = "查询共同邻居接口", response = Response.class)
    public Response<NeighborhoodResp> neighborhood(@Validated @RequestBody NeighborhoodReq req) {

        Response result;
        try {
            SessionBo session = getSession(request);
            result = graphService.neighborhood(session, req);
        } catch (BizException e) {
            return Response.buildFailure(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.buildFailure(ErrorCode.FAILED.getCode(), e.getMessage());
        }
        return result;
    }

}
