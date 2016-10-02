package gameoflife;

import xml.GameOfLifeXMLFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import base.UserInput;

/**
 * @author Delia
 *
 */
public class GameOfLifeInput extends UserInput {
    private GameOfLifeSimulation gameOfLife;

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
     * @see base.UserInput#manualInput()
     */
    @Override
    public void manualInput() {
		selectGridSize();
		//		selectColors();
		getGrid().add(startManualButton("Start Game of Life Simulation"), 0, 1);
    }

	/* (non-Javadoc)
	 * @see base.UserInput#startManualSimulation()
	 */
	@Override
	public void startManualSimulation() {
		gameOfLife = new GameOfLifeSimulation(getGridSize());
		startSimulation();
	}


	/* (non-Javadoc)
	 * @see base.UserInput#initSimulation()
	 */
	@Override
	public Scene initSimulation() {
		return gameOfLife.init(stage);
	}

}
