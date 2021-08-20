package com.github.jeromp.DocumentManagementSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractApiRestControllerTest {
    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected void setUp(){
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

}
