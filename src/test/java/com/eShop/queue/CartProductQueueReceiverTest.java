package com.eShop.queue;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import com.eShop.config.EShopApplication;
import com.eShop.domain.CartProduct;

@SpringBootTest(classes = { EShopApplication.class, CartProductQueueReceiverTest.class,
		CartProductQueueReceiverTest.TestConfig.class })
public class CartProductQueueReceiverTest {

	@Autowired
	private JmsTemplate template;

	@Test
	public void testOnReceive() throws Exception {
		CartProduct cartProduct = new CartProduct(1, "anwar", "Mobile", 10);
		this.template.convertAndSend("ProductQueue", cartProduct);
		assertThat(TestConfig.latch.await(10, TimeUnit.SECONDS)).isTrue();
		assertThat(TestConfig.received).isEqualTo(cartProduct);
	}

	@Configuration
	public static class TestConfig {

		private static final CountDownLatch latch = new CountDownLatch(1);

		private static Object received;

		@Bean
		public static BeanPostProcessor listenerWrapper() {
			return new BeanPostProcessor() {

				@Override
				public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
					if (bean instanceof CartProductQueueReceiver) {
						MethodInterceptor interceptor = new MethodInterceptor() {

							@Override
							public Object invoke(MethodInvocation invocation) throws Throwable {
								Object result = invocation.proceed();
								if (invocation.getMethod().getName().equals("onReceive")) {
									received = invocation.getArguments()[0];
									latch.countDown();
								}
								return result;
							}
						};
						if (AopUtils.isAopProxy(bean)) {
							((Advised) bean).addAdvice(interceptor);
							return bean;
						} else {
							ProxyFactory proxyFactory = new ProxyFactory(bean);
							proxyFactory.addAdvice(interceptor);
							return proxyFactory.getProxy();
						}
					} else {
						return bean;
					}
				}
			};
		}
	}
}
