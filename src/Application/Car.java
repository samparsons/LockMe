package Application;

import java.io.Serializable;

public class Car implements Serializable {
	private int year;
	private String model;

	private static final long serialVersionUID = 1L;
	public Car() {

	}

	public Car(int year, String model) {

		this.year = year;
		this.model = model;
	}

	@Override
	public String toString() {
		return "Car [year=" + year + ", model=" + model + "]";
	}
	
	

	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

}