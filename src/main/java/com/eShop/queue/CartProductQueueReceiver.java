package com.eShop.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.eShop.domain.CartProduct;
import com.eShop.repositories.CartProductRepository;

@Component
public class CartProductQueueReceiver {

	@Autowired private CartProductRepository repository;
	
	@JmsListener(destination = "ProductQueue", containerFactory = "myFactory")
	public void onReceive(CartProduct cartProduct) throws Throwable {
		try {
			System.out.println("---------------> " + cartProduct);
			repository.save(cartProduct);
		} catch (Throwable t) {
			//Put message in error Queue or another temp storage
			//Send error notification
		}
	}
			
}
