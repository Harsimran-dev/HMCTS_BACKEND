package com.hmctsbackend;

import com.hmctsbackend.controllers.TaskController;
import com.hmctsbackend.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HmctsBackendApplicationTests {

    @Autowired
    private TaskController taskController;

    @Autowired
    private TaskService taskService;

    @Test
    void contextLoads() {
        // Verifies that the context loads and beans are injected correctly
        assert taskController != null;
        assert taskService != null;
    }
}
