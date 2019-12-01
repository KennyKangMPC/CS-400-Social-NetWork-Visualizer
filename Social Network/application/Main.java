package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is a very basic JavaFX panel that contains a label in the top of 
 * the panel; a ComBox in the left panel that shows all my majors; an
 * ImageView of my Halloween selfie in the center; a simple button with
 * label "Done" in the bottom panel; a simple user interface control element
 * which asks for user name and password
 *  
 * @author Kangqi Fu
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
        primaryStage.setTitle("FX_BLANK");
        primaryStage.show();
	}

	/**
	 * The main method which is used to activate the entire program
	 * @param args (not used here)
	 */
	public static void main(String[] args) {
		   launch(args);
	}
}