package gameoflife;

import xml.GameOfLifeXMLFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import base.Simulation.CellType;
import base.UserInput;

/**
 * @author Delia
 *
 */
public class GameOfLifeInput extends UserInput {
	private Scene gameofLifeScene;
	private String lifeString = "Game of Life";
	private GameOfLifeSimulation gameOfLife;
	private Spinner<Double> percentAliveSpinner;

	/**
	 * @param s
	 * @param factory
	 * @param mySim
	 */
	public GameOfLifeInput(Stage s, GameOfLifeXMLFactory factory, GameOfLifeSimulation mySim) {
		super(s);
		this.gameOfLife = mySim;
	}


	/* (non-Javadoc)
	 * @see base.UserInput#startXMLSimulation()
	 */
	@Override
	public void startXMLSimulation() {
		gameofLifeScene = gameOfLife.init(stage, CellType.SQUARE);
		stage.setScene(gameofLifeScene);
		stage.show();

	}

	/**
	 * 
	 */
	public void selectPercAlive() {
		percentAliveSpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		percentAliveSpinner.setEditable(true);
		getGrid().add(new Label("(%) Percentage Alive"), 0, 2);
		getGrid().add(percentAliveSpinner, 1, 2);
	}

	/* (non-Javadoc)
	 * @see base.UserInput#startManualSimulation(base.Simulation.CellType)
	 */
	@Override
	public void startManualSimulation(CellType type) {
		gameOfLife = new GameOfLifeSimulation(getGridSize(), percentAliveSpinner.getValue(),type);
		gameofLifeScene = gameOfLife.init(stage, type);
		stage.setScene(gameofLifeScene);
		stage.show();
	}


	/* (non-Javadoc)
	 * @see base.UserInput#generateNodes()
	 */
	@Override
	public void generateNodes() {
		selectGridSize();
		selectPercAlive();
		getGrid().add(beginHexButton(lifeString), 0, 3);
		getGrid().add(beginTriangleButton(lifeString), 0, 4);
		getGrid().add(beginSquareButton(lifeString), 0, 5);
	}
}