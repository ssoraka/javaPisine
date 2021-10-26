package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
	List<Product> findAll() throws SQLException, IllegalAccessException;
	Optional<Product> findById(Long id) throws IllegalAccessException;
	void update(Product product) throws IllegalAccessException;
	void save(Product product) throws IllegalAccessException;
	void delete(Long id);
}
