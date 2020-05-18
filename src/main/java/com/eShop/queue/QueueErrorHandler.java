package com.eShop.queue;

import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class QueueErrorHandler implements ErrorHandler {

	@Override
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

}
