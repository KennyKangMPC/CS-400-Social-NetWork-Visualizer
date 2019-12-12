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

package application;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Filename:   Graph.java
 * 
 * This class is designed in order to implement the backend 
 * data structure for the SocialNetwork. 
 * 
 * Whenever a new user is added, or remove these methods 
 * are used to manipulate graph data structure which 
 * contains all of the users and their friendship 
 * relationships
 * 
 * @author ateam 15
 */
public class Graph implements GraphADT {
	
	// This stores all the coding
	private HashMap<String, Person> allUsers;
	
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() {
		allUsers = new HashMap<>();
	}
	
	/**
     * Add new friend to the graph.
     *
     * If friend is null or already exists,
     * method ends without adding a friend or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. friend is non-null
     * 2. friend is not already in the graph
     * 
     * @param friend to the friend to be added
     */
	public void addVertex(String vertex) {
		if ((vertex != null) && !allUsers.containsKey(vertex)) {
			this.allUsers.put(vertex, new Person(vertex));
		}
	}
	
	/**
     * Remove a friend and all associated 
     * friendships from the graph.
     * 
     * If friend is null or does not exist,
     * method ends without removing a friend, friendships, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. friend is non-null
     * 2. friend is already in the graph
     * 
     * @param friend - the friend to be removed
     */
	public void removeVertex(String vertex) {
		if ((vertex != null) && allUsers.containsKey(vertex)) {
			this.allUsers.remove(vertex);
			allUsers.forEach((k,v) -> this.removeEdgeS(k, vertex));
		}
	}
	
	/**
	 * 
	 * This method removes all the friendships for a 
	 * particular friend. Runs through each friend 
	 * that has a friendship, then removes the friendship
	 * 
	 * @param v1
	 * @param v2
	 */
	private void removeEdgeS(String v1, String v2) {
		
		//first checks if if the input is valud and contained
		if (v1 != null && v2 != null && this.allUsers.containsKey(v1)) {
			Person traker = this.getAllVertices().get(v1);
			Person trakPerson = null;
			for (Person nei : traker.getNeighbors()) { //goes through each neighbor
				if (nei.getName().equals(v2)) { //if person is neighbor, you break and 
					trakPerson = nei; //remove the edge between vl and the friend
					break;
				}
			}
			
			//removes person from list
			traker.getNeighbors().remove(trakPerson); 
		}
	}
	
	/**
     * Add the edge from friend1 (v1) to friend2 (v2)
     * to this undirected graph. 
     * If either friend does not exist,
     * add friend, and add friend, no exception is thrown.
     * If the friendship exists in the graph,
     * no friendship is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the friendship is not in the graph
     * 
     * @param vertex1 the first friend (src)
     * @param vertex2 the second friend (dst)
	 */
	public void addEdge(String v1, String v2) {
		
		//checks if the inputs are valid. 
		if (v1 != null && v2 != null) {
			boolean isIn1 = false;
			boolean isIn2 = false;
			
			//runs through the each of the friends of v1, if yes, break. 
			for (Person v : this.allUsers.get(v1).getNeighbors()) {
				if (v.getName() == v2) {
					isIn1= true;
					break;
				}
			}
			
			//runs through each of the friends of v2, if yes, break. 
			for (Person v: this.allUsers.get(v2).getNeighbors()) {
				if (v.getName() == v1) {
					isIn2= true;
					break;
				}
			}

			//checks if eiehter if the booleans are false, if false, then doesn't
			//add since already friendship
			if (!isIn1) {
				this.allUsers.get(v1).getNeighbors().add(this.allUsers.get(v2));
			}

			if (!isIn2) {
				this.allUsers.get(v2).getNeighbors().add(this.allUsers.get(v1));
			}
		}
	}
	
	/**
     * Remove the edge from friend1 to friend2
     * from this graph.  (edge is directed and unweighed)
     * If either friend does not exist,
     * or if an edge from friend1 to friend2 does not exist,
     * no friendship is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither friend is null
     * 2. both friends are in the graph 
     * 3. the edge from friend1 to friend2 is in the graph
     * 
     * @param vertex1 the first friend
     * @param vertex2 the second friend
     */
	public void removeEdge(String v1, String v2) {
		
		//checks if friends are valid 
		//and if they are actually friends.
		if (v1 != null && v2 != null &&
			this.allUsers.containsKey(v1) &&
			this.allUsers.containsKey(v2) &&
			this.allUsers.get(v1).getNeighbors().contains(this.allUsers.get(v2)) &&
			this.allUsers.get(v2).getNeighbors().contains(this.allUsers.get(v1))){
			
			//if true, then removes from each of these
			Person v1U = this.allUsers.get(v1);
			Person v2U = this.allUsers.get(v2);
			v2U.getNeighbors().remove(v1U);
			v1U.getNeighbors().remove(v2U);
		}
	}	
	
	/**
	 * This method gets all of the vertices. 
	 * 
	 * @return allUsers - all of the friends
	 */
	public HashMap<String, Person> getAllVertices() {
		return allUsers;
	}
	
	/**
     * Get all the friends (adjacent) friends of a vertex
 
     * In terms of packages, this list contains the immediate
     * friendships of A and depending on your graph structure,
     * this could be either the predecessors or successors of A.
     * 
     * @param friend of the specified vertex
     * @return an List<Person> of all the adjacent friends for specified friend
	 */
	public List<Person> getAdjacentVerticesOf(String vertex) {
		return this.allUsers.get(vertex).getNeighbors();
	}
	
	/**
	 * This returns the number of friends or the size 
	 * of the list 
	 * 
	 * @return int size - the size. 
	 */
	public int size() {
		return this.allUsers.size();
	}
	
}
