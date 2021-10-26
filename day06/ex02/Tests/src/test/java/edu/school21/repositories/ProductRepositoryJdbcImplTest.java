package edu.school21.repositories;

import static org.junit.jupiter.api.Assertions.*;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImplTest {
	private ProductRepository repository;
	private EmbeddedDatabase dataSource;

	final List<Product> EXPECTED_FIND_ALL_PRODUCTS =
			Arrays.asList(	new Product(1L, "product1", 100.0),
					new Product(2L, "product2", 200.0),
					new Product(3L, "product3", 300.0),
					new Product(4L, "product4", 400.0),
					new Product(5L, "product5", 500.0));
	final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2L, "product2", 200.0);
	final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "new product", 1000.0);
	final Product EXPECTED_SAVED_PRODUCT = new Product(6L, "product6", 600.0);

	@BeforeEach
	public void init() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		dataSource = builder
				.addScript("schema.sql")
				.addScript("data.sql")
				.build();
		repository = new ProductRepositoryJdbcImpl(dataSource);
	}

	@Test
	void connectionDbTest() throws SQLException {
		assertNotNull(dataSource.getConnection());
	}

	@Test
	void findAllTest() throws SQLException, IllegalAccessException {
		assertEquals(EXPECTED_FIND_ALL_PRODUCTS, repository.findAll());
	}

	@Test
	void findByIdTest() throws IllegalAccessException {
		assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, repository.findById(2L).get());
	}

	@Test
	void updateTest() throws IllegalAccessException {
		repository.update(EXPECTED_UPDATED_PRODUCT);
		assertEquals(EXPECTED_UPDATED_PRODUCT, repository.findById(2L).get());
	}

	@Test
	void saveTest() throws IllegalAccessException {
		repository.save(EXPECTED_SAVED_PRODUCT);
		assertEquals(EXPECTED_SAVED_PRODUCT, repository.findById(6L).get());
	}

	@Test
	void deleteTest() throws IllegalAccessException {
		repository.delete(2L);
		assertEquals(Optional.empty(), repository.findById(2L));
	}

	@AfterEach
	void close() {
		dataSource.shutdown();
	}
}
