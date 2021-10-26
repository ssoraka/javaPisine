package edu.school21.models;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

@OrmEntity(table = "myCars")
public class Car {
	@OrmColumnId
	private Long id;
	@OrmColumn(name = "car_model", lenght = 150)
	private String model;
	@OrmColumn(name = "car_price")
	private Double price;
	private int speed;
	@OrmColumn(name = "car_reserv")
	private Boolean reserved;

	public Car() {
	}

	public Car(Long id, String model, Double price, int speed, Boolean reserved) {
		this.id = id;
		this.model = model;
		this.price = price;
		this.speed = speed;
		this.reserved = reserved;
	}

	@Override
	public String toString() {
		return "Cars{" +
				"id=" + id +
				", model='" + model + '\'' +
				", price=" + price +
				", speed=" + speed +
				", reserved=" + reserved +
				'}';
	}
}
