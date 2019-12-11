package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class DragNodes extends Application {
    double mouse_x = 0.0;
    double mouse_y = 0.0;
    double circle_x = 10;
    double circle_y = 14;
    double height = 40;
    double width = 40;
    boolean circle_selected = false;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(300,100);
        this.createCircle(canvas);

        canvas.setOnMouseClicked(e-> this.select(e));
        canvas.setOnMouseMoved(e -> { if(this.circle_selected) this.move(e, canvas); });

        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //checks whether the mouse location is within the circle or not
    private void select(MouseEvent e) {
        double temp_mouse_x = e.getSceneX();
        double temp_mouse_y = e.getSceneY();
        double x_max = this.circle_x + this.width;
        double y_max = this.circle_y + this.height;
        boolean selected = temp_mouse_x >= this.circle_x && temp_mouse_x <= x_max // x-area
                    &&
                      temp_mouse_y >= this.circle_y && temp_mouse_y <= y_max; //y-area              

        if(circle_selected && selected) { 
            //deselect the circle if already selected
            circle_selected = false;
        }else {
            circle_selected = selected;
        }
        this.mouse_x = temp_mouse_x;
        this.mouse_y = temp_mouse_y;
    }

    //move circle
    public void move(MouseEvent e, Canvas canvas) {
            double change_x = e.getSceneX() - this.mouse_x;
            this.circle_x += change_x;
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            this.createCircle(canvas);
            this.mouse_x = e.getSceneX();
            this.mouse_y = e.getSceneY();
    }

    public void createCircle(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //outer circle
        Stop[] stops = new Stop[]{new Stop(0, Color.LIGHTSKYBLUE), new Stop(1, Color.BLUE)};
        LinearGradient gradient = new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, stops);
        gc.setFill(gradient);
        gc.fillOval(this.circle_x, this.circle_y, this.width, this.height);
        gc.translate(0, 0);
        gc.fill();
        gc.stroke();

        // Inner circle
        stops = new Stop[]{new Stop(0, Color.BLUE), new Stop(1, Color.LIGHTSKYBLUE)};
        gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        gc.setFill(gradient);
        gc.fillOval(this.circle_x + 3, this.circle_y + 3, this.width - 6, this.height - 6);
        gc.fill();
        gc.stroke();    
    }

    public static void main(String[] args) {
        launch(args);
    }

}