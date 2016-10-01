package gameoflife;

import xml.GameOfLifeXMLFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import base.UserInput;

public class GameOfLifeInput extends UserInput {
    private Scene gameofLifeScene;
    private GameOfLifeSimulation gameOfLife;

    public GameOfLifeInput(Stage s, GameOfLifeXMLFactory factory, GameOfLifeSimulation mySim) {
        super(s);
        this.gameOfLife = mySim;
    }

    @Override
    public void manualInput() {
        // TODO Auto-generated method stub

    }

    @Override
    public void startXMLSimulation() {
        // TODO Auto-generated method stub
        gameofLifeScene = gameOfLife.init(stage);
        stage.setScene(gameofLifeScene);
        stage.show();

    }

	@Override
	public void selectGridSize() {
		// TODO Auto-generated method stub
		
	}

}
