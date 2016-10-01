package gameoflife;

import xml.GameOfLifeXMLFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
        // TODO Auto-generated method stub
		gameofLifeScene = new Scene(grid, INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);

		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		selectGridSize();
		//		selectColors();
		grid.add(beginButton(), 0, 6);
		stage.setScene(gameofLifeScene);
		stage.show();

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
		gridSizeSpinner = new Spinner<>(10, 100, 50, 5);
		gridSizeSpinner.setEditable(true);
		grid.add(new Label("Size of Square Grid"), 0, 4);
		grid.add(gridSizeSpinner, 1, 4);
		
	}

	@Override
	public void startManualSimulation() {
		// TODO Auto-generated method stub
		gameOfLife = new GameOfLifeSimulation(gridSizeSpinner.getValue());
		gameofLifeScene = gameOfLife.init(stage);
		stage.setScene(gameofLifeScene);
		stage.show();
	}

	@Override
	public Button beginButton() {
		// TODO Auto-generated method stub
		Button beginSim = new Button("Start Game of Life Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation());
		
		return beginSim;
	}

}
