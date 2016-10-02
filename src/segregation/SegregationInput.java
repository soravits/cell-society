package segregation;

import xml.SegregationXMLFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import base.UserInput;

public class SegregationInput extends UserInput {
	
	private Segregation segregation;
	private Spinner<Double> percEmptySpinner, percASpinner, percBSpinner, threshSpinner;

	public SegregationInput(Stage s, SegregationXMLFactory factory, Segregation mySim) {
		super(s);
		this.segregation = mySim;
	}

	public void selectPercEmpty() {
		percEmptySpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		percEmptySpinner.setEditable(true);
		getGrid().add(new Label("% Empty"), 0, 0);
		getGrid().add(percEmptySpinner, 1, 0);

	}

	public void selectPercA() {
		//lowest, highest, default value, increment size
		percASpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		percASpinner.setEditable(true);
		getGrid().add(new Label("% Color A"), 0, 1);
		getGrid().add(percASpinner, 1, 1);

	}

	public void selectPercB() {
		percBSpinner = new Spinner<>(0.05, 1 - percASpinner.getValue(), 0.5, 0.05);
		percBSpinner.setEditable(true);
		getGrid().add(new Label("% Color B"), 0, 2);
		getGrid().add(percBSpinner, 1, 2);

	}

	public void selectSatisfyThresh() {
		threshSpinner = new Spinner<>(0.1, 0.95, 0.5, 0.05);
		threshSpinner.setEditable(true);
		getGrid().add(new Label("% for Satisfaction"), 0, 3);
		getGrid().add(threshSpinner, 1, 3);

	}

	public void selectColors(){

	}

	@Override
	public void manualInput() {
		selectPercEmpty();
		selectPercA();
		selectPercB();
		selectSatisfyThresh();
		selectGridSize();
		//		selectColors();
		getGrid().add(startManualButton("Start Segregation Simulation"), 0, 6);
	}
	
	
	public void startManualSimulation() {
		segregation = new Segregation(getGridSize(), threshSpinner.getValue(),
				percASpinner.getValue(), percBSpinner.getValue(), percEmptySpinner.getValue());
		startSimulation();
	}

	@Override
	public Scene initSimulation() {
		return segregation.init(stage);
	}
}
