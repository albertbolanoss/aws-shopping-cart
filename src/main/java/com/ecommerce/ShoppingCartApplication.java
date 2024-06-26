package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
@EnableCaching
public class ShoppingCartApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	@Bean
	public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}
}
