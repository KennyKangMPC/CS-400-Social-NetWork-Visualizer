package application;

public class Person {
	private String name;
	private double positionX;
	private double positionY;
	
	public Person(String name) {
		this(name, 0,0); //TODO: Figure out how to set up positions
	}
	
	public Person(String name, double pX, double pY) {
		this.name = name;
		this.positionX = pX;
		this.positionY = pY;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getX() {
		return this.positionX;
	}
	
	public double getY() {
		return this.positionY;
	}
	
	public void setX(double pX) {
		this.positionX = pX;
	}
	
	public void setY(double pY) {
		this.positionY = pY;
	}
}
