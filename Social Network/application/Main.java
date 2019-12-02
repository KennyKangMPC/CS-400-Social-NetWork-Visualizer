package application;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 
 * @author Kangqi Fu
 */
public class Main extends Application {

//	private static final int WINDOW1_WIDTH = 300; // width of the scene1
//	private static final int WINDOW1_HEIGHT = 400; // height of the scene1

	private static final int WINDOW_WIDTH = 1200; // width of the scene2
	private static final int WINDOW_HEIGHT = 750; // height of the scene2
	private static final String APP_TITLE = "Social Network"; // title name of the APP

//	private static final String MY_IMAGE = "myGhost.jpg"; // image file path
	private SocialNetwork socialNetwork = new SocialNetwork();
	private static Stage stage = new Stage();
	static BorderPane root = new BorderPane();

	/**
	 * Creates comboBox in the left panel. It shows the a drop down box with three
	 * items that can be selected but does not do anything.
	 * 
	 * @return majorBox that stores three majors
	 */
//	private ComboBox<String> leftPanelComboBox() {
//		String myMajors[] = { "Computer Engineer", "Physics", "Math" };
//		ComboBox<String> majorBox = new ComboBox<String>(FXCollections.observableArrayList(myMajors));
//		return majorBox;
//	}

	/**
	 * Creates an ImageView of an Image in the center panel
	 * 
	 * @return ImageView object that contains my face
	 */
//	private ImageView centerPanelImage() {
//		ImageView imv = new ImageView();
//       	Image image = new Image(Main.class.getResourceAsStream(MY_IMAGE));
//       	imv.setImage(image);
//       	imv.setFitHeight(200); // Crop image
//       	imv.setFitWidth(200);  // Crop image
//       	return imv;
//	}
//	

	/**
	 * Load the file or write to the file by updating the friendship hash map and
	 * the command list.
	 * 
	 * @param isLoad option of reading or writing file
	 * @return the load write button
	 */
	private Button setupLoadWriteButton(Boolean isLoad) {
		Button lwButton = new Button();
		if (isLoad)
			lwButton.setText("Upload file");
		else
			lwButton.setText("Write to file");
		lwButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "*.*"));
			File selectedFile = fileChooser.showOpenDialog(Main.stage);
			if (selectedFile != null) {
				if (isLoad) {
					socialNetwork.loadFromFile(selectedFile);
				} else {
					socialNetwork.saveToFile(selectedFile);
				}
			} else {
				Alert info = new Alert(AlertType.INFORMATION);
				info.setTitle("WARNING");
				info.setContentText("Please Select a File");
				info.showAndWait();
			}
		});
		return lwButton;
	}
	
	private HBox addUserButton(HBox UIButtons) {
		//TODO: Check character whether is valid
		Button addUB = new Button("Add");
		TextField user = new TextField();
		user.setPromptText("username");
		UIButtons.getChildren().addAll(addUB, user);
		addUB.setOnAction(e->{
			socialNetwork.addUser(user.getText());
		});
		return UIButtons;
	}

	/**
	 * Creates a User Interface control element which here is a interface that allow
	 * user to enter user name and password
	 * 
	 * @return the user interface as a gridPane
	 */
	private GridPane LeftPanelUI() {

		GridPane grid = new GridPane();
		grid.setHgap(7.5); // set horizontal gap
		grid.setVgap(15); // set vertical gap

		HBox UIButtons = new HBox();
		UIButtons.setSpacing(5.0); // horizontal space between buttons
		
		HBox addUserBox = addUserButton(UIButtons);
		grid.add(UIButtons, 1, 1);
		UIButtons.getChildren().clear();
		
		
		
		
		
		Label partName = new Label("Control Panel");
		Label userName = new Label("User name:");
		TextField tfName = new TextField();
		Label Pwd = new Label("Password:");
		TextField pfPwd = new TextField();

		// Add and set location on the grid
		UIButtons.getChildren().addAll(btSubmit, btClear, btExit);

		Button loadButton = setupLoadWriteButton(true); // TODO: Call back end method
		Button writeButton = setupLoadWriteButton(false); // TODO: Call back end method

		grid.add(loadButton, 1, 4);
		grid.add(writeButton, 1, 6);

		return grid;
	}

	// TODO: Method

	/**
	 * This is an override class which is used to call the other method and start
	 * generating the interface
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		// Add a comboBox in the left panel
//       	root.setLeft(this.leftPanelComboBox());
//		
//       	// Add an ImageView of an Image in the center panel
//       	root.setCenter(this.centerPanelImage());
//       	
//       	// Add a Button in the bottom panel with the label "Done"
//       	root.setBottom(new Button("Done"));

		// Add a user interface control element of your choosing
		// in the right panel.

		Main.root.setLeft(this.LeftPanelUI());

		// Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * The main method which is used to activate the entire program
	 * 
	 * @param args (not used here)
	 */
	public static void main(String[] args) {
		launch(args);
	}

// TODO: Below are some trashes
//Scene 1 TODO: Options 2
//Label label1= new Label("This is the first scene");
//Button button1= new Button("Go to Visualizer");
//button1.setOnAction(e -> primaryStage.setScene(scene2));   
//VBox layout1 = new VBox(20);     
//layout1.getChildren().addAll(label1, button1);
//scene1= new Scene(layout1, WINDOW1_WIDTH, WINDOW1_HEIGHT);

//Scene 2
//Label label2= new Label("This is the second scene");
//Button button2= new Button("Go to Control Panel");
//button2.setOnAction(e -> primaryStage.setScene(scene1));
//VBox layout2= new VBox(20);
//layout2.getChildren().addAll(label2, button2);
//scene2= new Scene(layout2,WINDOW2_WIDTH,WINDOW2_HEIGHT);
//
//primaryStage.setScene(scene1);
//primaryStage.show();

}