

package com.lpz.graph.gateway.web.config.converter;

import org.apache.commons.lang3.StringUtils;

/**
 * <code>
 * <pre>
 * 空字符串("")转换成Integer的null
 *
 * </pre>
 * </code>
 */
public class StringToIntegerUtil {
    /**
     * @param source
     * @return
     */
    public static Integer convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        Integer i = Integer.parseInt(source);
        return i;
    }
}
