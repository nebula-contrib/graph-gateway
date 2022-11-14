package com.lpz.graph.gateway.service.util;

import com.lpz.graph.gateway.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lpz
 * @Date: 2022-03-28 17:11
 */
@Slf4j
public class GqlUtil {


    /**
     * 组装gql，获取多个点交集的标签点
     *
     * @param vidList
     * @return
     */
    public static String queryIntersectGql(List<String> vidList) {
        StringBuilder ssb = new StringBuilder();
        for (int i = 0; i < vidList.size(); i++) {
            String vid = vidList.get(i);
            if (StringUtil.isBlank(vid)) {
                break;
            }
            if (i == vidList.size() - 1) {
                ssb.append("MATCH (v)--(v2) WHERE id(v2)=='" + vid + "' RETURN DISTINCT id(v) ");
            } else {
                ssb.append("MATCH (v)--(v2) WHERE id(v2)=='" + vid + "' RETURN DISTINCT id(v) INTERSECT ");
            }
        }
        String intersectGql = ssb.toString();
        log.info(intersectGql);
        return intersectGql;
    }


    /**
     * 拼接gql，查找点和交点之间的边关系
     * relationships
     *
     * @return
     */
    public static String queryRelashionGql(List<String> vidList, List<String> intersectIds) {
        // 查找边的关系path
        StringBuilder ssb = new StringBuilder();
        List<String> vidListTemp = new ArrayList<>(vidList);
        List<String> interListTemp = new ArrayList<>(intersectIds);
        //  MATCH p = (v)--(v2) WHERE id(v) IN ["team204"] And id(v2) IN ["player100", "player114", "player101"] RETURN p
        for (int i = 0; i < vidList.size(); i++) {
            vidListTemp.set(i, "'" + vidList.get(i) + "'");
        }
        for (int i = 0; i < intersectIds.size(); i++) {
            interListTemp.set(i, "'" + intersectIds.get(i) + "'");
        }
        String vidStr = StringUtil.join(vidListTemp, ",");
        String interStr = StringUtil.join(interListTemp, ",");
        String qGql = ssb.append("MATCH p = (v)--(v2) WHERE id(v) IN [").append(interStr)
                .append("] And id(v2) IN [").append(vidStr).append("] RETURN p").toString();

        log.info(qGql);
        return qGql;
    }


}
