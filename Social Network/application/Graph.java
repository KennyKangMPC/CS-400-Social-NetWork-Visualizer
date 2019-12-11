package application;
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Programming Assignment 4 Package Manager, Graph class
// Files: 
// Course: 2019 Fall, CS 400
//
// Author: Kangqi Fu
// Email: kfu9@wisc.edu
// Lecturer's Name: Deb Deppeler
// Lecture's Number: 001
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    Kangqi (Kenny) Fu
 * 
 * Directed and unweighted graph implementation
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
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph
     * 
     * @param vertex the vertex to be added
     */
	public void addVertex(String vertex) {
		if ((vertex != null) && !allUsers.containsKey(vertex)) {
			this.allUsers.put(vertex, new Person(vertex));
		}
	}
	
	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is already in the graph
     * 
     * @param vertex the vertex to be removed
     */
	public void removeVertex(String vertex) {
		if ((vertex != null) && allUsers.containsKey(vertex)) {
			this.allUsers.remove(vertex);
			allUsers.forEach((k,v) -> this.removeEdgeS(k, vertex));
		}
	}
	
	/**
	 * 
	 * @param v1
	 * @param v2
	 */
	private void removeEdgeS(String v1, String v2) {
		if (v1 != null && v2 != null && this.allUsers.containsKey(v1)) {
			Person traker = this.getAllVertices().get(v1);
			Person trakPerson = null;
			for (Person nei : traker.getNeighbors()) {
				if (nei.getName().equals(v2)) {
					trakPerson = nei;
					break;
				}
			}
			traker.getNeighbors().remove(trakPerson);
		}
	}
	
	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * add vertex, and add edge, no exception is thrown.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
     * 
     * @param vertex1 the first vertex (src)
     * @param vertex2 the second vertex (dst)
	 */
	public void addEdge(String v1, String v2) {
		if (v1 != null && v2 != null) {
			boolean isIn1 = false;
			boolean isIn2 = false;
			
			for (Person v : this.allUsers.get(v1).getNeighbors()) {
				if (v.getName() == v2) {
					isIn1= true;
					break;
				}
			}
			
			for (Person v: this.allUsers.get(v2).getNeighbors()) {
				if (v.getName() == v1) {
					isIn2= true;
					break;
				}
			}

			if (!isIn1) {
				this.allUsers.get(v1).getNeighbors().add(this.allUsers.get(v2));
			}

			if (!isIn2) {
				this.allUsers.get(v2).getNeighbors().add(this.allUsers.get(v1));
			}
		}
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighed)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
	public void removeEdge(String v1, String v2) {
		if (v1 != null && v2 != null &&
			this.allUsers.containsKey(v1) &&
			this.allUsers.containsKey(v2) &&
			this.allUsers.get(v1).getNeighbors().contains(this.allUsers.get(v2)) &&
			this.allUsers.get(v2).getNeighbors().contains(this.allUsers.get(v1))){
			Person v1U = this.allUsers.get(v1);
			Person v2U = this.allUsers.get(v2);
			v2U.getNeighbors().remove(v1U);
			v1U.getNeighbors().remove(v2U);
		}
	}	
	
	/**
	 * 
	 */
	public HashMap<String, Person> getAllVertices() {
		return allUsers;
	}
	
	/**
     * Get all the neighbor (adjacent) vertices of a vertex
     * 
     * For the example graph, A->[B, C], D->[A, B]
     *     getAdjacentVerticesOf(A) should return [B, C].
     * 
     * In terms of packages, this list contains the immediate
     * dependencies of A and depending on your graph structure,
     * this could be either the predecessors or successors of A.
     * 
     * @param vertex the specified vertex
     * @return an List<String> of all the adjacent vertices for specified vertex
	 */
	public List<Person> getAdjacentVerticesOf(String vertex) {
		return this.allUsers.get(vertex).getNeighbors();
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.allUsers.size();
	}
	
}
