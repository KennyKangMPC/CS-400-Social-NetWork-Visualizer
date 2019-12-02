package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SocialNetwork {
	
	private List<String> commandList;
	
	public SocialNetwork() {
		this.commandList = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param sc
	 * @param de
	 * @return
	 */
	public boolean addFriends(String sc, String de) {
		return false;
	}
	
	/**
	 * 
	 * @param sc
	 * @param de
	 * @return
	 */
	public boolean removeFriends(String sc, String de) {
		return false;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(String user) {
		return false;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean removeUser(String user) {
		return false;
	}
	
	/**
	 * 
	 * @param file
	 */
	public void loadFromFile(File file) {
		//TODO: Implement here for reading to the data structure
	}
	
	/**
	 * 
	 * @param file
	 */
	public void saveToFile(File file) {
		//TODO: Implement here for writing to the file
	}
}
