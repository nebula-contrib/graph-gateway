package com.lpz.graph.gateway.common.param.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Date: 2022-02-10 10:51
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "ConnectReq对象", description = "数据库连接查询参数")
public class ConnectReq implements Serializable {

    @NotBlank(message = "address must not null.")
    @ApiModelProperty(value = "数据库地址IP")
    private String address;

    @NotNull(message = "port must not null.")
    @ApiModelProperty(value = "数据库端口")
    private Integer port = 9669;

    @NotBlank(message = "username must not null.")
    @ApiModelProperty(value = "数据库用户名")
    private String username;

    @NotBlank(message = "password must not null.")
    @ApiModelProperty(value = "数据库密码")
    private String password;

}
