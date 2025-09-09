package com.example.springbootpostgresqlcrud.controller;

import com.example.springbootpostgresqlcrud.model.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCRUD() throws Exception {
        // Create
        ProductDTO product = new ProductDTO(null, "Laptop", "A high-performance laptop.", 1200.00);
        String productJson = objectMapper.writeValueAsString(product);

        String response = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andReturn().getResponse().getContentAsString();

        ProductDTO created = objectMapper.readValue(response, ProductDTO.class);

        // Read
        mockMvc.perform(get("/api/products/" + created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));

        // Update
        ProductDTO updated = new ProductDTO(created.id(), "Updated Laptop", "An updated high-performance laptop.", 1300.00);
        String updatedJson = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/api/products/" + created.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Laptop"))
                .andExpect(jsonPath("$.price").value(1300.00));

        // Delete
        mockMvc.perform(delete("/api/products/" + created.id()))
                .andExpect(status().isNoContent());

        // Confirm deletion
        mockMvc.perform(get("/api/products/" + created.id()))
                .andExpect(status().isNotFound());
    }
}
