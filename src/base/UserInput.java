package base;

import base.Simulation.CellType;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Delia
 *
 */
public abstract class UserInput {
	public static final int INPUT_MENU_WIDTH = 700;
	public static final int INPUT_MENU_HEIGHT = 600;	
	
	public static Stage stage;
	public Pane segWindow;
	private Scene foragingAntsScene;
	private Spinner<Integer> gridSizeSpinner;
	
	private GridPane grid = new GridPane();
	private String buttonFill = "-fx-background-color: linear-gradient(#0079b3, #00110e);" + 
			"-fx-background-radius: 20;" + 
			"-fx-text-fill: white;";
    private String overButton = "-fx-background-color: linear-gradient(#00110e, #0079b3);" + 
            "-fx-background-radius: 20;" + 
            "-fx-text-fill: white;";

	/**
	 * @param s
	 */
	public UserInput(Stage s) {
		stage = s;
		stage.setScene(new Scene(setUpWindow()));
		stage.show();
	}

	/**
	 * @return
	 */
	private Parent setUpWindow() {
		segWindow = new Pane();
		segWindow.setPrefSize(INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);
		segWindow.getChildren().add(setBackground()); 
		
		promptText(60, "Read from an XML File");		
		promptText(150, "Or Choose your own Parameters");
		
		xmlButton();
		manualButton();
		
		return segWindow;
	}
	
	/**
	 * @param yPos
	 * @param message
	 */
	private void promptText(int yPos, String message) {
		Text prompt = new Text(50, yPos, message);
        prompt.setFont(Font.font ("Verdana", FontWeight.BOLD, 25));
        prompt.setFill(Color.WHITE);
		segWindow.getChildren().add(prompt);
	}
	
	/**
	 * @return
	 */
	public GridPane getGrid() {
		return grid;
	}
	
	/**
	 * @return
	 */
	public int getGridSize() {
		return gridSizeSpinner.getValue();
	}
	
	/**
	 * @return
	 */
	public ImageView setBackground() {
		Image background = new Image(getClass().getClassLoader()
				.getResourceAsStream("BackgroundCellSoc.jpg")); 
		ImageView backgroundImage = new ImageView(background);
		backgroundImage.setFitWidth(INPUT_MENU_WIDTH + 50);
		backgroundImage.setFitHeight(INPUT_MENU_HEIGHT);
		return backgroundImage;
	}

	/**
	 * 
	 */
	public void xmlButton() {
		Button readXML = new Button("Run with XML");
		readXML.setStyle(buttonFill);
        readXML.setOnMouseEntered(e -> mouseIn(readXML));
        readXML.setOnMouseExited(e -> mouseOut(readXML));
		readXML.setOnMouseClicked(e -> startXMLSimulation());
		readXML.setTranslateX(40);
		readXML.setTranslateY(80);
		segWindow.getChildren().add(readXML);
	}
	
	public void manualButton(){
		Button inputManual = new Button("Input values here");
		inputManual.setStyle(buttonFill);
        inputManual.setOnMouseEntered(e -> mouseIn(inputManual));
        inputManual.setOnMouseExited(e -> mouseOut(inputManual));
		inputManual.setOnMouseClicked(e -> manualInput());
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
    
//	@Override
	public void manualInput() {
		foragingAntsScene = new Scene(getGrid(), INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);

		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		generateNodes();
		stage.setScene(foragingAntsScene);
		stage.show();
	}

	public void selectGridSize() {
		gridSizeSpinner = new Spinner<>(10, 100, 50, 5);
		gridSizeSpinner.setEditable(true);
		grid.add(new Label("Size of Square Grid"), 0, 0);
		grid.add(gridSizeSpinner, 1, 0);
	}
	
//	@Override
	public Button beginHexButton(String sim) {
		Button beginSim = new Button("Start " + sim + " Hex Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.HEX));
		return beginSim;
	}

//	@Override
	public Button beginTriangleButton(String sim) {
		Button beginSim = new Button("Start " + sim + " Triangle Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.TRIANGLE));
		return beginSim;
	}

//	@Override
	public Button beginSquareButton(String sim) {
		Button beginSim = new Button("Start " + sim + " Square Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.SQUARE));
		return beginSim;
	}
	
	/**
	 * @return
	 */
//	public abstract Scene initSimulation(Stage s, CellType t);
	
//	public void startSimulation(){
//		mySimScene = segregation.init(stage, CellType.SQUARE);
//		stage.setScene(mySimScene);
//		stage.show();
//	}
	
	public abstract void startXMLSimulation();
	
	public abstract void startManualSimulation(CellType cellType);
	
	public abstract void generateNodes();

//	public abstract void selectGridSize();
	
//	public abstract void manualInput();
//	
//	public abstract Button beginHexButton();
//	
//	public abstract Button beginSquareButton();
//	
//	public abstract Button beginTriangleButton();



}




