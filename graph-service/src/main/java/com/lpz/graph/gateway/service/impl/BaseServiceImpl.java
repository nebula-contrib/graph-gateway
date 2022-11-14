package com.lpz.graph.gateway.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lpz.graph.gateway.common.param.req.OrderQueryParam;
import com.lpz.graph.gateway.common.param.req.QueryParam;
import com.lpz.graph.gateway.service.BaseService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    protected static final String DESC = "desc";
    protected static final String ASC = "asc";

    protected Page setPageParam(QueryParam queryParam) {
        return setPageParam(queryParam, null);
    }

    protected Page setPageParam(QueryParam queryParam, OrderItem defaultOrder) {
        Page page = new Page();
        // 设置当前页码
        page.setCurrent(queryParam.getPageNum());
        // 设置页大小
        page.setSize(queryParam.getPageSize());
        /**
         * 如果是queryParam是OrderQueryParam，并且不为空，则使用前端排序
         * 否则使用默认排序
         */
        if (queryParam instanceof OrderQueryParam) {
            OrderQueryParam orderQueryParam = (OrderQueryParam) queryParam;
            List<OrderItem> orderItems = orderQueryParam.getOrders();
            if (CollectionUtils.isEmpty(orderItems)) {
                page.setOrders(Arrays.asList(defaultOrder));
            } else {
                page.setOrders(orderItems);
            }
        } else {
            page.setOrders(Arrays.asList(defaultOrder));
        }

        return page;
    }

    protected Page setPageMultiOrderParam(QueryParam queryParam, List<OrderItem> orders) {
        Page page = new Page();
        // 设置当前页码
        page.setCurrent(queryParam.getPageNum());
        // 设置页大小
        page.setSize(queryParam.getPageSize());
        /**
         * 如果是queryParam是OrderQueryParam，并且不为空，则使用前端排序
         * 否则使用默认排序
         */
        if (queryParam instanceof OrderQueryParam) {
            OrderQueryParam orderQueryParam = (OrderQueryParam) queryParam;
            List<OrderItem> orderItems = orderQueryParam.getOrders();
            if (CollectionUtils.isEmpty(orderItems)) {
                page.setOrders(orders);
            } else {
                page.setOrders(orderItems);
            }
        } else {
            page.setOrders(orders);
        }

        return page;
    }

    /**
     * @param requestParam
     * @return
     */
    protected OrderItem sort(OrderQueryParam requestParam) {
        switch (requestParam.getOrder().toLowerCase()) {
            case DESC:
                return OrderItem.desc(requestParam.getSortBy());
            default:
                return OrderItem.asc(requestParam.getSortBy());
        }
    }


}
