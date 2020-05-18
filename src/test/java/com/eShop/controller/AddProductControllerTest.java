package com.eShop.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eShop.config.EShopApplication;

@AutoConfigureMockMvc
//@ContextConfiguration(classes = {EShopApplication.class})
@SpringBootTest(classes = {EShopApplication.class, AddProductController.class})
public class AddProductControllerTest {

	@Autowired
    private MockMvc mockMvc;
	 
	//@Autowired
	//private TestRestTemplate restTemplate;
	
	//@Autowired
	//private JmsTemplate jmsTemplate;
	
	@Test
    public void testAdd() throws Exception {
		String product = "{\"user\":\"anwar\",\"product\":\"tab\",\"quantity\":\"5\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                .content(product)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }
}
