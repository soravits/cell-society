package base;

import controller.MainMenu.MenuItem;
import controller.MainMenu.OptionContainer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class UserInput {
    public static final int INPUT_MENU_WIDTH = 700;
    public static final int INPUT_MENU_HEIGHT = 600;	
	private Alert alert;
	public static Stage stage;
	public Pane inputWindow;
	
	public UserInput(Stage s){

        stage.setScene(new Scene(setUpWindow()));
	}
	
	private Parent setUpWindow() {
		 	inputWindow = new Pane();
	        inputWindow.setPrefSize(INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);
	        Image background = new Image(getClass().getClassLoader()
	                                     .getResourceAsStream("BackgroundCellSoc.jpg")); 
	        ImageView backgroundImage = new ImageView(background);
	        backgroundImage.setFitWidth(INPUT_MENU_WIDTH + 50);
	        backgroundImage.setFitHeight(INPUT_MENU_HEIGHT);
	        inputWindow.getChildren().add(backgroundImage); 
	        Text xmlPrompt = new Text(50, 50, "Read in an XML file");
	        inputWindow.getChildren().add(xmlPrompt);
	        xmlButton();
	        

//	        OptionContainer optionList = new OptionContainer(
//	                                                         new MenuItem("FOREST FIRE"),
//	                                                         new MenuItem("PREDATOR PREY"),
//	                                                         new MenuItem("SEGREGATION"),
//	                                                         new MenuItem("GAME OF LIFE"));
//	        //new MenuItem("READ XML FILE"));
//	        optionList.setTranslateX(200);
//	        optionList.setTranslateY(350);
//	        gameWindow.getChildren().add(optionList);

	        return inputWindow;
	}
	
	public void xmlButton(){
		String buttonFill = "-fx-background-color: linear-gradient(#0079b3, #00110e);" + 
                "-fx-background-radius: 20;" + 
                "-fx-text-fill: white;";
    	
        Button readXML = new Button("Read values from XML file");
        readXML.setStyle(buttonFill);
        readXML.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.startSimulation();
            }
        });
        readXML.setTranslateX(20);
        readXML.setTranslateY(200);
        inputWindow.getChildren().add(readXML);
	}

	public void selectGridSize(){
		TextField numberField = new TextField();
		numberField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
	}
	
	public void errorPopup(String errorText){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Input error");

		    String s = errorText;
		    alert.setContentText(s);
		    alert.showAndWait();
//		    String msg = "Invalid text entered: ";
		
	}
	}
	




