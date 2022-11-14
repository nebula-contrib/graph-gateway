
package com.lpz.graph.gateway.web.config.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * <code>
 *
 * </code>
 *
 */
public class StringToDoubleConverter implements Converter<String, Double> {

    @Override
    public Double convert(String source) {
        return StringToDoubleUtil.convert(source);
    }
}
