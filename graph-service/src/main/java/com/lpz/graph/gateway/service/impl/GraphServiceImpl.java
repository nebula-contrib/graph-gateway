package com.lpz.graph.gateway.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.lpz.graph.gateway.common.constant.ErrorCode;
import com.lpz.graph.gateway.common.param.req.ConnectReq;
import com.lpz.graph.gateway.common.param.req.ExecReq;
import com.lpz.graph.gateway.common.param.req.InitializeGqlReq;
import com.lpz.graph.gateway.common.param.req.NeighborhoodReq;
import com.lpz.graph.gateway.common.param.resp.ConnectBo;
import com.lpz.graph.gateway.common.param.resp.NeighborhoodResp;
import com.lpz.graph.gateway.common.param.resp.Response;
import com.lpz.graph.gateway.common.param.resp.ResponseData;
import com.lpz.graph.gateway.common.param.resp.SessionBo;
import com.lpz.graph.gateway.common.util.CacheManager;
import com.lpz.graph.gateway.common.util.StringUtil;
import com.lpz.graph.gateway.common.util.UuidUtil;
import com.lpz.graph.gateway.service.GraphService;
import com.lpz.graph.gateway.service.util.GqlUtil;
import com.lpz.graph.gateway.service.util.GraphUtil;
import com.google.common.collect.Maps;
import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import com.vesoft.nebula.graph.PlanDescription;
import com.vesoft.nebula.graph.PlanNodeDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * @Author: lpz
 * @Date: 2022-02-09 11:10
 */
@Slf4j
@Service
public class GraphServiceImpl implements GraphService {

//
//    @Autowired
//    private RedisService redisService;


    /**
     * 共同邻居
     *
     * @param sessionBo
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public Response neighborhood(SessionBo sessionBo, NeighborhoodReq req) throws Exception {
        Response connectResp = new Response(ErrorCode.SUCCESSED);
        //
        List<String> vidList = req.getVidList();
        if (CollectionUtil.isEmpty(vidList)) {
            return new Response(ErrorCode.FAILED.getCode(), "vidList is illegal.");
        }
        HashSet hashSet = new HashSet(vidList);
        vidList.clear();
        vidList.addAll(hashSet);
        if (vidList.size() > 10 || vidList.size() < 2) {
            return new Response(ErrorCode.FAILED.getCode(), "vidList size should in [2,10].");
        }
        //
        Session session = sessionBo.getSession();
        ResultSet resultSet = session.execute(GqlUtil.queryIntersectGql(vidList));
        if (ErrorCode.SUCCESSED.getCode() != resultSet.getErrorCode()) {
            return new Response(resultSet.getErrorCode(), resultSet.getErrorMessage());
        }
        log.info("query data from space: {}", resultSet.getSpaceName());
        if (resultSet.rowsSize() < 1) {
            return new Response(ErrorCode.SUCCESSED.getCode(), "there is no data.");
        }
        List<String> intersectIds = new ArrayList<>();
        // 行数据
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record valueWrappers = resultSet.rowValues(i);
            // 列数据
            for (ValueWrapper valueWrapper : valueWrappers) {
                Object value = GraphUtil.getValue(valueWrapper);
                if (Objects.nonNull(value)) {
                    intersectIds.add(String.valueOf(value));
                }
            }
        }
        log.info("intersectIds: {}", StringUtil.join(intersectIds, ","));
        NeighborhoodResp respBuild = NeighborhoodResp.builder().intersectIds(intersectIds).build();
        //
        List<String> allidSet = new ArrayList<>(vidList);
        allidSet.addAll(intersectIds);
        for (int i = 0; i < allidSet.size(); i++) {
            allidSet.set(i, "'" + allidSet.get(i) + "'");
        }
        // 查找各个标签点属性
        String vertexGql = "MATCH (n) WHERE id(n) IN [" + StringUtil.join(allidSet, ",") + "] RETURN n;";
        respBuild.setVertexGql(vertexGql);
        //查询边的关系(点与交点之间path)
        String relashion = GqlUtil.queryRelashionGql(vidList, intersectIds);
        respBuild.setPathGql(relashion);
//            List<String> edgeGqls = obtainEdgeGqls(session, relashion);
//            respBuild.setEdgeGqls(edgeGqls);
        connectResp.setData(respBuild);
        return connectResp;
    }

    @Override
    public Response<ConnectBo> connect(ConnectReq req) {
        log.info(req.toString());
        NebulaPool pool = new NebulaPool();
        Response connectResp = new Response(ErrorCode.SUCCESSED);
        try {
            NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
            nebulaPoolConfig.setMaxConnSize(100);
            pool.init(Arrays.asList(new HostAddress(req.getAddress(), req.getPort())), nebulaPoolConfig);
            Session session = pool.getSession(req.getUsername(), req.getPassword(), false);

            //
            String nsid = UuidUtil.getSessionId();
            SessionBo sessionBo = new SessionBo();
            sessionBo.setSession(session);
            sessionBo.setAddress(req.getAddress());
            sessionBo.setPort(req.getPort());
            sessionBo.setUsername(req.getUsername());
//            sessionBo.setPassword(req.getPassword());
            sessionBo.setNsid(nsid);
            CacheManager.put(nsid, sessionBo);
//            redisService.set(nsid, sessionBo, CommonConstant.APP_SESSION_TIMEOUT, TimeUnit.MINUTES);
            log.info("login success... address:{}, sessionId:{}", req.getAddress(), nsid);

            //
            ConnectBo build = ConnectBo.builder().nsid(nsid).version(pool.getActiveConnNum() + "").build();
            connectResp.setData(build);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Response(ErrorCode.FAILED.getCode(), e.getMessage());
        }

        return connectResp;
    }


    @Override
    public Response<ResponseData> exec(SessionBo sessionBo, ExecReq req) {
        Response connectResp = new Response(ErrorCode.SUCCESSED);
        ResponseData responseData = new ResponseData();
        try {
            //
            Session session = sessionBo.getSession();
            log.info("execute gql: {}", req.getGql());
            ResultSet resultSet = session.execute(req.getGql());
            if (ErrorCode.SUCCESSED.getCode() != resultSet.getErrorCode()) {
                return new Response(resultSet.getErrorCode(), resultSet.getErrorMessage());
            }
            log.info("query data from space: {}", resultSet.getSpaceName());
            List<String> colNames = resultSet.getColumnNames();
            List<Map<String, Object>> rowValueMaps = new ArrayList<>();

            // 查询执行计划... 参考：nebula-hhtp-gateway, Execute, service/dao/dao.go:302
            PlanDescription planDesc = resultSet.getPlanDesc();
            if (Objects.nonNull(planDesc)) {
                return dealPlanDescription(resultSet, planDesc);
            }
            // 行数据
            int rowsSize = resultSet.rowsSize();
            for (int i = 0; i < rowsSize; i++) {
                ResultSet.Record valueWrappers = resultSet.rowValues(i);
                List<Map<String, Object>> verticesParsedList = new ArrayList<>();
                List<Map<String, Object>> edgesParsedList = new ArrayList<>();
                List<Map<String, Object>> pathsParsedList = new ArrayList<>();
                // 列数据
                Map<String, Object> rowValue = Maps.newHashMap();
                for (int j = 0; j < valueWrappers.size(); j++) {
                    ValueWrapper valueWrapper = valueWrappers.get(j);
                    rowValue.put(colNames.get(j), GraphUtil.getValue(valueWrapper));
                    String valueType = GraphUtil.descType(valueWrapper.getValue()).toLowerCase();
                    Map<String, Object> parseValue = Maps.newHashMap();
                    if (Arrays.asList("vertex", "edge", "path").contains(valueType)) {
                        parseValue.put("type", valueType);
                    }
                    if ("vertex".equals(valueType)) {
                        GraphUtil.getVertexInfo(valueWrapper, parseValue);
                        verticesParsedList.add(parseValue);
                    } else if ("edge".equals(valueType)) {
                        GraphUtil.getEdgeInfo(valueWrapper, parseValue);
                        edgesParsedList.add(parseValue);
                    } else if ("path".equals(valueType)) {
                        GraphUtil.getPathInfo(valueWrapper, parseValue);
                        pathsParsedList.add(parseValue);
                    } else if ("list".equals(valueType)) {
                        ArrayList<ValueWrapper> valueList = valueWrapper.asList();
                        getCollectionInfo(valueList, verticesParsedList, edgesParsedList, pathsParsedList);
                    } else if ("set".equals(valueType)) {
                        HashSet<ValueWrapper> valueList = valueWrapper.asSet();
                        getCollectionInfo(valueList, verticesParsedList, edgesParsedList, pathsParsedList);
                    } else if ("map".equals(valueType)) {
                        getMapInfo(valueWrapper, verticesParsedList, edgesParsedList, pathsParsedList);
                    }

                    if (verticesParsedList.size() > 0) {
                        rowValue.put("_verticesParsedList", verticesParsedList);
                    }
                    if (edgesParsedList.size() > 0) {
                        rowValue.put("_edgesParsedList", edgesParsedList);
                    }
                    if (pathsParsedList.size() > 0) {
                        rowValue.put("_pathsParsedList", pathsParsedList);
                    }
                }
                rowValueMaps.add(rowValue);
            }
            //
            responseData.setHeaders(colNames);
            responseData.setTables(rowValueMaps);
            responseData.setTimeCost(resultSet.getLatency());
            connectResp.setData(responseData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Response(ErrorCode.FAILED.getCode(), e.getMessage());
        }
        return connectResp;
    }

    private void getMapInfo(ValueWrapper valWarp, List<Map<String, Object>> verticesParsedList,
                            List<Map<String, Object>> edgesParsedList, List<Map<String, Object>> pathsParsedList) throws UnsupportedEncodingException {
        Map<String, ValueWrapper> valueMap = valWarp.asMap();
        for (ValueWrapper value : valueMap.values()) {
            String vType = GraphUtil.descType(value.getValue()).toLowerCase();
            Map<String, Object> props = Maps.newHashMap();
            dealWithValueList(value, props, vType, verticesParsedList, edgesParsedList, pathsParsedList);
        }
        return;
    }


    private void getCollectionInfo(Collection<ValueWrapper> listType, List<Map<String, Object>> verticesParsedList,
                                   List<Map<String, Object>> edgesParsedList, List<Map<String, Object>> pathsParsedList) throws UnsupportedEncodingException {

        for (ValueWrapper value : listType) {
            Map<String, Object> props = Maps.newHashMap();
            String vType = GraphUtil.descType(value.getValue()).toLowerCase();
            props.put("type", vType);
            dealWithValueList(value, props, vType, verticesParsedList, edgesParsedList, pathsParsedList);
        }
        return;
    }

    private void dealWithValueList(ValueWrapper value, Map<String, Object> props, String vType, List<Map<String, Object>> verticesParsedList, List<Map<String, Object>> edgesParsedList,
                                   List<Map<String, Object>> pathsParsedList) throws UnsupportedEncodingException {
        if ("vertex".equals(vType)) {
            props = GraphUtil.getVertexInfo(value, props);
            verticesParsedList.add(props);
        } else if ("edge".equals(vType)) {
            props = GraphUtil.getEdgeInfo(value, props);
            edgesParsedList.add(props);
        } else if ("path".equals(vType)) {
            props = GraphUtil.getPathInfo(value, props);
            pathsParsedList.add(props);
        } else if ("list".equals(vType)) {
            ArrayList<ValueWrapper> valueWrappers = value.asList();
            getCollectionInfo(valueWrappers, verticesParsedList, edgesParsedList, pathsParsedList);
        } else if ("set".equals(vType)) {
            HashSet<ValueWrapper> valueWrappers = value.asSet();
            getCollectionInfo(valueWrappers, verticesParsedList, edgesParsedList, pathsParsedList);
        } else if ("map".equals(vType)) {
            getMapInfo(value, verticesParsedList, edgesParsedList, pathsParsedList);
        } else {
            // no need to parse basic value now
        }
    }


    /**
     * @param resultSet
     * @param planDesc
     * @return
     */
    private Response<ResponseData> dealPlanDescription(ResultSet resultSet, PlanDescription planDesc) throws UnsupportedEncodingException {
        Response connectResp = new Response(ErrorCode.SUCCESSED);
        ResponseData responseData = new ResponseData();
        List<String> columnNames = resultSet.getColumnNames();
        List<Map<String, Object>> listMaps = new ArrayList<>();
        //
        String format = new String(planDesc.getFormat(), "utf-8");
        if ("row".equals(format)) {
            columnNames.addAll(Arrays.asList("id", "name", "dependencies", "profiling data", "operator info"));
//            List<Row> rows = resultSet.getRows();
            List<PlanNodeDescription> planNodeDescs = planDesc.getPlan_node_descs();
            for (PlanNodeDescription nodeDesc : planNodeDescs) {
                Map<String, Object> props = Maps.newHashMap();
                props.put("id", nodeDesc.getId());
                props.put("name", new String(nodeDesc.getName(), "utf-8"));
                props.put("dependencies", nodeDesc.getDependencies());
                props.put("profiling data", nodeDesc.getProfiles());
                props.put("operator info", new String(nodeDesc.getOutput_var(), "utf-8"));
                listMaps.add(props);
            }
            responseData.setHeaders(columnNames);
            responseData.setTables(listMaps);
            responseData.setTimeCost(resultSet.getLatency());
            connectResp.setData(responseData);
            return connectResp;
        } else {
            Map<String, Object> props = Maps.newHashMap();
            columnNames.addAll(Arrays.asList("format"));
            StringBuilder sb = new StringBuilder();
            for (PlanNodeDescription nodeDesc : planDesc.getPlan_node_descs()) {
                String name = new String(nodeDesc.getName(), "utf-8");
                sb.append(name + "_" + nodeDesc.getId() + "|" + new String(nodeDesc.getOutput_var(), "utf-8") + ";");
            }
            if ("dot".equals(format)) {
                // resp.MakeDotGraph()
                props.put("format", sb.toString());
            } else if ("dot:struct".equals(format)) {
                // resp.MakeDotGraphByStruct()
                props.put("format", format + ":" + sb.toString());
            }
            listMaps.add(props);
            responseData.setHeaders(columnNames);
            responseData.setTables(listMaps);
            responseData.setTimeCost(resultSet.getLatency());
            connectResp.setData(responseData);
            return connectResp;
        }
    }


    @Override
    public Response disconnect(SessionBo req) {
        //
//        Boolean delete = redisService.delete(req.getNsid());
        CacheManager.remove(req.getNsid());
        if (Objects.nonNull(req.getSession())) {
            req.getSession().release();
        }
        return Response.buildSuccess();
    }


    @Override
    public Response initializeGql(SessionBo sessionBo, InitializeGqlReq req, HashMap<String, String> tagVertexMap) throws Exception {
        //
        Session session = sessionBo.getSession();
        String gql1 = "SHOW TAGS;";
        ResultSet resultSet = session.execute(gql1);
        if (ErrorCode.SUCCESSED.getCode() != resultSet.getErrorCode()) {
            return new Response(resultSet.getErrorCode(), resultSet.getErrorMessage());
        }
        String spaceName = resultSet.getSpaceName();
        log.info("query data from space: {}", spaceName);
        String tagName = null;
        if (Objects.nonNull(tagVertexMap) && tagVertexMap.containsKey(spaceName)) {
            tagName = tagVertexMap.get(spaceName);
        }
        if (StringUtil.isBlank(tagName)) {
            // 行数据
            for (int i = 0; i < resultSet.rowsSize(); i++) {
                ResultSet.Record valueWrappers = resultSet.rowValues(i);
                // 列数据
                for (ValueWrapper valueWrapper : valueWrappers) {
                    Object value = GraphUtil.getValue(valueWrapper);
                    if (Objects.nonNull(value)) {
                        tagName = String.valueOf(value);
                        break;
                    }
                }
                if (StringUtil.isNotBlank(tagName)) {
                    break;
                }
            }
        }
        if (StringUtil.isBlank(tagName)) {
            return new Response(ErrorCode.FAILED.getCode(), spaceName + " has no tags...");
        }
        //
        int skip = new Random().nextInt(15);
        String gql2 = "MATCH (n:`" + tagName + "`) RETURN n SKIP " + skip + " LIMIT " + req.getNum() + ";";
        ExecReq execReq = new ExecReq(gql2);
        return exec(sessionBo, execReq);
    }

    /**
     * 查询边的关系(点与交点之间path)，进一步组装查找边信息
     *
     * @param session
     * @param relashion
     * @return
     */
//    private List<String> obtainEdgeGqls(Session session, String relashion) {
//        List<String> edgeGqls = new ArrayList<>();
//        try {
//            //
//            ResultSet resultSet2 = session.execute(relashion);
//            if (ErrorCode.SUCCESSED.getCode() != resultSet2.getErrorCode()) {
//                throw new BizException(resultSet2.getErrorCode(), resultSet2.getErrorMessage());
//            }
//            Map<String, Set<String>> edgePropMap = new HashMap<>();
//            // 行数据
//            for (int i = 0; i < resultSet2.rowsSize(); i++) {
//                ResultSet.Record valueWrappers = resultSet2.rowValues(i);
//                // 列数据
//                for (ValueWrapper valueWrapper : valueWrappers) {
//                    String valueType = GraphUtil.descType(valueWrapper.getValue()).toLowerCase();
//                    if ("path".equals(valueType)) {
//                        List<Relationship> relationships = valueWrapper.asPath().getRelationships();
//                        for (Relationship relationship : relationships) {
//                            //`serve` "player102"->"team203"@0
//                            StringBuilder ssb = new StringBuilder();
//                            String edgeGql = ssb.append("'").append(GraphUtil.getID(relationship.srcId()))
//                                    .append("'->'").append(GraphUtil.getID(relationship.dstId())).append("'")
//                                    .append("@").append(relationship.ranking()).toString();
//                            log.info("to edgeGql:{} ", edgeGql);
//                            String edgeName = relationship.edgeName();
//                            if (edgePropMap.containsKey(edgeName)) {
//                                edgePropMap.get(edgeName).add(edgeGql);
//                            } else {
//                                Set<String> edgeList = new HashSet<>();
//                                edgeList.add(edgeGql);
//                                edgePropMap.put(edgeName, edgeList);
//                            }
//                        }
//                    } else {
//                        log.warn("illegal gql, not find path relationships...");
//                    }
//                }
//            }
//            if (CollectionUtil.isEmpty(edgePropMap)) {
//                throw new BizException(ErrorCode.FAILED.getCode(), "gql result error. find null path to query edgeGql...");
//            }
//            edgePropMap.forEach((key, values) -> {
//                StringBuilder ssb = new StringBuilder();
//                edgeGqls.add(ssb.append("`").append(key).append("` ").append(StringUtil.join(values, ", ")).toString());
//            });
//            for (int i = 0; i < edgeGqls.size(); i++) {
//                edgeGqls.set(i, "fetch prop on " + edgeGqls.get(i) + " YIELD edge as `edge_`;");
//            }
//            return edgeGqls;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return edgeGqls;
//    }


}
