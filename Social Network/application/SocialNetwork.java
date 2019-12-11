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
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;

/**
 * Back end data structure for Bakla Network
 * 
 * @author A-Team 15
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
		this.commandList.add("a " + sc + " " + de);
		boolean isAdd = true;
		boolean isRemoveUser = false;
		graph.addEdge(sc, de);
		Person scUser = this.graph.getAllVertices().get(sc);
		Person deUser = this.graph.getAllVertices().get(de);
		Main.drawEdge(isAdd, scUser, deUser, isRemoveUser);
		return true;
	}

	/**
	 * Removes a friendship between two users.
	 * 
	 * @param sc - first user in friendship
	 * @param de - second user in friendship
	 * @return true if friendship is removed from network, false otherwise
	 */
	public boolean removeFriends(String sc, String de) {
		this.commandList.add("r " + sc + " " + de);
		boolean isAdd = false;
		boolean isRemoveUser = false;
		graph.removeEdge(sc, de);
		Person scUser = this.graph.getAllVertices().get(sc);
		Person deUser = this.graph.getAllVertices().get(de);
		Main.drawEdge(isAdd, scUser, deUser, isRemoveUser);
		return true;
	}

	/**
	 * Adds a user into network. The user starts out with no friends.
	 * 
	 * @param user - user to be added in network
	 * @return true if user is added into network, false if otherwise
	 */
	public boolean addUser(String user) {
		if (this.graph.getAllVertices().containsKey(user)) {
			return false;
		}
		boolean isAdd = true;
		boolean isRemoveUser = false;
		this.commandList.add("a " + user);
		graph.addVertex(user);
		Person uPerson = this.graph.getAllVertices().get(user);
		Main.drawNode(uPerson, isAdd, isRemoveUser);
		return true;
	}

	/**
	 * Removes a user from network. Removes all of their friendships.
	 * 
	 * @param user - user to be removed from network
	 * @return true if user is removed from network, false otherwise
	 */
	public boolean removeUser(String user) {
		boolean isAdd = false;
		boolean isRemoveUser = false;
		this.commandList.add("r " + user);
		Person uPerson = this.graph.getAllVertices().get(user);
		graph.removeVertex(user);
		Main.drawNode(uPerson, isAdd, isRemoveUser);
		return true;
	}

	/**
	 * Sets a new user to be the center of the visualizer. With the new center user,
	 * all of their friendships are shown.
	 * 
	 * @param user - user to be set as center of visualizer
	 * @return true if user is set as center of visualizer, false otherwise
	 */
	public boolean setCenter(String user) {
		this.commandList.add("s " + user);
		Person centerUser = this.graph.getAllVertices().get(user);
		Main.reCenterDraw(centerUser);
		return false;
	}
	
	/**
	 * 
	 * @param user1
	 * @param user2
	 * @return
	 */
	public List<Person> getMutualFriends(String user1, String user2){
		Person p1 = this.graph.getAllVertices().get(user1);
		Person p2 = this.graph.getAllVertices().get(user2);
		
		List<Person> p1Friends = p1.getNeighbors();
		List<Person> p2Friends = p2.getNeighbors();
		
		// Find the common elements
		p1Friends.retainAll(p2Friends);
		return p1Friends;
	}
	
	/**
	 * 
	 * @param p
	 * @param c
	 */
	private void DFS(Person p, ArrayList<Person> c) {
		p.setIsVisited(true);
		c.add(p);
		for (Person f : p.getNeighbors()) {
			if (!f.getIsVisited()) {
				DFS(f,c);
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ArrayList<Person>> getCComponentsList() {
		List<ArrayList<Person>> components = new ArrayList<ArrayList<Person>> ();
		// Set all the nodes to be unvisited
		graph.getAllVertices().forEach((k,v) -> v.setIsVisited(false));
		
		for (Entry<String, Person> e : graph.getAllVertices().entrySet()) {
			if (!e.getValue().getIsVisited()) {
				ArrayList<Person> connected = new ArrayList<Person>();
				DFS(e.getValue(), connected);
				components.add(connected);
			}
		}
		return components;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumConnectedComponents() {
		return this.getCComponentsList().size();
	}
	
	/**
	 * This is the method for giving the shortest path
	 * @param start
	 * @param end
	 * @param n
	 * @return shortest path
	 */
	private List<Person> minPathBFS(Person start, Person end, int n) {
		// Set all to be unvisited
		graph.getAllVertices().forEach((k,v) -> v.setIsVisited(false));
		List<Person> visited = new ArrayList<Person>();
		List<Person> shortestPath = new ArrayList<Person>();
		List<ArrayList<Person>> allPath= new ArrayList<ArrayList<Person>>();
		minPathBFS(allPath, visited, start, end);
		if (allPath.size() == 0) {
			return shortestPath; // this should be empty list, no path alaert
		} else {
			shortestPath = allPath.get(0);
			
			for (List<Person> path : allPath) {
				if (path.size() < shortestPath.size()) {
					shortestPath = path;
				}
			}
		}
		return shortestPath;
	}
	
	private void minPathBFS(List<ArrayList<Person>> allPath, List<Person> visited, Person current, Person end){
        if(visited.contains(current)){
            return ;
        }
        
        if(end.equals(current)){
        	ArrayList<Person> path = new ArrayList<Person>();
            for(Person p : visited){
            	path.add(p);
            }
            path.add(end);
            allPath.add(path);
            return;
        }
        
        visited.add(current);
        for(Person child : current.getNeighbors()){
        	minPathBFS(allPath, visited,child, end);
        }
        visited.remove(current);
	}
	
	/**
	 * Get the shortest path between the two users
	 * 
	 * @param scUser
	 * @param deUser
	 * @return
	 */
	public List<Person> getShortestPath(String scUser, String deUser){
		Person scPerson = this.graph.getAllVertices().get(scUser);
		Person dePerson = this.graph.getAllVertices().get(deUser);
		int n = this.graph.size();
		List<ArrayList<Person>> cComponents = this.getCComponentsList();
		
		for (ArrayList<Person> cList : cComponents) {
			if (cList.contains(scPerson)) {
				if (cList.contains(dePerson)) {
					break;
				} else {
					return null; 
					// pop out a alert indicating they are not in the same components
				}
			}
		}
		
		return this.minPathBFS(scPerson, dePerson, n);
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
