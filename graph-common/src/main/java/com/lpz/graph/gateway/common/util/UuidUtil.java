package com.lpz.graph.gateway.common.util;

import java.util.UUID;

/**
 *
 */
public class UuidUtil {

    /**
     * uuid,randomUUID
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    /**
     * 根据uuid获取sessionId，拼接前缀app便于标识查找
     *
     * @return
     */
    public static String getSessionId() {
        return "ve_" + getUUID();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(UuidUtil.get32RandomCode());

        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        uuid = uuid.replaceAll("-", "");
        System.out.println(uuid);
        System.out.println(uuid.length());

        String appSessionId = getSessionId();
        System.out.println(appSessionId);
    }
}
