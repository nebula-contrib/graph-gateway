package com.lpz.graph.gateway.web.controller.base;

import com.lpz.graph.gateway.common.constant.ErrorCode;
import com.lpz.graph.gateway.common.param.resp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 全局Error/404处理
 *
 */
@ApiIgnore
@RestController
@Slf4j
public class GlobalErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private static final String INDEX_PATH = "/index";

    /**
     * @return
     */
    @RequestMapping(ERROR_PATH)
    public Response handleError() {
        log.error("404 NOT FOUND");
        return Response.buildFailure(ErrorCode.NOT_FOUND);
    }

    /**
     * @param response
     * @throws IOException
     */
    @RequestMapping(INDEX_PATH)
    public void indexPage(HttpServletResponse response) throws IOException {
        log.info("to index.html");
        response.sendRedirect("/index.html");
    }

    //    @Override
    public String getErrorPath() {
        log.error("errorPath....");
        return ERROR_PATH;
    }
}
