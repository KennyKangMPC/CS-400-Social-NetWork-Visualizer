///////////////////////////////////////////////////////////////////////////////
//Assignment Name: Social Network A2
//Author: A-Team 15
//Member:
//Kang Fu, 001, kfu9@wisc.edu
//Jamal Moussa, 002, jmoussa@wisc.edu
//Suraj Joottu, 001, sjoottu@wisc.edu
//Tejvir Mann, 001, tsmann@wisc.edu
//Michael Her, 002, mvher2@wisc.edu
//Due Date: November 3, 2019
//Other Source Credits: None
//Known Bugs: None, to the best of my knowledge
///////////////////////////////////////////////////////////////////////////////
package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a single user in Bakla Network. This user has a given
 * name and a (x,y) position to be used in visualizer.
 * 
 * @author A-Team 15
 *
 */

//TODO: May change them all the package level code
public class Person {
	// private fields
	private String name;
	private double positionX;
	private double positionY;
	private boolean isCenter;
	private List<Person> neighbors;
	private boolean isVisited;
	private int distance;
	private int count;
	private double textLengthX;
	private double textLengthY;
	
	/**
	 * Constructor
	 *  
	 * @param name - Name of person to be created
	 */
	public Person(String name) {
		this(name, 0, 0); // TODO: Get position based on canvas 
	}

	/**
	 * Constructor
	 * 
	 * @param name - Name of person to be created
	 * @param pX - starting x position in (x,y)
	 * @param pY - starting y position in (x,y)
	 */
	public Person(String name, double pX, double pY) {
		this.name = name;
		this.positionX = pX;
		this.positionY = pY;
		this.neighbors = new ArrayList<Person>();
		this.isCenter = false;
		this.isVisited = false;
		this.distance = 0;
		this.count = 0;
		this.textLengthX = 0;
		this.textLengthY = 0;
	}

	/**
	 * Returns the name of the current person
	 * 
	 * @return name of current person
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns current x value in (x,y) of this person
	 * 
	 * @return current x value in (x,y) of this person
	 */
	public double getX() {
		return this.positionX;
	}

	/**
	 * Returns current y value in (x,y) of this person
	 * 
	 * @return current y value in (x,y) of this person
	 */
	public double getY() {
		return this.positionY;
	}

	/**
	 * Sets new value for x in (x,y) for this person
	 * 
	 * @param pX - new x coordinate
	 */
	public void setX(double pX) {
		this.positionX = pX;
	}
	
	/**
	 * Sets new value for y in (x,y) for this person
	 * 
	 * @param pY - new y coordinate
	 */
	public void setY(double pY) {
		this.positionY = pY;
	}
	
	public void setCenter(boolean isCenter) {
		this.isCenter = isCenter;
	}
	
	public boolean getCenter() {
		return this.isCenter;
	}
	
	public List<Person> getNeighbors() {
		return this.neighbors;
	}
	
	public boolean getIsVisited() {
		return this.isVisited;
	}
	
	public void setIsVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public boolean getCount() {
		if (count == 0) {
			this.count++;
			return true;
		}
		return false;
	}
	
	public void setTextX(double xL) {
		this.textLengthX = xL;
	}
	
	public double getTextX() {
		return this.textLengthX;
	}
	
	public void setTextY(double yL) {
		this.textLengthY = yL;
	}
	
	public double getTextY() {
		return this.textLengthY;
	}
}
