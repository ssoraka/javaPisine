package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImpl implements ProductRepository {
	private DataSource dataSource;

	private final String SQL_FIND_ALL =
			"SELECT * FROM product";

	private final String SQL_FIND_BY_ID =
			"SELECT * FROM product " +
			"WHERE id = ?";

	private final String SQL_UPDATE =
			"UPDATE product " +
			"SET name = ?, price = ? " +
			"WHERE id = ?";

	private final String SQL_SAVE =
			"INSERT INTO product (id, name, price) " +
			"VALUES (?, ?, ?)";

	private final String SQL_DELETE =
			"DELETE FROM product " +
			"WHERE id = ?";

	public ProductRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Product> findAll() {
		List<Product> productList = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
			while (resultSet.next())
			{
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				Double price = resultSet.getDouble("price");
				Product product = new Product(id, name, price);
				productList.add(product);
			}
			return productList;
		} catch (SQLException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
		return productList;
	}

	@Override
	public Optional<Product> findById(Long id) {
		Optional<Product> productOptional = Optional.empty();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next())
			{
				String name = resultSet.getString("name");
				Double price = resultSet.getDouble("price");
				Product product = new Product(id, name, price);
				productOptional = Optional.of(product);
			}
			return  productOptional;
		} catch (SQLException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
		return productOptional;
	}

	@Override
	public void save(Product product) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_SAVE);
			statement.setLong(1, product.getId());
			statement.setString(2, product.getName());
			statement.setDouble(3, product.getPrice());
			if (statement.executeUpdate() == 0) {
				throw new IllegalAccessException("Record was not save!");
			}
		} catch (SQLException | IllegalAccessException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
	}

	@Override
	public void update(Product product) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
			statement.setLong(3, product.getId());
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
			if (statement.executeUpdate() == 0) {
				throw new IllegalAccessException("Record was not update!");
			}
		} catch (SQLException | IllegalAccessException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Long id) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
			statement.setLong(1, id);
			if (statement.executeUpdate() == 0) {
				throw new IllegalAccessException("Record was not delete!");
			}
		} catch (SQLException | IllegalAccessException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
	}
}
