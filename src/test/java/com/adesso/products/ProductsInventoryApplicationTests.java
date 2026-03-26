package com.adesso.products;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductsInventoryApplicationTests extends AbstractIntegrationTest {
	
	@AfterAll
    static void afterAll() {
        postgres.stop();
    }

	@Test
	void contextLoads() {
		assert(true);
	}
}
