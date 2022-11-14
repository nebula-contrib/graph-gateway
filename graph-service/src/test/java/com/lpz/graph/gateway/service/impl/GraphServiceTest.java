package com.lpz.graph.gateway.service.impl;

import com.lpz.graph.gateway.common.param.req.ConnectReq;
import com.lpz.graph.gateway.common.param.req.ExecReq;
import com.lpz.graph.gateway.common.param.req.InitializeGqlReq;
import com.lpz.graph.gateway.common.param.req.NeighborhoodReq;
import com.lpz.graph.gateway.common.param.resp.SessionBo;
import com.lpz.graph.gateway.common.util.UuidUtil;
import com.lpz.graph.gateway.service.BaseTest;
import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: lpz
 * @Date: 2022-03-28 11:20
 */
@Slf4j
public class GraphServiceTest extends BaseTest {


    static SessionBo sessionBo = new SessionBo();
    @InjectMocks
    GraphServiceImpl graphService;

    @BeforeAll
    public static void initAllTest() {
        ConnectReq req = new ConnectReq();
        req.setAddress("172.21.18.181");
        req.setPort(9669);
        req.setUsername("root");
        req.setPassword("nebula");

        NebulaPool pool = new NebulaPool();
        try {
            NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
            nebulaPoolConfig.setMaxConnSize(100);
            pool.init(Arrays.asList(new HostAddress(req.getAddress(), req.getPort())), nebulaPoolConfig);
            Session session = pool.getSession(req.getUsername(), req.getPassword(), false);
            //
            String nsid = UuidUtil.getSessionId();
            sessionBo.setSession(session);
            sessionBo.setAddress(req.getAddress());
            sessionBo.setPort(req.getPort());
            sessionBo.setUsername(req.getUsername());
//            sessionBo.setPassword(req.getPassword());
            sessionBo.setNsid(nsid);
            log.info(sessionBo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectTest() {
        ConnectReq mackReq = Mockito.mock(ConnectReq.class);
        graphService.connect(mackReq);
//        Assertions.assertThrows(NotValidConnectionException.class, () -> graphService.connect(mackReq));

        ConnectReq req = new ConnectReq();
        req.setAddress("172.21.18.181");
        req.setPort(9669);
        req.setUsername("root");
        req.setPassword("nebula");
        graphService.connect(req);
    }


    @Test
    public void execTest0() {
        ExecReq req = Mockito.mock(ExecReq.class);
        graphService.exec(sessionBo, req);

        req = new ExecReq("ERROR TEST SHOW SPACES....");
        graphService.exec(sessionBo, req);
    }

    @Test
    public void execTest01() {
        ExecReq req = new ExecReq("use`basketballplayer`;");
        graphService.exec(sessionBo, req);

        req = new ExecReq("EXPLAIN format='row' SHOW TAGS;");
        graphService.exec(sessionBo, req);

        req = new ExecReq("EXPLAIN format='dot' SHOW TAGS;");
        graphService.exec(sessionBo, req);

//        req = new ExecReq("PROFILE format='dot' SHOW TAGS;");
//        graphService.exec(sessionBo, req);
    }

    @Test
    public void execTest() {
//        ExecReq req = new ExecReq("SHOW SPACES;");
//        graphService.exec(sessionBo, req);

        ExecReq req = new ExecReq("use`basketballplayer`;");
        graphService.exec(sessionBo, req);

        req = new ExecReq("MATCH (n:player) RETURN n LIMIT 5;");
        graphService.exec(sessionBo, req);

        req = new ExecReq("MATCH p = (v)--(v2) WHERE id(v) IN ['team204'] And id(v2) IN ['player100', 'player114'] RETURN p;");
        graphService.exec(sessionBo, req);

        req = new ExecReq("fetch prop on `serve` 'player100'->'team204'@0, 'player101'->'team204'@0 YIELD edge as `edge_`;");
        graphService.exec(sessionBo, req);


    }

    @Test
    public void execTest2() {
        //
        ExecReq req = new ExecReq("use`basketballplayer`;");
        graphService.exec(sessionBo, req);
        // list set map
        // 集合（Set）是复合数据类型。在 OpenCypher 中，集合不是一个数据类型，而在 nGQL 中，集合仍在设计阶段。
        req = new ExecReq("RETURN split('basketballplayer', 'b');");
        graphService.exec(sessionBo, req);
        // map
        req = new ExecReq("YIELD {key: 'Value', listKey: [{inner: 'Map1'}, {inner: 'Map2'}]};");
        graphService.exec(sessionBo, req);

//        req = new ExecReq("LOOKUP ON player;");
//        graphService.exec(sessionBo, req);

    }


    @Test
    public void initializeGqlTest() throws Exception {
        InitializeGqlReq req = Mockito.mock(InitializeGqlReq.class);
        graphService.initializeGql(sessionBo, req, null);

        HashMap<String, String> tagVertexMap = new HashMap<>();
        tagVertexMap.put("basketballplayer", "player");
        graphService.initializeGql(sessionBo, req, tagVertexMap);
    }


    @Test
    public void neighborhoodTest() throws Exception {
        NeighborhoodReq req = Mockito.mock(NeighborhoodReq.class);
        graphService.neighborhood(sessionBo, req);

        req = new NeighborhoodReq();
        List<String> vidList = new ArrayList<>();
        vidList.add("player100");
        req.setVidList(vidList);
        graphService.neighborhood(sessionBo, req);

        vidList.clear();
        vidList.add("player100");
        vidList.add("team211");
        req.setVidList(vidList);
        graphService.neighborhood(sessionBo, req);

        vidList.clear();
        vidList.add("player100");
        vidList.add("player101");
        vidList.add("player114");
        req.setVidList(vidList);
        graphService.neighborhood(sessionBo, req);

    }


    @AfterAll
    public void disconnectTest() {
        graphService.disconnect(sessionBo);
    }

}
