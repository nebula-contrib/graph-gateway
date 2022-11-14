package com.lpz.graph.gateway.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.lpz.graph.gateway.common.param.req.OrderQueryParam;
import com.lpz.graph.gateway.common.param.req.QueryParam;
import com.lpz.graph.gateway.service.BaseTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

/**
 * @Author: lpz
 * @Date: 2022-03-28 11:20
 */
public class BaseServiceTest extends BaseTest {


    @InjectMocks
    private BaseServiceImpl baseService;

    @Mock
    QueryParam queryParam;

    @Mock
    OrderItem defaultOrder;

    @Mock
    List<OrderItem> orderItems;


    @Test
    public void setPageParamTest() {
        baseService.setPageParam(queryParam);
    }

    @Test
    public void setPageParamTest2() {
        baseService.setPageParam(queryParam, defaultOrder);

        OrderQueryParam mockedOrder = Mockito.mock(OrderQueryParam.class);
        Mockito.when(mockedOrder.getOrders()).thenReturn(Collections.EMPTY_LIST);
        queryParam = mockedOrder;
        baseService.setPageParam(queryParam, defaultOrder);

        orderItems.add(new OrderItem());
        Mockito.when(mockedOrder.getOrders()).thenReturn(orderItems);
        queryParam = mockedOrder;
        baseService.setPageParam(queryParam, defaultOrder);
    }

    @Test
    public void setPageMultiOrderParamTest() {

        baseService.setPageMultiOrderParam(queryParam, orderItems);

        OrderQueryParam mockedOrder = Mockito.mock(OrderQueryParam.class);
        queryParam = mockedOrder;
        baseService.setPageMultiOrderParam(queryParam, Collections.EMPTY_LIST);

        orderItems.add(new OrderItem());
        Mockito.when(mockedOrder.getOrders()).thenReturn(orderItems);
        queryParam = mockedOrder;
        baseService.setPageMultiOrderParam(queryParam, orderItems);

    }


    @Test
    public void sortTest() {
        OrderQueryParam orderQueryParam = new OrderQueryParam();
        orderQueryParam.setOrder("desc");
        baseService.sort(orderQueryParam);

        orderQueryParam.setOrder("asc");
        baseService.sort(orderQueryParam);
    }

}
