package com.lpz.graph.gateway.common.param.req;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 可排序查询参数对象
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("可排序查询参数对象")
public class OrderQueryParam extends QueryParam {


    private static final long serialVersionUID = 57714391204790143L;

    @ApiModelProperty(value = "排序")
    private List<OrderItem> orders;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String sortBy;
    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序，默认降序")
    private String order = "desc";

    /**
     * @param orderItem
     */
    public void defaultOrder(OrderItem orderItem) {
        this.defaultOrders(Arrays.asList(orderItem));
    }

    /**
     * @param orderItems
     */
    public void defaultOrders(List<OrderItem> orderItems) {
        if (CollectionUtils.isEmpty(orderItems)) {
            return;
        }
        this.orders = orderItems;
    }

}
