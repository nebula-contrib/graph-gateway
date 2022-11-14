
package com.lpz.graph.gateway.web.config.converter;


import org.apache.commons.lang3.StringUtils;

/**
 * <code>
 * <pre>
 * 空字符串("")转换成Double的null
 *
 * </pre>
 * </code>
 */
public class StringToDoubleUtil {
    /**
     * @param source
     * @return
     */
    public static Double convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        Double d = Double.parseDouble(source);
        return d;
    }
}
