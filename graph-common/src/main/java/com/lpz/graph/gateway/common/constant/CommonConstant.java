package com.lpz.graph.gateway.common.constant;

/**
 * 常量
 *
 */
public class CommonConstant {


    /**
     * 默认页码为1
     */
    public static final Integer DEFAULT_PAGE_INDEX = 1;

    /**
     * 默认页大小为10
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;


    /**
     * WEB端的session过期时间，可设置调整，12小时=720min，24小时=1440min
     */
    public static final long APP_SESSION_TIMEOUT = 720;


    public static final String JDBC_NEBULA_PREFIX = "jdbc:nebula://";


}