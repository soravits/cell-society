package main;

import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.MainMenu;
import javafx.application.Application;

/**
 *	Starts the program and sets the stage
 *  @author Soravit, Delia, Brian
 *
 */
public class Main extends Application {
	Stage stage;

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	public void start(Stage primaryStage) {	
		stage = primaryStage;
		Scene scene = new Scene(new MainMenu(primaryStage).setUpWindow());
		primaryStage.setTitle("Cell Society");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
	}
}