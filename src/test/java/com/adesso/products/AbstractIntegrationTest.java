package com.adesso.products;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

@SpringBootTest(classes = ProductsInventoryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.datasource.url=jdbc:postgresql://localhost:5433/productsInventory" })
@ActiveProfiles("dev")
public abstract class AbstractIntegrationTest {
	protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
			.withCopyFileToContainer(MountableFile.forClasspathResource("/sql/init-db.sql"),
					"/docker-entrypoint-initdb.d/");

	@DynamicPropertySource
	static void datasourceProperties(final DynamicPropertyRegistry registry) {
		postgres.withDatabaseName("productsInventory");
		postgres.withUsername("productsUser");
		postgres.withPassword("products");
		postgres.start();
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.port", postgres::getFirstMappedPort);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}
}
