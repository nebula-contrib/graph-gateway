package com.lpz.graph.gateway.common.param.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: lpz
 * @Date: 2022-02-10 10:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectBo implements Serializable {


    /**
     * The response data nsid 5e18fa40-5343-422f-84e3-e7f9cad6b735 is encoded by HMAC-SH256 encryption algorithm,
     * so it's not the same as what you get from a cookie. If you connect to the graphd service successfully,
     * remember to save the NSID locally, which is important for the exec api to execute nGQL.
     * If you restart the gateway server, all authenticated session will be lost, please be aware of this.
     */
    @ApiModelProperty(value = "数据库连接成功后获取的nsid")
    private String nsid;


    @ApiModelProperty(value = "数据库版本信息")
    private String version;
}
