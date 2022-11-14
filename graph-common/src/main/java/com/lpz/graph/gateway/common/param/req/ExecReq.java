package com.lpz.graph.gateway.common.param.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: lpz
 * @Date: 2022-02-11 10:12
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ApiModel(value = "ExecReq对象", description = "gql命令执行查询请求")
public class ExecReq implements Serializable {

    @NotBlank(message = "gql must not null.")
    @ApiModelProperty(value = "图数据查询gql")
    private String gql;

    @ApiModelProperty(value = "图数据查询gql参数")
    private List<String> paramList;

    public ExecReq(@NotBlank(message = "gql must not null.") String gql) {
        this.gql = gql;
    }

}
