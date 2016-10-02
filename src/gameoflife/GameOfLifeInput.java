package gameoflife;

//why does it only show a third of the grid when I run it with XML??

//running it with hex is perfect. But with triangle and square, we get strange coloring on the left side

import xml.GameOfLifeXMLFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import base.Simulation.CellType;
import base.UserInput;

public class GameOfLifeInput extends UserInput {
    private Scene gameofLifeScene;
    private String lifeString = "Game of Life";
    private GameOfLifeSimulation gameOfLife;

    public GameOfLifeInput(Stage s, GameOfLifeXMLFactory factory, GameOfLifeSimulation mySim) {
        super(s);
        this.gameOfLife = mySim;
    }


    @Override
    public void startXMLSimulation() {
        gameofLifeScene = gameOfLife.init(stage, CellType.SQUARE);
        stage.setScene(gameofLifeScene);
        stage.show();

    }


	@Override
	public void startManualSimulation(CellType type) {
//		int inputValue = gridSizeSpinner.getValue();
//		if((gridSizeSpinner.getValue() > 45)){
//			inputValue = 45;
//		}
		gameOfLife = new GameOfLifeSimulation(getGridSize(), type);
		gameofLifeScene = gameOfLife.init(stage, type);
		stage.setScene(gameofLifeScene);
		stage.show();
	}


	@Override
	public void generateNodes() {
		selectGridSize();
		getGrid().add(beginHexButton(lifeString), 0, 1);
		getGrid().add(beginTriangleButton(lifeString), 0, 2);
		getGrid().add(beginSquareButton(lifeString), 0, 3);
	}
}