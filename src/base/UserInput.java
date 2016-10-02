package base;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public abstract class UserInput {
	public static final int INPUT_MENU_WIDTH = 700;
	public static final int INPUT_MENU_HEIGHT = 600;	
	private Alert alert;
	public static Stage stage;
	public Pane segWindow;
	private GridPane grid = new GridPane();
	private Scene mySimScene;
	private Spinner<Integer> gridSizeSpinner;

	private String buttonFill = "-fx-background-color: linear-gradient(#0079b3, #00110e);" + 
			"-fx-background-radius: 20;" + 
			"-fx-text-fill: white;";
    private String overButton = "-fx-background-color: linear-gradient(#00110e, #0079b3);" + 
            "-fx-background-radius: 20;" + 
            "-fx-text-fill: white;";

	public UserInput(Stage s){
		stage = s;
		stage.setScene(new Scene(setUpWindow()));
		stage.show();
	}

	private Parent setUpWindow() {
		segWindow = new Pane();
		
		segWindow.setPrefSize(INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);
		
		segWindow.getChildren().add(setBackground()); 
		
		Text prompt = new Text(50, 60, "Read from an XML File");
        prompt.setFont(Font.font ("Verdana", FontWeight.BOLD, 25));
        prompt.setFill(Color.WHITE);
		segWindow.getChildren().add(prompt);
		
		xmlButton();
		

		Text otherOption = new Text(50, 150, "Or Choose your own Parameters");
        otherOption.setFont(Font.font ("Verdana", FontWeight.BOLD, 25));
        otherOption.setFill(Color.WHITE);
        segWindow.getChildren().add(otherOption);
        
		manualButton();
		
		return segWindow;
	}
	
	public GridPane getGrid(){
		return grid;
	}
	
	public int getGridSize(){
		return gridSizeSpinner.getValue();
	}
	
	public ImageView setBackground(){
		Image background = new Image(getClass().getClassLoader()
				.getResourceAsStream("BackgroundCellSoc.jpg")); 
		ImageView backgroundImage = new ImageView(background);
		backgroundImage.setFitWidth(INPUT_MENU_WIDTH + 50);
		backgroundImage.setFitHeight(INPUT_MENU_HEIGHT);
		return backgroundImage;
	}

	public void xmlButton(){

		Button readXML = new Button("Run with XML");
		readXML.setStyle(buttonFill);
        readXML.setOnMouseEntered(e -> mouseIn(readXML));
        readXML.setOnMouseExited(e -> mouseOut(readXML));
		readXML.setOnMouseClicked(e -> startSimulation());
		readXML.setTranslateX(40);
		readXML.setTranslateY(80);
		segWindow.getChildren().add(readXML);
	}
	
	public void manualButton(){
		String buttonFill = "-fx-background-color: linear-gradient(#0079b3, #00110e);" + 
				"-fx-background-radius: 20;" + 
				"-fx-text-fill: white;";

		Button inputManual = new Button("Input values here");
		inputManual.setStyle(buttonFill);
        inputManual.setOnMouseEntered(e -> mouseIn(inputManual));
        inputManual.setOnMouseExited(e -> mouseOut(inputManual));
		inputManual.setOnMouseClicked(e -> setManualGrid());
		inputManual.setTranslateX(40);
		inputManual.setTranslateY(160);
		segWindow.getChildren().add(inputManual);
	}
	
    private void mouseIn(Button b){
    	b.setStyle(overButton);
    }
    
    private void mouseOut(Button b){
    	b.setStyle(buttonFill);
    }
	
	public abstract Scene initSimulation();
	
	public abstract void startManualSimulation();

	public void selectGridSize() {
		gridSizeSpinner = new Spinner<>(10, 100, 50, 5);
		gridSizeSpinner.setEditable(true);
		grid.add(new Label("Size of Square Grid"), 0, 4);
		grid.add(gridSizeSpinner, 1, 4);
		
	}
	
	public void startSimulation() {
		mySimScene = initSimulation();
		stage.setScene(mySimScene);
		stage.show();
	}
	
	public void setManualGrid(){
		mySimScene = new Scene(grid, INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);
		grid.setStyle("-fx-background-color: #a0c6ed;");
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		manualInput();
		stage.setScene(mySimScene);
		stage.show();
	}
	
	public Button startManualButton(String s){
		Button beginSim = new Button(s);
		beginSim.setOnMouseClicked(e -> startManualSimulation());
		return beginSim;
	}
	
	public abstract void manualInput();

}





