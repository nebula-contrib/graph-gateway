package com.lpz.graph.gateway.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * <p>
 *     日期格式常量
 * </p>
 */
public interface DatePattern {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DatePattern.YYYYMM);

    /**
     * 年-月-日
     */
    String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 年-月-日 时:分
     */
    String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * 年-月-日 时:分:秒
     */
    String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 年-月-日 时:分:秒:毫秒
     */
    String YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 时:分
     */
    String HH_MM = "HH:mm";
    /**
     * 时:分:秒
     */
    String HH_MM_SS = "HH:mm:ss";
    /**
     * 时:分:秒:毫秒
     */
    String HH_MM_SS_S = "HH:mm:ss:S";

    /**
     * 时:分:秒:毫秒
     */
    String YYYYMM = "yyyyMM";
}
