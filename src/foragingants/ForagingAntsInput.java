package foragingants;

/**
 * Defines input parameters for Foraging Ants.
 * @author Delia
 */

import base.Simulation;
import base.UserInput;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import base.Simulation.CellType;
import base.UserInput;
import xml.ForagingAntsXMLFactory;

/**
 * @author Delia
 *
 */
public class ForagingAntsInput extends UserInput {
	private Scene foragingAntsScene;
	private String antString = "Foraging Ants";
	private ForagingAntsSimulation foragingAnts;
	private Spinner<Integer> simDurationSpinner, nestLocationRowSpinner, nestLocationColSpinner, 
	foodSourceRowSpinner, foodSourceColSpinner, maxAntsSpinner, maxAntsPerLocationSpinner, 
	antLifetimeSpinner, initialPopulationSpinner, antsBornPerStepSpinner;
	private Spinner<Double> minPheromoneSpinner, maxPheromoneSpinner, evapRatioSpinner, 
	diffusionRatioSpinner;

	public ForagingAntsInput(Stage s, ForagingAntsXMLFactory factory, ForagingAntsSimulation mySim) {
		super(s);
		this.foragingAnts = mySim; 
	}

	/**
	 * Creates spinner to choose the duration of the sim
	 */
	 public void selectSimDuration() {
		 simDurationSpinner = new Spinner<>(100, 8000, 5000, 100);
		 simDurationSpinner.setEditable(true);
		 getGrid().add(new Label("Duration of Simulation"), 0, 1);
		 getGrid().add(simDurationSpinner, 1, 1);
	 }

	/**
	 * Creates spinner to choose the row of the nest location in the grid
     * @param gridSize
	 */
	 public void selectNestLocationRow(int gridSize) {
		 nestLocationRowSpinner = new Spinner<>(0, gridSize, 15, 1);
		 nestLocationRowSpinner.setEditable(true);
		 getGrid().add(new Label("Row of nest"), 0, 2);
		 getGrid().add(nestLocationRowSpinner, 1, 2);

	 }

	 /**
	  * Creates a spinner to choose the column of the nest location in the grid
      * @param gridSize
	  */
	 public void selectNestLocationCol(int gridSize) {
		 nestLocationColSpinner = new Spinner<>(0, gridSize, 0, 1);
		 nestLocationColSpinner.setEditable(true);
		 getGrid().add(new Label("Column of nest"), 0, 3);
		 getGrid().add(nestLocationColSpinner, 1, 3);

	 }

	/**
	 * Creates spinner to choose the row of the food source in the grid
     * @param gridSize
	 */
	 public void selectFoodSourceRow(int gridSize) {
		 foodSourceRowSpinner = new Spinner<>(0, gridSize, 0, 1);
		 foodSourceRowSpinner.setEditable(true);
		 getGrid().add(new Label("Row of Food Source"), 0, 4);
		 getGrid().add(foodSourceRowSpinner, 1, 4);
	 }


    /**
     * Creates spinner to choose percentage alive
     * @param gridSize
     */
	 public void selectFoodSourceCol(int gridSize) {
		 foodSourceColSpinner = new Spinner<>(0, gridSize, 0, 1);
		 foodSourceColSpinner.setEditable(true);
		 getGrid().add(new Label("Column of Food Source"), 0, 5);
		 getGrid().add(foodSourceColSpinner, 1, 5);
	 }

    /**
     * Creates spinner to choose the max number of ants per sim
     */
	 public void selectMaxAnts() {
		 maxAntsSpinner = new Spinner<>(100, 2000, 1000, 100);
		 maxAntsSpinner.setEditable(true);
		 getGrid().add(new Label("Max total ant population"), 0, 6);
		 getGrid().add(maxAntsSpinner, 1, 6);
	 }

    /**
     * Creates spinner to choose the max number of ants per cell
     */
	 public void selectMaxAntsPerLocation() {
		 maxAntsPerLocationSpinner = new Spinner<>(1, 20, 10, 1); //what's the upper limit for this
		 maxAntsPerLocationSpinner.setEditable(true);
		 getGrid().add(new Label("Max ant population per location"), 0, 7);
		 getGrid().add(maxAntsPerLocationSpinner, 1, 7);
	 }

	 /**
	  * Creates a spinner to choose the lifteime of an ant
	  */
	 public void selectAntLifetime() {
		 antLifetimeSpinner = new Spinner<>(10, 1000, 500, 10);
		 antLifetimeSpinner.setEditable(true);
		 getGrid().add(new Label("Ant Lifetime"), 0, 8);
		 getGrid().add(antLifetimeSpinner, 1, 8);

	 }

	 /**
	  * Creates a spinner to choose the initial ant population
	  */
	 public void selectInitialPopulation() {
		 initialPopulationSpinner = new Spinner<>(1, 20, 2, 1);
		 initialPopulationSpinner.setEditable(true);
		 getGrid().add(new Label("Initial ant population"), 0, 9);
		 getGrid().add(initialPopulationSpinner, 1, 9);

	 }

    /**
     * Creates spinner to choose the number of ants born at each step
     */
	 public void selectAntsBornPerStep() {
		 antsBornPerStepSpinner = new Spinner<>(1, 20, 2, 1);
		 antsBornPerStepSpinner.setEditable(true);
		 getGrid().add(new Label("Ants born per step"), 0, 10);
		 getGrid().add(antsBornPerStepSpinner, 1, 10);
	 }

    /**
     * Creates spinner to choose the minimum pheromone per cell
     */
	 public void selectMinPheromone() {
		 minPheromoneSpinner = new Spinner<>(0.00, 10.0, 0.0, 0.5); 
		 minPheromoneSpinner.setEditable(true);
		 getGrid().add(new Label("Min pheromone"), 0, 11);
		 getGrid().add(minPheromoneSpinner, 1, 11);
	 }

    /**
     * Creates spinner to choose the maximum pheromone per cell
     */
	 public void selectMaxPheromone() {
		 //lowest, highest, default value, increment size
		 maxPheromoneSpinner = new Spinner<>(10.0, 2000.0, 1000.0, 10.0);
		 maxPheromoneSpinner.setEditable(true);
		 getGrid().add(new Label("Max pheromone"), 0, 12);
		 getGrid().add(maxPheromoneSpinner, 1, 12);
	 }

    /**
     * Creates a spinner to select the evaporation ratio of pheromones
     */
	 public void selectEvapRatio() {
		 evapRatioSpinner = new Spinner<>(0.000, 0.030, 0.001, 0.001);
		 evapRatioSpinner.setEditable(true);
		 getGrid().add(new Label("Evaporation Ratio"), 0, 13);
		 getGrid().add(evapRatioSpinner, 1, 13);
	 }

    /**
     * Creates spinner to choose the difusion ratio of pheromones
     */
	 public void selectDiffusionRatio() {
		 diffusionRatioSpinner = new Spinner<>(0.5, 20.0, 10.0, 0.5);
		 diffusionRatioSpinner.setEditable(true);
		 getGrid().add(new Label("Diffusion Ratio"), 0, 14);
		 getGrid().add(diffusionRatioSpinner, 1, 14);
	 }

    /**
     * Start the simulation with XML input
     */
	 @Override
	 public void startXMLSimulation() {
		 foragingAntsScene = foragingAnts.init(stage, Simulation.CellType.SQUARE);
		 stage.setScene(foragingAntsScene);
		 stage.show();
	 }

    /**
     * Starts the simulation with manual input
     */
	 @Override
	 public void manualInput() { 
		 foragingAntsScene = new Scene(getGrid(), INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT + 200);
		 getGrid().setHgap(50);
		 getGrid().setVgap(10);
		 getGrid().setPadding(new Insets(10));
		 generateNodes();
		 stage.setScene(foragingAntsScene);
		 stage.show();
	 }


	 /* (non-Javadoc)
	  * @see base.UserInput#startManualSimulation(base.Simulation.CellType)
	  */
	 @Override
	 public void startManualSimulation(Simulation.CellType type) {
		 foragingAnts = new ForagingAntsSimulation(getGridSize(), type, simDurationSpinner.getValue(), 
				 nestLocationRowSpinner.getValue(), nestLocationColSpinner.getValue(), 
				 foodSourceRowSpinner.getValue(), foodSourceColSpinner.getValue(), 
				 maxAntsSpinner.getValue(), maxAntsPerLocationSpinner.getValue(), 
				 antLifetimeSpinner.getValue(), initialPopulationSpinner.getValue(), 
				 antsBornPerStepSpinner.getValue(), minPheromoneSpinner.getValue(), 
				 maxPheromoneSpinner.getValue(), evapRatioSpinner.getValue(), 
				 diffusionRatioSpinner.getValue());
		 foragingAntsScene = foragingAnts.init(stage, type);
		 stage.setScene(foragingAntsScene);
		 stage.show();
	 }


	 /* (non-Javadoc)
	  * @see base.UserInput#generateNodes()
	  */
	 @Override
	 public void generateNodes() {
		 selectGridSize();
		 selectMaxAntsPerLocation();
		 selectAntsBornPerStep();
		 selectMaxAnts();
		 selectFoodSourceRow(getGridSize());
		 selectFoodSourceCol(getGridSize());
		 selectNestLocationCol(getGridSize());
		 selectSimDuration();
		 selectNestLocationRow(getGridSize());
		 selectAntLifetime();
		 selectInitialPopulation();
		 selectMinPheromone();
		 selectMaxPheromone();
		 selectEvapRatio();
		 selectDiffusionRatio();

		 getGrid().add(beginHexButton(antString), 0, 15);
		 getGrid().add(beginTriangleButton(antString), 0, 16);
		 getGrid().add(beginSquareButton(antString), 0, 17);
	 }
}