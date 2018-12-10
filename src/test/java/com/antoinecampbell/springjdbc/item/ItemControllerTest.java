package com.antoinecampbell.springjdbc.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.antoinecampbell.springjdbc.MatchesPattern.matchesPattern;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:11-alpine://localhost/db?TC_INITSCRIPT=schema.sql",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
})
@AutoConfigureMockMvc
@Transactional
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {

    }

    @Test
    public void shouldInsertItem() throws Exception {
        Item item = new Item();
        item.setName("test");

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, matchesPattern(".*/items/\\d$")))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is(item.getName())));
    }
}