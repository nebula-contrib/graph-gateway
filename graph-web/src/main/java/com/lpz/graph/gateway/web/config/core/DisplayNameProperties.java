
package com.lpz.graph.gateway.web.config.core;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 属性配置信息
 *
 */
@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "display-name", ignoreUnknownFields = true)
public class DisplayNameProperties {

    /**
     * 配置space
     */
    private TagConfig spaceName;

    /**
     *
     */
    @Data
    public static class TagConfig {

        /**
         * tag标签展示属性名配置
         */
        private HashMap<String, String[]> tagNames;

        /**
         * space选择初始化标签点配置
         * space - tag
         */
        private HashMap<String, String> tagVertex;

    }


}
