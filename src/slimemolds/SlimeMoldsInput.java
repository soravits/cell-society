package slimemolds;

import base.Simulation.CellType;
import base.UserInput;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import xml.SlimeXMLFactory;

public class SlimeMoldsInput extends UserInput{
	private String segName = "SlimeMolds";
	private Scene segScene;
	private SlimeMoldsSimulation SlimeMoldsSimulation;
	private Spinner<Double> diffSpinner, touchSpinner, threshSpinner, dissipateSpinner, probMoldSpinner;

	/**
	 * @param s
	 * @param factory
	 * @param mySim
	 */
	public SlimeMoldsInput(Stage s, SlimeXMLFactory factory, SlimeMoldsSimulation mySim) {
		super(s);
		this.SlimeMoldsSimulation = mySim;
	}

	/**
	 * 
	 */
	public void selectDiffusionSpore() {
		diffSpinner = new Spinner<>(0.3, 1.5, 0.7, 0.1); 
		diffSpinner.setEditable(true);
		getGrid().add(new Label("Diffusion Pollution Amount"), 0, 1);
		getGrid().add(diffSpinner, 1, 1);

	}

	/**
	 * 
	 */
	public void selectTouchSpore() {
		//lowest, highest, default value, increment size
		touchSpinner = new Spinner<>(0.5, 2.5, 1.5, 0.1);
		touchSpinner.setEditable(true);
		getGrid().add(new Label("Touch Pollution Amount"), 0, 2);
		getGrid().add(touchSpinner, 1, 2);
	}

	/**
	 * 
	 */
	public void setThreshold() {
		threshSpinner = new Spinner<>(1.0,3.0,1.5,0.2);
		threshSpinner.setEditable(true);
		getGrid().add(new Label("Threshold for Mold To Take notice"), 0, 3);
		getGrid().add(threshSpinner, 1, 3);

	}

	/**
	 * 
	 */
	public void setDissipateAmount() {
		dissipateSpinner = new Spinner<>(0.1, 0.95, 0.5, 0.05);
		dissipateSpinner.setEditable(true);
		getGrid().add(new Label("Amount Polution Cell Loses"), 0, 4);
		getGrid().add(dissipateSpinner, 1, 4);
	}
	
	/**
	 * 
	 */
	public void probMold() {
		probMoldSpinner = new Spinner<>(0.01, 0.99, 0.03, 0.01);
		probMoldSpinner.setEditable(true);
		getGrid().add(new Label("Probability Cell is Mold"), 0, 5);
		getGrid().add(probMoldSpinner, 1, 4);
	}


	/* (non-Javadoc)
	 * @see base.UserInput#generateNodes()
	 */
	public void generateNodes(){
		selectGridSize();
		selectDiffusionSpore();
		selectTouchSpore();
		setThreshold();
		setDissipateAmount();
		probMold();
		getGrid().add(beginHexButton(segName), 0, 6);
		getGrid().add(beginTriangleButton(segName), 0, 7);
		getGrid().add(beginSquareButton(segName), 0, 8);
	}


	/* (non-Javadoc)
	 * @see base.UserInput#startManualSimulation(base.Simulation.CellType)
	 */
	public void startManualSimulation(CellType type) {
		SlimeMoldsSimulation = new SlimeMoldsSimulation(getGridSize(), diffSpinner.getValue(),
				touchSpinner.getValue(), threshSpinner.getValue(), dissipateSpinner.getValue(),
				probMoldSpinner.getValue(), type);
		segScene = SlimeMoldsSimulation.init(stage,type);
		stage.setScene(segScene);
		stage.show();
	}


	/* (non-Javadoc)
	 * @see base.UserInput#startXMLSimulation()
	 */
	@Override
	public void startXMLSimulation() {
		segScene = SlimeMoldsSimulation.init(stage, CellType.SQUARE);
		stage.setScene(segScene);
		stage.show();
	}
}
