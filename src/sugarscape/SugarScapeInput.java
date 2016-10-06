package sugarscape;

import xml.SugarScapeXMLFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import base.Simulation.CellType;
import base.UserInput;

/**
 * This class creates the userInput scenes and adds nodes if the user decides to 
 * manually select parameters
 * @author Delia
 *
 */
public class SugarScapeInput extends UserInput {

	private String sugarName = "Sugarscape";
	private static final int GRIDSIZE = 51;
	private Scene sugarScene;
	private SugarScapeSimulation sugar;
	private Spinner<Integer> maxSugarSpinner, totalAgentsSpinner, growBackSpinner, maxCarbsSpinner, 
	minCarbsSpinner, visionSpinner, metabSpinner, presetSpinner;

	/**
	 * @param s
	 * @param factory	
	 * @param mySim
	 */
	public SugarScapeInput(Stage s, SugarScapeXMLFactory factory, SugarScapeSimulation mySim) {
		super(s);
		this.sugar = mySim;
	}

	/**
	 * Creates spinner for user to select maximum sugar a patch can hold
	 */
	public void selectMaxPatchSugar() {
		maxSugarSpinner = new Spinner<>(1, 12, 4, 1);
		maxSugarSpinner.setEditable(true);
		getGrid().add(new Label("Maximum Sugar per Patch"), 0, 0);
		getGrid().add(maxSugarSpinner, 1, 0);
	}

	/**
	 * Creates spinner for user to select total agent population on the grid
	 */
	public void selectTotalAgents() {
		totalAgentsSpinner = new Spinner<>(1, 400, 200, 10);
		totalAgentsSpinner.setEditable(true);
		getGrid().add(new Label("Total Number of Agents"), 0, 1);
		getGrid().add(totalAgentsSpinner, 1, 1);

	}

	/**
	 * Creates spinner for user to select rate that a patch grows sugar back
	 */
	public void selectGrowBack() {
		growBackSpinner = new Spinner<>(1, 12, 1, 1);
		growBackSpinner.setEditable(true);
		getGrid().add(new Label("Patch Sugar Growback Rate"), 0, 2);
		getGrid().add(growBackSpinner, 1, 2);

	}	

	/**
	 * Creates spinner for user to select the maximum carbs an agent can spawn with
	 */
	public void selectAgentMaxCarbs() {
		maxCarbsSpinner = new Spinner<>(1, 35, 25, 1);
		maxCarbsSpinner.setEditable(true);
		getGrid().add(new Label("Agent Maximum Carbs"), 0, 3);
		getGrid().add(maxCarbsSpinner, 1, 3);
	}

	/**
	 * Creates spinner for user to select the minimum carbs an agent can spawn with
	 */
	public void selectAgentMinCarbs() {
		minCarbsSpinner = new Spinner<>(1, 20, 5, 1);
		minCarbsSpinner.setEditable(true);
		getGrid().add(new Label("Agent Minimum Carbs"), 0, 4);
		getGrid().add(minCarbsSpinner, 1, 4);
	}

	/**
	 * Creates spinner for user to select the number of carbs agents lose for each unit
	 * of distance moved
	 */
	public void selectAgentMetabRate() {
		metabSpinner = new Spinner<>(1, 10, 1, 1);
		metabSpinner.setEditable(true);
		getGrid().add(new Label("Agent Metabolism Rate"), 0, 5);
		getGrid().add(metabSpinner, 1, 5);
	}

	/**
	 * Creates spinner for user to select the distance an agent can move in a given direction
	 */
	public void selectAgentVision() {
		visionSpinner = new Spinner<>(1, 20, 4, 1);
		visionSpinner.setEditable(true);
		getGrid().add(new Label("Agent Vision"), 0, 6);
		getGrid().add(visionSpinner, 1, 6);
	}

	/**
	 * Creates spinner for user to select which preset (1 or 2) of the simulation to run
	 */
	public void choosePreset() {
		presetSpinner = new Spinner<>(1, 2, 1, 1);
		presetSpinner.setEditable(true);
		getGrid().add(new Label("Sugarscape preset"), 0, 7);
		getGrid().add(presetSpinner, 1, 7);
	}

	@Override
	public void startXMLSimulation() {
		sugarScene = sugar.init(stage, CellType.SQUARE);
		stage.setScene(sugarScene);
		stage.show();
	}

	@Override
	public void startManualSimulation(CellType cellType) {
		sugar = new SugarScapeSimulation(GRIDSIZE, maxSugarSpinner.getValue(),
				totalAgentsSpinner.getValue(), growBackSpinner.getValue(), maxCarbsSpinner.getValue(), 
				minCarbsSpinner.getValue(), metabSpinner.getValue(), visionSpinner.getValue(), 
				presetSpinner.getValue(), cellType);
		sugarScene = sugar.init(stage, cellType);
		stage.setScene(sugarScene);
		stage.show();
	}

	@Override
	public void generateNodes() {
		choosePreset();
		selectAgentMaxCarbs();
		selectAgentMetabRate();
		selectAgentMinCarbs();
		selectAgentVision();
		selectGrowBack();
		selectMaxPatchSugar();
		selectTotalAgents();

		getGrid().add(beginHexButton(sugarName), 0, 8);
		getGrid().add(beginTriangleButton(sugarName), 0, 9);
		getGrid().add(beginSquareButton(sugarName), 0, 10);
	}
}
