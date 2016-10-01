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

public class SegregationInput extends UserInput{
	private Scene segScene;
	private Segregation segregation;
	private GridPane grid = new GridPane();
	private double percA, percB, empty, threshold;
	private int gridsize;
	private Spinner<Double> percEmptySpinner, percASpinner, percBSpinner, threshSpinner;
	private Spinner<Integer> gridSizeSpinner;

	public SegregationInput(Stage s, SegregationXMLFactory factory, Segregation mySim) {
		super(s);
		this.segregation = mySim;

		//		System.out.println(stage);
		// TODO Auto-generated constructor stub
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
		percBSpinner = new Spinner<>(0.05, 1 - percA, 0.5, 0.05);
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
		grid.add(beginButton(), 0, 6);
		stage.setScene(segScene);
		stage.show();
	}
	
	private Button beginButton(){
		Button beginSim = new Button("Start Segregation Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation());
		beginSim.setTranslateX(40);
		beginSim.setTranslateY(80);
		
		return beginSim;
	}
	
	public void startManualSimulation() {
//		private Spinner<Double> percEmptySpinner, percASpinner, percBSpinner;
//		private Spinner<Integer> gridSizeSpinner;
		// TODO Auto-generated method stub
		segregation = new Segregation(gridSizeSpinner.getValue(), threshSpinner.getValue(),
				percASpinner.getValue(), percBSpinner.getValue(), percEmptySpinner.getValue());
		segScene = segregation.init(stage);
		stage.setScene(segScene);
		stage.show();
	}

	@Override
	public void startXMLSimulation() {
		// TODO Auto-generated method stub
		segScene = segregation.init(stage);
		stage.setScene(segScene);
		stage.show();
	}


}
