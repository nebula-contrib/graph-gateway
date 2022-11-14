package com.lpz.graph.gateway.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 */
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("dev")
//@Transactional
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {


    /**
     *
     */
    @BeforeAll
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * @throws Exception
     */
    @Test
    public void baseTest() {
        Assertions.assertEquals(true, true);
    }
}
