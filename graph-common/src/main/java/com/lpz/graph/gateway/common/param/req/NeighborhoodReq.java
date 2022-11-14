package com.lpz.graph.gateway.common.param.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: lpz
 * @Date: 2022-02-11 10:12
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "neighborhoodReq对象", description = "共同邻居请求")
public class NeighborhoodReq implements Serializable {


    @NotEmpty(message = "vidList must not null.")
    @ApiModelProperty(value = "图数据点ID集合")
    private List<String> vidList;

//    private String tagName;


}
