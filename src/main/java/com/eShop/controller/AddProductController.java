package com.eShop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eShop.domain.CartProduct;
import com.eShop.repositories.CartProductRepository;

/**
 * @author anwar
 *
 */
@RestController
@RequestMapping("/cart")
public class AddProductController {

	/**
	 * 
	 */
	@Autowired
	private JmsTemplate jmsTemplate;

	/**
	 * 
	 */
	@Autowired
	private CartProductRepository repository;

	/**
	 * @param product
	 */
	@PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void add(@RequestBody CartProduct product) {
		jmsTemplate.convertAndSend("ProductQueue", product);
	}

	//Temp only  
	@GetMapping(path = "/view", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CartProduct> viewAll() {
		List<CartProduct> list = new ArrayList<>();
		repository.findAll().forEach(e -> list.add(e));
		return list;
	}
}
