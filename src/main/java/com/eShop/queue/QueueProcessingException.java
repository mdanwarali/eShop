package com.eShop.queue;

import com.eShop.domain.CartProduct;

public class QueueProcessingException extends Throwable {
	private CartProduct cartProduct;
	
	public QueueProcessingException(CartProduct cartProduct, Throwable t) {
		super(t);
		this.cartProduct = cartProduct;
	}

	public CartProduct getCartProduct() {
		return cartProduct;
	}
}
