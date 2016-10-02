package spreadingoffire;

import xml.FireXMLFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import base.UserInput;

/**
 * @author Delia
 *
 */
public class SpreadingOfFireInput extends UserInput {
	
	private SpreadingOfFireSimulation fireSim;
	private Spinner<Double> probCatchSpinner;

	/**
	 * @param s
	 * @param factory
	 * @param mySim
	 */
	public SpreadingOfFireInput(Stage s, FireXMLFactory factory, 
			SpreadingOfFireSimulation mySim) {
		super(s);
		this.fireSim = mySim;
	}

	/**
	 * 
	 */
	public void selectProbCatch() {
		probCatchSpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		probCatchSpinner.setEditable(true);
		getGrid().add(new Label("Probability tree ignites"), 0, 1);
		getGrid().add(probCatchSpinner, 1, 1);
	}


	/* (non-Javadoc)
	 * @see base.UserInput#manualInput()
	 */
	@Override
	public void manualInput() {
		selectGridSize();
		selectProbCatch();
		//		selectColors();
		getGrid().add(startManualButton("Start Spreading of Fire Simulation"), 0, 2);

	}

	/* (non-Javadoc)
	 * @see base.UserInput#startManualSimulation()
	 */
	@Override
	public void startManualSimulation() {
		fireSim = new SpreadingOfFireSimulation(getGridSize(), probCatchSpinner.getValue());
		startSimulation();

	}

	/* (non-Javadoc)
	 * @see base.UserInput#initSimulation()
	 */
	@Override
	public Scene initSimulation() {
		return fireSim.init(stage);
	}
}
