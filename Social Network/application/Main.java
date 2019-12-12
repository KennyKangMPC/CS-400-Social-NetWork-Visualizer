package application;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Main JavaFX class that is the front-end of Bakla Network
 * 
 * @author A-Team 15
 */
public class Main extends Application {

	private static final int WINDOW_WIDTH = 1200; // width of the scene2
	private static final int WINDOW_HEIGHT = 750; // height of the scene2
	private static final String APP_TITLE = "Social Network"; // title name of the APP
	private static SocialNetwork socialNetwork = new SocialNetwork(); // back end
	private static Stage stage = new Stage();
	static BorderPane root = new BorderPane();
	static Canvas canvas;
	static GraphicsContext gc;
	static HashMap<Person, double[]> mousePositions = new HashMap<Person, double[]>(); // for mouse positions of placed
																						// circles

	/**
	 * Method displays an alert with given message indicating some sort of
	 * information that must be understood by user of program.
	 * 
	 * @param message - message of information that user can use to debug to
	 *                continue using program
	 */
	static void alertMessage(String message) {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setTitle("INFORMATION and ALERT");
		info.setHeight(400);
		info.setWidth(300);
		info.setContentText(message);
		info.showAndWait();
	}

	/**
	 * Method displays an alert with help message indicating information on how to
	 * use the program
	 */
	private void helpMessage() {
		Alert info = new Alert(AlertType.INFORMATION);

		info.setTitle("Help");
		info.setHeight(720);
		info.setWidth(300);
		info.setHeaderText("Help for Social Network");

		String help = "Upload file: Allows user to upload a file of commands for program to au" + "tomate through. \n"
				+ "Write to File: Allows user to save a text file of commands that "
				+ "were used in the programs uptime. \n"
				+ "Set Center: Given an existing social network, allows user to set "
				+ "a person to center, ie. shows the social network for that given user."
				+ " An alternate method is to choose a center by clicking on the circle" + " itself. \n"
				+ "Add [username]: Type the desired name of new member of the network,"
				+ " then click 'Add' followed by clicking anywhere in the panel to draw" + " the circle. \n"
				+ "Remove [username]: Type in an already exsiting member name, then "
				+ "click 'remove'; this will delete that member. \n"
				+ "Add [user1][user2]: Type in two member names that already exist in"
				+ " the network, then click 'Add'; this will draw a line between the" + " members. \n"
				+ "Remove [user1][user2]:Type in two member names that already exist"
				+ " in the network, then click 'Remove'; this will delete the line that"
				+ " already exists between the members." + "\n\n\n"
				+ "Notes: To add users to the graph, type in the username and click \"Add\"."
				+ " Users do not show on the GUI automatically.\r\n"
				+ "To visualize the network, click anywhere on the visulaization panel to "
				+ "place the user. Then continue "
				+ "doing this with users after adding. To set a user as central user click"
				+ " on any user from the visualization panel."
				+ "The new screen with central user is not interactable and is just a visual."
				+ " However, the main visualization panel" + "is still clickable to set new central user. ";

		info.setContentText(help);
		info.showAndWait();
	}

	/**
	 * Method for pop up for the end of the program. Method is called when user
	 * clicks close button. Offers to exit without saving file of commands or exit
	 * with saving file of commands that were inputed during runtime.
	 */
	public void lastMessage() {
		BorderPane pane = new BorderPane();
		Stage exitStage = new Stage();

		// first button
		Button save = new Button("Save and Exit");
		save.setFont(Font.font(java.awt.Font.SANS_SERIF, 13));

		save.setOnAction(e -> {
			// for choosing location for saving file and creating file
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Text File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "."));
			File selectedFile = fileChooser.showSaveDialog(Main.stage);
			if (selectedFile != null) {
				try {
					socialNetwork.saveToFile(selectedFile);
				} catch (FileNotFoundException e1) {
					alertMessage("Not a valid file!");
				}
			}
			System.exit(0);
		});

		// button for not saving commands
		Button save1 = new Button("Exit without saving");
		save1.setFont(Font.font(java.awt.Font.SANS_SERIF, 13));

		save1.setOnAction(e -> {
			System.exit(0);
		});

		// set pange, scene, stage
		pane.setLeft(save);
		pane.setRight(save1);
		exitStage.setTitle("Exit");
		Scene exitScene = new Scene(pane, 265, 75);
		exitStage.setScene(exitScene);
		exitStage.show();
	}

	/**
	 * Method that draws edge between two users or removes edges. Functionality of
	 * method depends on what user wants to do between two friends. This method will
	 * not draw method if one or more of the users is not in the network already and
	 * will not remove edges between if users are non-existent in network.
	 * 
	 * @param isAdd     - true if adding friendship between two users, false
	 *                  otherwise
	 * @param scUser    - first user in friendship
	 * @param deUser    - second user in friendship
	 * @param isRemoveU - used for remove user, true if removing user from network
	 *                  where friendships of that user must be deleted
	 */

	static void drawEdge(boolean isAdd, Person scUser, Person deUser, boolean isRemoveU) {
		double d = 80; // diameter of circle
		if (isAdd) { // adding friendship
			gc.setStroke(Color.BLUE);
			gc.strokeLine(deUser.getX(), deUser.getY(), scUser.getX(), scUser.getY());
		} else { // removing friendship, idea is to cover up the line that was previously drawn
					// with a line
			// larger than the connecting line
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(2.0);
			gc.strokeLine(deUser.getX(), deUser.getY(), scUser.getX(), scUser.getY());
			gc.setFill(Color.color(0, 0, 1));
			// for removing all edges for a user removed from network
			if (!isRemoveU)
				gc.fillOval(deUser.getX() - (d / 2 - deUser.getTextX()), deUser.getY() - (d / 2 - deUser.getTextY()), d,
						d);
			gc.fillOval(scUser.getX() - (d / 2 - scUser.getTextX()), scUser.getY() - (d / 2 - scUser.getTextY()), d, d);
			gc.setFill(Color.color(1, 0, 0));
			gc.fillText(scUser.getName(), scUser.getX(), scUser.getY());
			if (!isRemoveU)
				gc.fillText(deUser.getName(), deUser.getX(), deUser.getY());
		}
	}

	/**
	 * Creates a new GUI that displays all of the friendships for a given person in
	 * network.
	 * 
	 * @param user - user that is central user
	 */
	static void reCenterDraw(Person user) {
		String message = "Click on " + user.getName() + " circle for re-center";
		alertMessage(message);
		Stage centerStage = new Stage();
		BorderPane root1 = new BorderPane();
		Canvas newCan = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		root1.getChildren().add(newCan);
		GraphicsContext gc1 = newCan.getGraphicsContext2D();
		double d = 80; // diameter of circle
		List<Person> friends = user.getNeighbors(); // friends of given user
		friends.add(user);
		// recreate all of the first canvas without friends of given user
		for (Person mem : friends) {
			mem.setCount(0);
			if (mem.getCount()) {
				gc1.setFill(Color.color(0, 0, 1)); // TODO: May generate random if have time
				gc1.fillOval(mem.getX(), mem.getY(), d, d);
				gc1.setFill(Color.color(1, 0, 0));
				gc1.fillText(mem.getName(), mem.getX() + (d / 2 - mem.getTextX()),
						mem.getY() + (d / 2 - mem.getTextY()));
			}
		}

		friends.remove(user); // dont want a self line to itself

		// redrawing all of the lines between friends
		for (Person mem : friends) {
			gc1.setStroke(Color.BLUE);
			gc1.strokeLine(mem.getX() + 25, mem.getY() + 20, user.getX() + 25, user.getY() + 20);
		}

		Scene newScene = new Scene(root1, WINDOW_WIDTH, WINDOW_HEIGHT);
		centerStage.setScene(newScene);
		centerStage.show();
	}

	/**
	 * This draws a circle on the canvas for the user to visualize. Left click once
	 * on the canvas to draw circle. The circle is able to be clicked to set to
	 * central user for viewing only friends of central user
	 * 
	 * @param thisGuy      - user in network that is going to be shown on visualizer
	 * @param isAdd        - true if being added to network, false
	 * @param isRemoveUser - true if being removed from network, false otherwise
	 */
	static void drawNode(Person thisGuy, boolean isAdd, boolean isRemoveUser) {
		double d = 80.0; // diameter of the circle
		Text usName = new Text(thisGuy.getName());
		// bounds of text box so can be covered up properly when removing
		double centerX = usName.getLayoutBounds().getCenterX();
		double centerY = usName.getLayoutBounds().getCenterY();
		thisGuy.setTextX(centerX);
		thisGuy.setTextY(centerY);

		gc = canvas.getGraphicsContext2D();
		// add circle to canvas and add mouse event to it
		if (isAdd) {
			canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (e.getClickCount() > 1) {
						if (thisGuy.getCount()) {
							gc.setFill(Color.color(0, 0, 1)); // TODO: May generate random if have time
							gc.fillOval(e.getX(), e.getY(), d, d);
							gc.setFill(Color.color(1, 0, 0));
							gc.fillText(thisGuy.getName(), e.getX() + (d / 2 - centerX), e.getY() + (d / 2 - centerY));
							thisGuy.setX(e.getX() + (d / 2 - centerX));
							thisGuy.setY(e.getY() + (d / 2 - centerY));
						}
					}
				}
			});
		} else {
			double pX = thisGuy.getX();
			double pY = thisGuy.getY();
			gc.setFill(Color.WHITE);
			gc.fillRect(pX - (d / 2 - centerX), pY - (d / 2 - centerY), d + 2, d + 2);
			if (thisGuy.getNeighbors().size() != 0) {
				for (Person friend : thisGuy.getNeighbors()) {
					isRemoveUser = true;
					Main.drawEdge(isAdd, friend, thisGuy, isRemoveUser);
				}
			}
		}
		clickOnNode(); // makes node have ablility to be clicked
	}

	/**
	 * Method that helps node on screen have the ability to be clicked.
	 */
	static void clickOnNode() {
		gc = canvas.getGraphicsContext2D();
		// mouse event
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouse) {
				boolean isClicked = false;

				Person choice = null;
				for (Entry<String, Person> e : socialNetwork.graph.getAllVertices().entrySet()) {

					double[] pos = { e.getValue().getX() + 40, e.getValue().getX() - 40, e.getValue().getY() + 40,
							e.getValue().getY() - 40 }; // store mouse position

					mousePositions.put(e.getValue(), pos);
					// if mouse is in valid position to click a circle
					for (Entry<Person, double[]> f : mousePositions.entrySet()) {
						if ((mouse.getX() <= f.getValue()[0] && mouse.getX() >= f.getValue()[1])
								&& (mouse.getY() <= f.getValue()[2] && mouse.getY() >= f.getValue()[3])) {
							isClicked = true;
							choice = f.getKey();
							break;
						}
					}
				}
				// for being set as central user
				if (isClicked && mouse.getClickCount() >= 1) {
					Main.reCenterDraw(choice);
				}
			}
		});
	}

	/**
	 * Load the file or write to the file by updating the friendship hash map and
	 * the command list.
	 * 
	 * @param isLoad option of reading or writing file, true for loading from file,
	 *               false for writing to file
	 * @return the load or write button
	 */
	private Button setupLoadWriteButton(Boolean isLoad) {
		Button lwButton = new Button();
		if (isLoad)
			lwButton.setText("Upload file");
		else
			lwButton.setText("Write to file");
		lwButton.setPrefSize(100, 50);

		lwButton.setStyle("-fx-background-color: MediumSeaGreen;" + "-fx-font-size: 10pt");

		// for opening a resource for program to read
		lwButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "*.*"));
			File selectedFile = null;
			if (isLoad) {
				selectedFile = fileChooser.showOpenDialog(Main.stage); // dialog for loading
			} else {
				selectedFile = fileChooser.showSaveDialog(Main.stage); // dialog for saving
			}

			// error cases
			if (selectedFile != null) {
				if (isLoad) {
					try {
						socialNetwork.loadFromFile(selectedFile);
					} catch (FileNotFoundException e1) {
					}
				} else {
					try {
						socialNetwork.saveToFile(selectedFile);
					} catch (FileNotFoundException e1) {
						alertMessage("No Such File!!");
					}
				}
			} else {
				alertMessage("Please Select a File");
			}
		});
		return lwButton;
	}

	/**
	 * Functionality of the add and remove button
	 * 
	 * @param isAdd    - to add a user
	 * @param isCenter - to have user set as center of visualizer
	 * @return HBox with the buttons of add or remove depending on what is clicked
	 */
	static HBox addRemoveUserButton(Boolean isAdd, Boolean isCenter) {
		String text;
		// for what was chosen by user
		if (isAdd) {
			text = "Add";
		} else if (isCenter) {
			text = "Set Center";
		} else {
			text = "Remove";
		}
		// new fields
		HBox UIButtons = new HBox();
		Button addUB = new Button(text);
		addUB.setPrefSize(100, 50);
		TextField user = new TextField();
		user.setPromptText("username");

		user.setPrefSize(100, 50);
		UIButtons.getChildren().addAll(addUB, user);

		addUB.setStyle("-fx-background-color: MediumSeaGreen;" + "-fx-font-size: 10pt");

		// acting of button
		addUB.setOnAction(e -> {
			if (!checkInput(user.getText())) {
				alertMessage("Invalid Character");
			} else {
				if (isAdd) { // for adding user into network
					boolean isDone = socialNetwork.addUser(user.getText());
					if (!isDone) {
						alertMessage("The user is already in the graph");
					} else {
						alertMessage(
								"Pick a spot under the Visualization Panel and click for adding user at that spot");
					}
				} else { // for user not present in network
					boolean isDone = socialNetwork.removeUser(user.getText());
					if (!isDone) {
						alertMessage("The user is not in the graph");
					}
				}
			}
		});
		return UIButtons;
	}

	/**
	 * Checks the inputting format
	 * 
	 * @param in
	 * @return
	 */
	private static boolean checkInput(String in) {
		boolean pass = true;
		for (char ch : in.toCharArray()) {
			if (!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '\'' && ch != '_') {
				pass = false;
				break;
			}
		}
		return pass;
	}

	/**
	 * Stores the buttons with text fields
	 * 
	 * @param isAdd - if user wants to add or remove person
	 * @return HBox that stores the buttons with text fields
	 */
	static HBox addRemoveFriendShip(Boolean isAdd) {
		String text;
		if (isAdd) {
			text = "Add Friend";
		} else {
			text = "Remove Friend";
		}
		HBox UIButtons = new HBox();
		Button addUB = new Button(text);
		addUB.setPrefSize(100, 50);

		TextField user1 = new TextField();
		user1.setPromptText("user1");

		TextField user2 = new TextField();
		user2.setPromptText("user2");

		user1.setPrefSize(100, 50);
		user2.setPrefSize(100, 50);

		addUB.setStyle("-fx-background-color: MediumSeaGreen;" + "-fx-font-size: 8pt");

		addUB.setOnAction(e -> {
			if (isAdd) {
				boolean isDone = socialNetwork.addFriends(user1.getText(), user2.getText());
				if (!isDone) {
					alertMessage("One or both of the users are not in the graph, make sure to add both first");
				}
			} else {
				boolean isDone = socialNetwork.removeFriends(user1.getText(), user2.getText());
				if (!isDone) {
					alertMessage(
							"One or both of the users are not in the graph or there is no friendship between this two"
									+ "users, make sure to add the friendship first before removing the firendship");
				}
			}
		});
		UIButtons.getChildren().addAll(addUB, user1, user2);
		return UIButtons;
	}

	/**
	 * Carries out action of help button from GUI. Shows help message for user.
	 * 
	 * @return HBox with help button for GUI
	 */
	private HBox helpButton() {
		HBox UIButtons = new HBox();

		Button addUB = new Button("Help");
		addUB.setPrefSize(100, 50);

		addUB.setOnAction(e -> {
			helpMessage();
		});

		UIButtons.getChildren().add(addUB);

		return UIButtons;
	}

	/**
	 * Creates a User Interface control element which here is a interface that allow
	 * user to enter user name and password
	 * 
	 * @return the user interface as a gridPane /** Creates a User Interface control
	 *         element which here is a interface that allow user to enter user name
	 *         and password
	 * 
	 * @return the user interface as a gridPane
	 */
	private GridPane LeftPanelUI() {

		GridPane grid = new GridPane();
		grid.setHgap(7.5); // set horizontal gap
		grid.setVgap(15); // set vertical gap

		// UIButtons.setSpacing(5.0); // horizontal space between buttons

		HBox addUserBox = addRemoveUserButton(true, false);
		addUserBox.setSpacing(5.0); // horizontal space between buttons

		HBox removeUserBox = addRemoveUserButton(false, false);
		removeUserBox.setSpacing(5.0);

		HBox centerUserBox = addRemoveUserButton(false, true);
		centerUserBox.setSpacing(5.0);

		HBox addFriendShipBox = addRemoveFriendShip(true);
		addFriendShipBox.setSpacing(5.0); // horizontal space between buttons

		HBox removeFriendShipBox = addRemoveFriendShip(false);
		removeFriendShipBox.setSpacing(5.0); // horizontal space between buttons

		HBox helpButtonBox = helpButton();
		helpButtonBox.setSpacing(5.0);

		Button loadButton = setupLoadWriteButton(true); // TODO: Call back end method
		Button writeButton = setupLoadWriteButton(false); // TODO: Call back end method

		HBox shortestPath = shortestPath();
		shortestPath.setSpacing(5.0);

		grid.add(loadButton, 1, 3);
		grid.add(writeButton, 1, 5);
		grid.add(centerUserBox, 1, 7);
		grid.add(addUserBox, 1, 9);
		grid.add(removeUserBox, 1, 11);
		grid.add(addFriendShipBox, 1, 13);
		grid.add(removeFriendShipBox, 1, 15);
		grid.add(shortestPath, 1, 17);
		grid.add(helpButtonBox, 1, 19);
		return grid;
	}

	/**
	 * HBox contains buttons and text fields about finding the shortest path between
	 * the two users in the graph given by the user of this interface
	 * 
	 * @return
	 */
	private HBox shortestPath() {
		HBox UIButtons = new HBox();

		Button addUB = new Button("Shortest Path between");
		addUB.setPrefSize(100, 50);

		TextField user = new TextField();
		user.setPromptText("Start user");

		TextField user2 = new TextField();
		user2.setPromptText("Terminal user");

		user.setPrefSize(100, 50);
		user2.setPrefSize(100, 50);

		addUB.setStyle("-fx-background-color: MediumSeaGreen;" + "-fx-font-size: 8.5pt");

		addUB.setOnAction(e -> {
			String path = "The shortest path between: " + user.getText() + " and " + user2.getText() + " is: ";
			for (Person fri : socialNetwork.getShortestPath(user.getText(), user2.getText())) {
				path += fri.getName();
			}
			int numUser = socialNetwork.graph.size();
			int numCComponent = socialNetwork.getCComponentsList().size();

			path += "\nnumber of user is: " + numUser + ", and number of connected " + "components is: "
					+ numCComponent;
			alertMessage(path);
		});

		UIButtons.getChildren().addAll(addUB, user, user2);
		return UIButtons;
	}

	/**
	 * This is an override class which is used to call the other method and start
	 * generating the interface
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Text text1 = new Text(10, 30, "Control Panel");
		text1.setFill(Color.CHOCOLATE);
		text1.setFont(Font.font(java.awt.Font.SERIF, 25));

		Text text2 = new Text(500, 30, "Visualization Panel");
		text2.setFill(Color.DARKCYAN);
		text2.setFont(Font.font(java.awt.Font.SERIF, 25));
		root.getChildren().addAll(text1, text2);

		canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		root.getChildren().add(canvas);

		Main.root.setLeft(this.LeftPanelUI());
		root.setStyle("-fx-background-color: white;");
		primaryStage.setTitle(APP_TITLE);
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				lastMessage();
			}
		});
	}

	/**
	 * The main method which is used to activate the entire program
	 * 
	 * @param args (not used here)
	 */
	public static void main(String[] args) {
		launch(args);
	}
}