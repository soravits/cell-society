package segregation;

import xml.SegregationXMLFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import base.UserInput;
import base.Simulation.CellType;

public class SegregationInput extends UserInput{
	private String segName = "SegregationSimulation";
	private Scene segScene;
	private SegregationSimulation segregation;
	private Spinner<Double> percEmptySpinner, percASpinner, percBSpinner, threshSpinner;

	public SegregationInput(Stage s, SegregationXMLFactory factory, SegregationSimulation mySim) {
		super(s);
		this.segregation = mySim;
	}

	public void selectPercEmpty() {
		percEmptySpinner = new Spinner<>(0.05, 0.95, 0.3, 0.05); 
		percEmptySpinner.setEditable(true);
		getGrid().add(new Label("% Empty"), 0, 1);
		getGrid().add(percEmptySpinner, 1, 1);

	}

	public void selectPercA() {
		//lowest, highest, default value, increment size
		percASpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		percASpinner.setEditable(true);
		getGrid().add(new Label("% Color A"), 0, 2);
		getGrid().add(percASpinner, 1, 2);

	}

	public void selectPercB() {
		percBSpinner = new Spinner<>(0.05, 1 - percASpinner.getValue(), 0.5, 0.05);
		percBSpinner.setEditable(true);
		getGrid().add(new Label("% Color B"), 0, 3);
		getGrid().add(percBSpinner, 1, 3);

	}

	public void selectSatisfyThresh() {
		threshSpinner = new Spinner<>(0.1, 0.95, 0.4, 0.05);
		threshSpinner.setEditable(true);
		getGrid().add(new Label("% for Satisfaction"), 0, 4);
		getGrid().add(threshSpinner, 1, 4);

	}


//	@Override
//	public void manualInput() {
//		segScene = new Scene(getGrid(), INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);
//
//		getGrid().setHgap(50);
//		getGrid().setVgap(10);
//		getGrid().setPadding(new Insets(10));
//		generateNodes();
//		stage.setScene(segScene);
//		stage.show();
//	}

	public void generateNodes(){
		selectGridSize();
		selectPercEmpty();
		selectPercA();
		selectPercB();
		selectSatisfyThresh();
		getGrid().add(beginHexButton(segName), 0, 5);
		getGrid().add(beginTriangleButton(segName), 0, 6);
		getGrid().add(beginSquareButton(segName), 0, 7);
	}


	public void startManualSimulation(CellType type) {
		segregation = new SegregationSimulation(getGridSize(), threshSpinner.getValue(),
				percASpinner.getValue(), percBSpinner.getValue(), percEmptySpinner.getValue(),type);
		segScene = segregation.init(stage,type);
		stage.setScene(segScene);
		stage.show();
	}


	@Override
	public void startXMLSimulation() {
		segScene = segregation.init(stage, CellType.SQUARE);
		stage.setScene(segScene);
		stage.show();
	}

}