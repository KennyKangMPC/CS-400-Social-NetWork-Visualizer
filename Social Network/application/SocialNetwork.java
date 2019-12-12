///////////////////////////////////////////////////////////////////////////////
//Assignment Name: Social Network A3
//Filename: Person
//Author: A-Team 15
//Member:
//Kang Fu, 001, kfu9@wisc.edu
//Jamal Moussa, 002, jmoussa@wisc.edu
//Suraj Joottu, 001, sjoottu@wisc.edu
//Tejvir Mann, 001, tsmann@wisc.edu
//Michael Her, 002, mvher2@wisc.edu
//Due Date: Dec 11, 2019
//Other Source Credits: None
//Known Bugs: None, to the best of my knowledge
///////////////////////////////////////////////////////////////////////////////
//Filename: SocialNetwork
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
 * Back end data structure for Bakla Network. 
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
	 * Adds a friendship between two users. Calls methods 
	 * in the graph class in order to add it ot the graph
	 * data structure. 
	 * 
	 * @param sc - first user in friendship
	 * @param de - second user in friendship
	 * @return true if friendship is added into network, false otherwise
	 */
	public boolean addFriends(String sc, String de) {
	
		//checks if the strings are valid. 
		if (!this.graph.getAllVertices().containsKey(sc) || 
				!this.graph.getAllVertices().containsKey(de)) {
			return false;
		}
		
		//adds to commandList
		this.commandList.add("a " + sc + " " + de);
		boolean isAdd = true;
		boolean isRemoveUser = false;
		graph.addEdge(sc, de);
		
		//gets the right friends from graph. 
		Person scUser = this.graph.getAllVertices().get(sc);
		Person deUser = this.graph.getAllVertices().get(de);
		
		//adds the user to the screen. 
		Main.drawEdge(isAdd, scUser, deUser, isRemoveUser);
		return true;
	}

	/**
	 * Removes a friendship between two users.
	 * 
	 * @param sc - first user in friendship
	 * @param de - second user in friendship
	 * @return true - if friendship is removed from network, false otherwise
	 */
	public boolean removeFriends(String sc, String de) {
		//checks of the users are valid. 
		if (!this.graph.getAllVertices().containsKey(sc) || 
				!this.graph.getAllVertices().containsKey(de)) {
			return false;
		}
		
		//adds to the commandlist 
		this.commandList.add("r " + sc + " " + de);
		boolean isAdd = false;
		boolean isRemoveUser = false;
		
		//removes the user from the graph. 
		graph.removeEdge(sc, de);
		Person scUser = this.graph.getAllVertices().get(sc);
		Person deUser = this.graph.getAllVertices().get(de);
		
		//removes the user from the screen. 
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
		
		//checks of the user is contained
		if (this.graph.getAllVertices().containsKey(user)) {
			return false; // warning, it is already in it
		}
		
		//if the user is new, then it adds the command to 
		//the command list/ 
		boolean isAdd = true;
		boolean isRemoveUser = false;
		this.commandList.add("a " + user);
		
		//adds user to the graph structure
		graph.addVertex(user);
		
		//adds the user to be added to the screen. 
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
		
		//checks if the user is valid, and contained. 
		if (!this.graph.getAllVertices().containsKey(user)) {
			return false; 
		}
		
		//commmand added to the commandList 
		boolean isAdd = false;  
		boolean isRemoveUser = false;
		this.commandList.add("r " + user);
		
		//user removed from the screen. 
		Person uPerson = this.graph.getAllVertices().get(user);
		graph.removeVertex(user);
		Main.drawNode(uPerson, isAdd, isRemoveUser);
		return true;
	}

	/**
	 * Sets a new user to be the center of the visualizer. With the new center user,
	 * all of their friendships are shown.
	 * 
	 * In the case that the user/friend is selected to become the main friend. 
	 * 
	 * @param user - user to be set as center of visualizer
	 * @return true - if user is set as center of visualizer, false otherwise
	 */
	public boolean setCenter(String user) {
		
		//adds the command to the list 
		this.commandList.add("s " + user);
		
		//changes the graph and sets the user to the center. 
		Person centerUser = this.graph.getAllVertices().get(user);
		Main.reCenterDraw(centerUser);
		return false;
	}
	
	/**
	 * 
	 * This method gets all of the friends between two different
	 * friends. It checks if the users are valid, then it 
	 * gets all of the friends of both, then returns the ones 
	 * that are in common. 
	 * 
	 * @param user1
	 * @param user2
	 * @return p1Friends - the mutual friends between user1 and 2. 
	 */
	public List<Person> getMutualFriends(String user1, String user2){
		
		//gets the Person vertex's based on the names stored and that match.
		Person p1 = this.graph.getAllVertices().get(user1);
		Person p2 = this.graph.getAllVertices().get(user2);
		
		//initializes the list of the friends/elements of both. 
		List<Person> p1Friends = p1.getNeighbors();
		List<Person> p2Friends = p2.getNeighbors();
		
		// Find the common elements
		p1Friends.retainAll(p2Friends);
		return p1Friends;
	}
	
	/**
	 * This moethod gets the depth first algorithm. Based on the person
	 * and the arraylist c, which contains all of the person/users 
	 * friends. It returns the correct order based on depth. 
	 * 
	 * @param Person p - the person that starts the DFS. 
	 * @param ArrayList <person/ c - the list of friends of the person
	 */
	private void DFS(Person p, ArrayList<Person> c) {
		p.setIsVisited(true); //if visited, then sets to true. 
		c.add(p); //adds the first person. 
		for (Person f : p.getNeighbors()) { //goes through each neighbor. 
			if (!f.getIsVisited()) { //if not visited then DFS. 
				DFS(f,c); // the recursive algorithm. 
			}
		}
	}
	
	/**
	 * 
	 * This method returns a list of components. 
	 * 
	 * @return ArrayList components - 
	 */
	public List<ArrayList<Person>> getCComponentsList() {
		List<ArrayList<Person>> components = new ArrayList<ArrayList<Person>> ();
	
		// Set all the nodes to be unvisited
		graph.getAllVertices().forEach((k,v) -> v.setIsVisited(false));
		
		//Goes through and and does a DFS, adds all of the friends/elements
		//for a specific element to the component list. 
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
	 * Gets the size of the component list
	 * @return int size. 
	 */
	public int getNumConnectedComponents() {
		return this.getCComponentsList().size();
	}
	
	/**
	 * This is the method for giving the shortest path 
	 * using a Breath First Search. 
	 * 
	 * @param start
	 * @param end
	 * @param n
	 * @return shortest path
	 */
	private List<Person> minPathBFS(Person start, Person end, int n) {
		// Set all to be unvisited
		graph.getAllVertices().forEach((k,v) -> v.setIsVisited(false));
		
		//lists initialized
		List<Person> visited = new ArrayList<Person>();
		List<Person> shortestPath = new ArrayList<Person>();
		List<ArrayList<Person>> allPath= new ArrayList<ArrayList<Person>>();
		
		//recursive call that returns path, the visited list, and 
		//the starting and ending person for the shortest path. 
		minPathBFS(allPath, visited, start, end);
		
		if (allPath.size() == 0) {  //since 0 is the shorest distance. 
			return shortestPath; // this should be empty list, no path alert
		} else {
			shortestPath = allPath.get(0); //gets the distances. 
			
			for (List<Person> path : allPath) { //if the path is the shortests
				if (path.size() < shortestPath.size()) {
					shortestPath = path;
				}
			}
		}
		return shortestPath;
	}

	/*
	 * This is another method used in otder to get the shortest path. This method
	 * takes in the current person, and the last person, ass well as the current path. 
	 * 
	 * @param allPath - the entire path in a list
	 * @param visited - the current list of visited nodes.
	 * @param current - the current person
	 * @param end - the last person.
	 */
	private void minPathBFS(List<ArrayList<Person>> allPath, List<Person> visited, Person current, Person end){
        if(visited.contains(current)){ //this means that the search is over. 
            return ; 
        }
        
        if(end.equals(current)){ //adds each of the people visited to the path. 
        	ArrayList<Person> path = new ArrayList<Person>();
            for(Person p : visited){
            	path.add(p); //adds 
            }
            path.add(end); //adds the last person, returns. 
            allPath.add(path);
            return;
        }
        
        visited.add(current); //adds all of the neighbors and moves along to the next list of people.
        for(Person child : current.getNeighbors()){
        	minPathBFS(allPath, visited,child, end); //calls the method until the end is reached. 
        }
        visited.remove(current); //removes the current person from the visited list.
	} 
	
	/**
	 * Gets the shortest path between the two users. 
	 * 
	 * @param scUser - one user 
	 * @param deUser - the ending user. 
	 * @return - the shortest list between two users. 
	 */
	public List<Person> getShortestPath(String scUser, String deUser){
		//gets the person by looking through the vertices of the graph. 
		Person scPerson = this.graph.getAllVertices().get(scUser);
		Person dePerson = this.graph.getAllVertices().get(deUser);
		
		int n = this.graph.size(); //sets n to size. 
		
		List<ArrayList<Person>> cComponents = this.getCComponentsList(); //gets a list of components 
		
		for (ArrayList<Person> cList : cComponents) { //for every component, if its contained, then break. 
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
		
		try (Scanner in = new Scanner(file)) { //initializes scanner. 
			while (in.hasNextLine()) { //while the file has next line.
				String[] rows = in.nextLine().trim().split("[ ]+"); //clean up the file 
				switch (rows[0]) {  //different cases
				case "a": //checks the command, it true, then executes. 
					if (rows.length == 3) {
						String message = "Under Control Panel within Add Friend, type " 
								+ rows[1] + " in first text field and " 
								+ rows[2] + " in second text field";
						Main.alertMessage(message);  //implements command.
						Main.addRemoveFriendShip(true);
					} else {  
						this.addUser(rows[1]);
						String message = "Under Control Panel within Add, type " 
								+ rows[1] + " in first text field";
						Main.alertMessage(message); //implements command.
						Main.addRemoveUserButton(true, false);
					}
					break;
				case "r":  //checks the command, it true, then executes. 
					if (rows.length == 3) {
						String message = "Under Control Panel within remove Friend, type " 
								+ rows[1] + " in first text field and " 
								+ rows[2] + " in second text field";
						Main.alertMessage(message); //implements command.
						Main.addRemoveFriendShip(false);
					} else {
						String message = "Under Control Panel within remove, type " 
								+ rows[1] + " in first text field";
						Main.alertMessage(message); //implements command.
						Main.addRemoveUserButton(false, false);
					}
					break;
				case "s": //checks the command, it true, then executes. 
					this.setCenter(rows[1]);
					break;
				default:
					System.out.println("No such command"); //when command doesn't match
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
		
		//this initializes print writer to a file. 
		try (PrintWriter writer = new PrintWriter(file.getAbsolutePath())) {
			for (String row : this.commandList) {
				writer.println(row); //prints each line in commandList.
			}
		}
	}
}
