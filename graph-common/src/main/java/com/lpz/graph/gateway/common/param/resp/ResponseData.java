package com.lpz.graph.gateway.common.param.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: lpz
 * @Date: 2022-02-16 11:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData implements Serializable {


    private List<String> headers;

    private List<Map<String, Object>> tables;

    private Integer timeCost;

}
