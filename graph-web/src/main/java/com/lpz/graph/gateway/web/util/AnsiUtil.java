
package com.lpz.graph.gateway.web.util;

import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;
import org.springframework.core.env.Environment;

/**
 */
@Slf4j
public class AnsiUtil {

    private static boolean enableAnsi;

    static {
        Boolean value = false;
        try {
            Environment environment = SpringContextUtil.getBean(Environment.class);
            value = environment.getProperty("spring-boot-plus.enable-ansi", boolean.class);
            value = value == null ? Boolean.FALSE : value;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        enableAnsi = value;
    }

    public static String getAnsi(Ansi.Color color, String text) {

        if (enableAnsi) {
            return Ansi.ansi().eraseScreen().fg(color).a(text).reset().toString();
        }
        return text;
    }

    public static String getAnsi(Ansi.Color color, String text, boolean flag) {
        if (flag) {
            return Ansi.ansi().eraseScreen().fg(color).a(text).reset().toString();
        }
        return text;
    }
}
