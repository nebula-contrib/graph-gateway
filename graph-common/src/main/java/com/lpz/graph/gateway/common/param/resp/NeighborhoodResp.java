package com.lpz.graph.gateway.common.param.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: lpz
 * @Date: 2022-03-21 16:32
 */
@Data
@Builder
@EqualsAndHashCode
@ApiModel(value = "NeighborhoodResp对象", description = "共同邻居响应数据")
public class NeighborhoodResp implements Serializable {

    @ApiModelProperty(value = "查找到的共同邻居点vid")
    private List<String> intersectIds;
    //
    @ApiModelProperty(value = "查询所有点的属性GQL")
    private String vertexGql;
    //
//    @ApiModelProperty(value = "查询边属性的GQL")
//    private List<String> edgeGqls;

    @ApiModelProperty(value = "查找点与共同邻居之间，边的关系path")
    private String pathGql;

}
