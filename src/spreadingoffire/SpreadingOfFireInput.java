package spreadingoffire;

import gameoflife.GameOfLifeSimulation;
import xml.FireXMLFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import base.UserInput;

public class SpreadingOfFireInput extends UserInput{
	private Scene fireScene;
	private SpreadingOfFireSimulation fireSim;
	private Spinner<Double> probCatchSpinner;
	private Spinner<Integer> gridSizeSpinner;
	private GridPane grid = new GridPane();
	
	public SpreadingOfFireInput(Stage s, FireXMLFactory factory, 
			SpreadingOfFireSimulation mySim) {
		super(s);
		this.fireSim = mySim;
	}

	public void selectProbCatch(){
		probCatchSpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		probCatchSpinner.setEditable(true);
		grid.add(new Label("Probability tree ignites"), 0, 0);
		grid.add(probCatchSpinner, 1, 0);
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
	public void manualInput() {
		// TODO Auto-generated method stub
		fireScene = new Scene(grid, INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);

		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		selectGridSize();
		selectProbCatch();
		//		selectColors();
		grid.add(beginButton(), 0, 6);
		stage.setScene(fireScene);
		stage.show();
		
	}

	@Override
	public void startXMLSimulation() {
		// TODO Auto-generated method stub
		fireScene = fireSim.init(stage);
		stage.setScene(fireScene);
		stage.show();
		
	}

	@Override
	public void startManualSimulation() {
		// TODO Auto-generated method stub
		fireSim = new SpreadingOfFireSimulation(gridSizeSpinner.getValue(), 
				probCatchSpinner.getValue());
		fireScene = fireSim.init(stage);
		stage.setScene(fireScene);
		stage.show();
		
	}

	@Override
	public Button beginButton() {
		Button beginSim = new Button("Start Spreading of Fire Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation());
		
		return beginSim;
	}
}
