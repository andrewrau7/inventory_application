package rau.inv_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/* Andrew Rau
   Student ID: 010266752
   C482 - Software 1
 */



/** This class starts the inventory application. */
public class InventoryApplication extends Application {

    /** This method loads the application.
     @param stage Initial stage is constructed by the platform.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("MainForm-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 425);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    // JAVA_DOC location:  inv_project/Inv_Project_Java_Doc/rau.inv_project/module-summary.html

    /** This method launches the application.
     Initial test data can be placed here.
     */
    public static void main(String[] args) {
        launch();
    }
}