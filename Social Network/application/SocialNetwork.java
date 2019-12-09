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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Back end data structure for Bakla Network
 * 
 * @author A-Team 15
 *
 */
public class SocialNetwork {
	
	// private fields
	List<String> commandList;
	Graph graph;
	/**
	 * Default no argument constructor
	 */
	public SocialNetwork() {
		this.commandList = new ArrayList<String>();
		this.graph = new Graph();	
	}
	
	/**
	 * Adds a friendship between two users.
	 * 
	 * @param sc - first user in friendship
	 * @param de - second user in friendship
	 * @return true if friendship is added into network, false otherwise
	 */
	public boolean addFriends(String sc, String de) {
		graph.addEdge(sc, de);
		return false;
	}

	/**
	 * Removes a friendship between two users.
	 * 
	 * @param sc - first user in friendship
	 * @param de - second user in friendship
	 * @return true if friendship is removed from network, false otherwise
	 */
	public boolean removeFriends(String sc, String de) {
		return false;
	}

	/**
	 * Adds a user into network. The user starts out with no friends.
	 * 
	 * @param user - user to be added in network
	 * @return true if user is added into network, false if otherwise
	 */
	public boolean addUser(String user) {
		graph.addVertex(user);
		this.commandList.add("a " + user);
		Main.drawNode(user);
		return true;
	}

	/**
	 * Removes a user from network. Removes all of their friendships.
	 * 
	 * @param user - user to be removed from network
	 * @return true if user is removed from network, false otherwise
	 */
	public boolean removeUser(String user) {
		
		return false;
	}

	/**
	 * Sets a new user to be the center of the visualizer. With the new center user,
	 * all of their friendships are shown.
	 * 
	 * @param user - user to be set as center of visualizer
	 * @return true if user is set as center of visualizer, false otherwise
	 */
	public boolean setCenter(String user) {
		
		return false;
	}

	/**
	 * Method loads a .txt file of instructions that is understood by the program
	 * 
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public void loadFromFile(File file) throws FileNotFoundException {
		try (Scanner in = new Scanner(file)) {
			while (in.hasNextLine()) {
				String[] rows = in.nextLine().trim().split("[ ]+");
				switch (rows[0]) {
				case "a":
					if (rows.length == 3) {
						this.addFriends(rows[1], rows[2]);
					} else {
						this.addUser(rows[1]);
					}
					break;
				case "r":
					if (rows.length == 3) {
						this.removeFriends(rows[1], rows[2]);
					} else {
						this.removeUser(rows[1]);
					}
					break;
				case "s":
					this.setCenter(rows[1]);
					break;
				default:
					//TODO: Change too alert??
					System.out.println("No such command");
				}
			}
		}
	}

	/**
	 * Saves a .txt file with all of the instructions that the user did when using
	 * the program
	 * 
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public void saveToFile(File file) throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(file.getAbsolutePath())) {
			for (String row : this.commandList) {
				writer.println(row);
			}
		}
	}
}
