package gameoflife;

import xml.GameOfLifeXMLFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import base.Simulation.CellType;
import base.UserInput;

public class GameOfLifeInput extends UserInput {
    private Scene gameofLifeScene;
    private GameOfLifeSimulation gameOfLife;
	private Spinner<Integer> gridSizeSpinner;
	private GridPane grid = new GridPane();

    public GameOfLifeInput(Stage s, GameOfLifeXMLFactory factory, GameOfLifeSimulation mySim) {
        super(s);
        this.gameOfLife = mySim;
    }

    @Override
    public void manualInput() {
		gameofLifeScene = new Scene(grid, INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);

		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		selectGridSize();
		//		selectColors();
		grid.add(beginHexButton(), 0, 6);
		grid.add(beginTriangleButton(), 0, 7);
		grid.add(beginSquareButton(), 0, 8);
		stage.setScene(gameofLifeScene);
		stage.show();

    }

    @Override
    public void startXMLSimulation() {
        gameofLifeScene = gameOfLife.init(stage, CellType.SQUARE);
        stage.setScene(gameofLifeScene);
        stage.show();

    }

	@Override
	public void selectGridSize() {
		gridSizeSpinner = new Spinner<>(10, 100, 50, 5);
		gridSizeSpinner.setEditable(true);
		grid.add(new Label("Size of Square Grid"), 0, 4);
		grid.add(gridSizeSpinner, 1, 4);
		
	}

	@Override
	public void startManualSimulation(CellType type) {
		int inputValue = gridSizeSpinner.getValue();
		if((gridSizeSpinner.getValue() > 45)){
			inputValue = 45;
		}
		gameOfLife = new GameOfLifeSimulation(inputValue,type);
		gameofLifeScene = gameOfLife.init(stage,type);
		stage.setScene(gameofLifeScene);
		stage.show();
	}

	@Override
	public Button beginHexButton() {
		Button beginSim = new Button("Start Game of Life Hex Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.HEX));
		return beginSim;
	}

	@Override
	public Button beginTriangleButton() {
		Button beginSim = new Button("Start Game of Life Triangle Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.TRIANGLE));
		return beginSim;
	}

	@Override
	public Button beginSquareButton() {
		Button beginSim = new Button("Start Game of Life Square Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation(CellType.SQUARE));
		return beginSim;
	}

}
