package com.lpz.graph.gateway.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @Author: lpz
 * @Date: 2019-09-27 17:12
 */
//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class SpringTest {


//    @Before
//    public void setup() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }


    /**
     * @throws Exception
     */
    @Test
    public void baseTest() {
        Assertions.assertEquals(true, true);
    }

}
