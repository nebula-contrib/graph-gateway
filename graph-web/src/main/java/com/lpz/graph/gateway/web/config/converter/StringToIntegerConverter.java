
package com.lpz.graph.gateway.web.config.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * <code>
 * <p>
 * </code>
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        return StringToIntegerUtil.convert(source);
    }
}
