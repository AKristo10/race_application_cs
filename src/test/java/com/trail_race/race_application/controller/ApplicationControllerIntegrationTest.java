package com.trail_race.race_application.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String VALID_JWT_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJjcyIsInN1YiI6ImFuZHJlYS5rcmlzdG9AZ21haWwuY29tIiwiZXhwIjoxNzQ2NDY5NDQ2LCJpYXQiOjE3MTQ5MzM0NDZ9.JZja9YR5dRBjGIFu98uO8ZWF1uOTTpS5bSADWyJdydcTlZOXhK13BsVlgBhExpvvGY4cOrtrFxFCumb2QDs89LD_0KoZPlBzeLH6JSiBNr8SooUnD7FY2-BKdkYXT03IzBE0MU_xdLAEAq2e6U8uJbYPPey1qPZytFZ9MVvNyTtF5YoLc_5Dp0jXcvf8J05wrLKjYrj5Lork03FMnIuQg87eKOwYcx9lyh_R7ydjEL7jX2vBBOf4-9SdwX3lSatLIRc3AO1_5cMdHgwXj0v-8x4G9bMJb3c0ZdLRRIKlySd0CooRWo8fZH7uvzfwBynQ0N2YiIGrJgy_ot_aCdqgoQ";

    @Test
    public void testCreateApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + VALID_JWT_TOKEN)
                        .content("{"
                                + "\"eventType\": \"CREATE_APPLICATION\","
                                + "\"id\": 1,"
                                + "\"firstName\": \"John\","
                                + "\"lastName\": \"Doe\","
                                + "\"club\": \"Running Club\","
                                + "\"distance\": \"MARATHON\""
                                + "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Application created successfully."));
    }

    @Test
    public void testUpdateApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/user/application/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + VALID_JWT_TOKEN)
                        .content("{"
                                + "\"eventType\": \"UPDATE_APPLICATION\","
                                + "\"id\": 1,"
                                + "\"firstName\": \"Updated John\","
                                + "\"lastName\": \"Updated Doe\","
                                + "\"club\": \"Updated Running Club\","
                                + "\"distance\": \"MARATHON\""
                                + "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Application updated successfully."));
    }

    @Test
    public void testDeleteApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                        .header("Authorization", "Bearer " + VALID_JWT_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Application deleted successfully."));
    }

}