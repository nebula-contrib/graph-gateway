package com.lpz.graph.gateway.common.param.resp;

import com.vesoft.nebula.client.graph.net.Session;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: lpz
 * @Date: 2022-02-14 17:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionBo implements Serializable {


    @ApiModelProperty(value = "IP地址")
    private String address;

    @ApiModelProperty(value = "数据库端口")
    private Integer port = 9669;

    @ApiModelProperty(value = "数据库用户名")
    private String username;

//    @ApiModelProperty(value = "数据库密码")
//    private String password;

    @ApiModelProperty(value = "自己生成维护的sessionId")
    private String nsid;

    // TODO： nebula-client中Session无法序列化，已反馈官方，被逼无奈只能自己想办法处理
    @ApiModelProperty(value = "graph获取的session")
    private Session session;

}
