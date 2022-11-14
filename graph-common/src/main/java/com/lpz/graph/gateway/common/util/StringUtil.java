package com.lpz.graph.gateway.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 字符串工具类
 */
@Slf4j
public class StringUtil extends StringUtils {


    /**
     * @param objs
     * @return
     */
    public static boolean isNull(Object... objs) {
        for (Object obj : objs) {
            if (Objects.isNull(obj) || (obj instanceof String && isBlank((String) obj))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 日期转换，201806 --> 2018.06
     *
     * @param month
     * @return
     */
    public static String dealMonth(String month) {
        StringBuilder sb = new StringBuilder(month);
        //在指定的位置插入指定的字符串
        sb.insert(4, ".");
        return sb.toString();
    }

    /**
     * 日期转换，20180602 --> 2018.06.02
     *
     * @param day
     * @return
     */
    public static String dealDay(String day) {
        StringBuilder sb = new StringBuilder(day);
        //在指定的位置插入指定的字符串
        sb.insert(6, ".");
        sb.insert(4, ".");
        return sb.toString();
    }

    /**
     * 百分比取整，保留位数
     *
     * @param num
     * @param scale 小数位数
     * @return
     */
    public static BigDecimal formatDataScale(Double num, int scale) {
        if (num == null) {
            return null;
        }
        return new BigDecimal(num * 100).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }


}
