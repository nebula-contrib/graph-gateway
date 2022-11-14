package com.lpz.graph.gateway.common.param.req;

import com.lpz.graph.gateway.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 */
@Data
@ApiModel("查询参数对象")
public abstract class QueryParam implements Serializable {

    private static final long serialVersionUID = -3263921252635611410L;

    @ApiModelProperty(value = "页码,默认为1")
    private Integer pageNum = CommonConstant.DEFAULT_PAGE_INDEX;

    @ApiModelProperty(value = "页大小,默认为10")
    private Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;

    @ApiModelProperty(value = "搜索字符串")
    private String keyword;

    public void setPageNum(Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            this.pageNum = CommonConstant.DEFAULT_PAGE_INDEX;
        } else {
            this.pageNum = pageNum;
        }
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            this.pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

}
