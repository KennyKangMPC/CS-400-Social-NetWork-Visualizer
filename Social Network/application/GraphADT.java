///////////////////////////////////////////////////////////////////////////////
//Assignment Name: Social Network A2
//Filename: GraphADT
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
import java.util.HashMap;
import java.util.List;

/**
 * Filename:   Graph.java
 * 
 * This interface is designed to give structure and specify methods for the 
 * implementation of the graph class. 
 * 
 * What happens in the graph class: 
 * Whenever a new user is added, or remove these methods 
 * are used to manipulate graph data structure which 
 * contains all of the users and their friendship 
 * relationships
 * 
 * @author ateam 15
 */
public interface GraphADT {

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
    public void addVertex(String vertex);

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
    public void removeVertex(String vertex);

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
    public void addEdge(String vertex1, String vertex2);
    
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
    public void removeEdge(String vertex1, String vertex2);
         
    /**
	 * This method gets all of the vertices. 
	 * 
	 * @return allUsers - all of the friends
	 */
    public HashMap<String, Person> getAllVertices();
    
    /**
     * Get all the friends (adjacent) friends of a vertex
 
     * In terms of packages, this list contains the immediate
     * friendships of A and depending on your graph structure,
     * this could be either the predecessors or successors of A.
     * 
     * @param friend of the specified vertex
     * @return an List<Person> of all the adjacent friends for specified friend
	 */
    public List<Person> getAdjacentVerticesOf(String vertex);
}
