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
	private static final int HORIZONTAL_GAP = 50;

	public Stage stage;
	public Pane segWindow;
	private Scene myScene;
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
	 * Starts the simulation with specific configurations in XML
	 */
	public abstract void startXMLSimulation();

	/**
	 * @param cellType
	 * Starts the simulation with specific configurations
	 */
	public abstract void startManualSimulation(CellType cellType);

	/**
	 *  Creates the nodes/options that allow you to manually specify configurations
	 */
	public abstract void generateNodes();
	
	/**
	 *  Takes user input for an option
	 */
	public void manualInput() {
		myScene = new Scene(getGrid(), INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);
		grid.setStyle("-fx-background-color: #02f7c6;");
		grid.setHgap(HORIZONTAL_GAP);
		grid.setVgap(HORIZONTAL_GAP/5);
		grid.setPadding(new Insets(HORIZONTAL_GAP/5));
		generateNodes();
		stage.setScene(myScene);
		stage.show();
	}

	/**
	 * @return Grid for simulation
	 */
	public GridPane getGrid() {
		return grid;
	}

	/**
	 * @return gridSize as determined in spinner
	 */
	public int getGridSize() {
		return gridSizeSpinner.getValue();
	}

	/**
	 * @return Background image that will be set for simulation
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
	 * @param buttonText
	 * @param yTranslate
	 * @return New generic button with hover and location
	 */
	public Button createButton(String buttonText, int yTranslate){
		Button newButton = new Button(buttonText);
		newButton.setStyle(buttonFill);
		newButton.setOnMouseEntered(e -> mouseIn(newButton));
		newButton.setOnMouseExited(e -> mouseOut(newButton));
		newButton.setTranslateX(40);
		newButton.setTranslateY(yTranslate);
		
		return newButton;

	}

	/**
	 *  Creates the XML option button
	 */
	public void xmlButton() {
		Button readXML = createButton("Run with XML",80);
		readXML.setOnMouseClicked(e -> startXMLSimulation());
		segWindow.getChildren().add(readXML);
	}

	/**
	 *  Creates a manual input button
	 */
	public void manualButton() {
		Button inputManual = createButton("Input values here",160);
		inputManual.setOnMouseClicked(e -> manualInput());
		segWindow.getChildren().add(inputManual);
	}

	/**
	 *  Takes user input for specific grid size
	 */
	public void selectGridSize() {
		gridSizeSpinner = new Spinner<>(10, 100, 50, 5);
		gridSizeSpinner.setEditable(true);
		grid.add(new Label("Size of Square Grid"), 0, 0);
		grid.add(gridSizeSpinner, 1, 0);
	}

	/**
	 * @param sim
	 * @return Simulation to begin with Hex configuration
	 */
	public Button beginHexButton(String sim) {
		Button beginSim = new Button("Start " + sim + " Hex Simulation");
		beginSim.setStyle(buttonFill);
		beginSim.setOnMouseEntered(e -> mouseIn(beginSim));
		beginSim.setOnMouseExited(e -> mouseOut(beginSim));
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.HEX));
		return beginSim;
	}

	/**
	 * @param sim
	 * @return Simulation to begin with triangle configuration
	 */
	public Button beginTriangleButton(String sim) {
		Button beginSim = new Button("Start " + sim + " Triangle Simulation");
		beginSim.setStyle(buttonFill);
		beginSim.setOnMouseEntered(e -> mouseIn(beginSim));
		beginSim.setOnMouseExited(e -> mouseOut(beginSim));
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.TRIANGLE));
		return beginSim;
	}

	/**
	 * @param sim
	 * @return Simulation to begin with square configuration
	 */
	public Button beginSquareButton(String sim) {
		Button beginSim = new Button("Start " + sim + " Square Simulation");
		beginSim.setStyle(buttonFill);
		beginSim.setOnMouseEntered(e -> mouseIn(beginSim));
		beginSim.setOnMouseExited(e -> mouseOut(beginSim));
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.SQUARE));
		return beginSim;
	}

	/**
	 * @return Window in which you select initial configurations
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
		Text prompt = new Text(HORIZONTAL_GAP, yPos, message);
		prompt.setFont(Font.font ("Verdana", FontWeight.BOLD, HORIZONTAL_GAP/2));
		prompt.setFill(Color.WHITE);
		segWindow.getChildren().add(prompt);
	}

	/**
	 * @param b
	 */
	private void mouseIn(Button b) {
		b.setStyle(overButton);
	}

	/**
	 * @param b
	 */
	private void mouseOut(Button b) {
		b.setStyle(buttonFill);
	}	
}