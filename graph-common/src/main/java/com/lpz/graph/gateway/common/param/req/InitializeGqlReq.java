package com.lpz.graph.gateway.common.param.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: lpz
 * @Date: 2022-02-11 10:12
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "InitializeGqlReq对象", description = "子图初始化请求")
public class InitializeGqlReq implements Serializable {


    //    @NotBlank(message = "gql must not null.")
    @ApiModelProperty(value = "随机查询点的个数，默认值：5")
    private Integer num = 5;

    public void setNum(Integer num) {
        if (num == null || num < 5) {
            num = 5;
        }
        this.num = num;
    }

}
