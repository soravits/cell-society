package slimemolds;

import base.Simulation.CellType;
import base.UserInput;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import xml.SlimeXMLFactory;

/**
 * Defines input parameters for Slime Molds
 * @author Brian
 *
 */
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
	 *  Spinner that lets you choose the amount of diffusion on each step cycle from main spore point
	 */
	public void selectDiffusionSpore() {
		diffSpinner = new Spinner<>(0.3, 1.5, 0.7, 0.1); 
		diffSpinner.setEditable(true);
		getGrid().add(new Label("Diffusion Pollution Amount"), 0, 1);
		getGrid().add(diffSpinner, 1, 1);

	}

	/**
	 * Set amount of pollution that gets dropped when a mold cell walks directly on cell
	 */
	public void selectTouchSpore() {
		//lowest, highest, default value, increment size
		touchSpinner = new Spinner<>(0.5, 2.5, 1.5, 0.1);
		touchSpinner.setEditable(true);
		getGrid().add(new Label("Touch Pollution Amount"), 0, 2);
		getGrid().add(touchSpinner, 1, 2);
	}

	/**
	 * Set threshold mold value at which the cell will attract others
	 */
	public void setThreshold() {
		threshSpinner = new Spinner<>(1.0,3.0,1.5,0.2);
		threshSpinner.setEditable(true);
		getGrid().add(new Label("Threshold for Mold To Take notice"), 0, 3);
		getGrid().add(threshSpinner, 1, 3);

	}

	/**
	 * Set amount of spores that disappear per step cycle
	 */
	public void setDissipateAmount() {
		dissipateSpinner = new Spinner<>(0.1, 0.95, 0.5, 0.05);
		dissipateSpinner.setEditable(true);
		getGrid().add(new Label("Amount Polution Cell Loses"), 0, 4);
		getGrid().add(dissipateSpinner, 1, 4);
	}
	
	/**
	 * Sets the percentage of the grid that will be mold initially
	 */
	public void probMold() {
		probMoldSpinner = new Spinner<>(0.01, 0.99, 0.03, 0.01);
		probMoldSpinner.setEditable(true);
		getGrid().add(new Label("Probability Cell is Mold"), 0, 5);
		getGrid().add(probMoldSpinner, 1, 5);
	}


	/* (non-Javadoc)
	 * @see base.UserInput#generateNodes()
	 * Set up all of the spinner nodes
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
	 * Start simulation with manual configurations
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
	 * Start simulation with configurations set in XML
	 */
	@Override
	public void startXMLSimulation() {
		segScene = SlimeMoldsSimulation.init(stage, CellType.SQUARE);
		stage.setScene(segScene);
		stage.show();
	}
}