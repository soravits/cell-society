package segregation;

import xml.SegregationXMLFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import base.UserInput;
import base.Simulation.CellType;

public class SegregationInput extends UserInput{
	private Scene segScene;
	private Segregation segregation;
	private GridPane grid = new GridPane();
	private Spinner<Double> percEmptySpinner, percASpinner, percBSpinner, threshSpinner;
	private Spinner<Integer> gridSizeSpinner;

	public SegregationInput(Stage s, SegregationXMLFactory factory, Segregation mySim) {
		super(s);
		this.segregation = mySim;
	}

	public void selectPercEmpty() {
		percEmptySpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		percEmptySpinner.setEditable(true);
		grid.add(new Label("% Empty"), 0, 0);
		grid.add(percEmptySpinner, 1, 0);

	}

	public void selectPercA() {
		//lowest, highest, default value, increment size
		percASpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		percASpinner.setEditable(true);
		grid.add(new Label("% Color A"), 0, 1);
		grid.add(percASpinner, 1, 1);

	}

	public void selectPercB() {
		percBSpinner = new Spinner<>(0.05, 1 - percASpinner.getValue(), 0.5, 0.05);
		percBSpinner.setEditable(true);
		grid.add(new Label("% Color B"), 0, 2);
		grid.add(percBSpinner, 1, 2);

	}

	public void selectSatisfyThresh() {
		threshSpinner = new Spinner<>(0.1, 0.95, 0.5, 0.05);
		threshSpinner.setEditable(true);
		grid.add(new Label("% for Satisfaction"), 0, 3);
		grid.add(threshSpinner, 1, 3);

	}
	
	@Override
	public void selectGridSize() {
		gridSizeSpinner = new Spinner<>(10, 100, 50, 5);
		gridSizeSpinner.setEditable(true);
		grid.add(new Label("Size of Square Grid"), 0, 4);
		grid.add(gridSizeSpinner, 1, 4);
		
	}

	public void selectColors(){

	}

	@Override
	public void manualInput() {
		segScene = new Scene(grid, INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);

		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		selectPercEmpty();
		selectPercA();
		selectPercB();
		selectSatisfyThresh();
		selectGridSize();
		//		selectColors();
		grid.add(beginHexButton(), 0, 6);
		grid.add(beginTriangleButton(), 0, 7);
		grid.add(beginSquareButton(), 0, 8);
		stage.setScene(segScene);
		stage.show();
	}
	
	
	public void startManualSimulation(CellType type) {
		int inputValue = gridSizeSpinner.getValue();
		if((gridSizeSpinner.getValue() > 45)){
			inputValue = 45;
		}
		segregation = new Segregation(inputValue, threshSpinner.getValue(),
				percASpinner.getValue(), percBSpinner.getValue(), percEmptySpinner.getValue(),type);
		segScene = segregation.init(stage,type);
		stage.setScene(segScene);
		stage.show();
	}
	
	@Override
	public Button beginHexButton() {
		Button beginSim = new Button("Start Segregation Hex Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.HEX));
		return beginSim;
	}

	@Override
	public Button beginTriangleButton() {
		Button beginSim = new Button("Start Segregation Triangle Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.TRIANGLE));
		return beginSim;
	}

	@Override
	public Button beginSquareButton() {
		Button beginSim = new Button("Start Segregation Square Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.SQUARE));
		return beginSim;
	}

	/**
	 * 
	 */
	public void selectPercB() {
		percBSpinner = new Spinner<>(0.05, 1 - percASpinner.getValue(), 0.5, 0.05);
		percBSpinner.setEditable(true);
		getGrid().add(new Label("% Color B"), 0, 3);
		getGrid().add(percBSpinner, 1, 3);

	}

	/**
	 * 
	 */
	public void selectSatisfyThresh() {
		threshSpinner = new Spinner<>(0.1, 0.95, 0.5, 0.05);
		threshSpinner.setEditable(true);
		getGrid().add(new Label("% for Satisfaction"), 0, 4);
		getGrid().add(threshSpinner, 1, 4);

	}

	/**
	 * 
	 */
	public void selectColors(){

	}

	/* (non-Javadoc)
	 * @see base.UserInput#manualInput()
	 */
	@Override
	public void manualInput() {
		selectPercEmpty();
		selectPercA();
		selectPercB();
		selectSatisfyThresh();
		selectGridSize();
		//		selectColors();
		getGrid().add(startManualButton("Start Segregation Simulation"), 0, 5);
	}
	
	

	@Override
	public void startXMLSimulation() {
		segScene = segregation.init(stage,CellType.SQUARE);
		stage.setScene(segScene);
		stage.show();
	}


}