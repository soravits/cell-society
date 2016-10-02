package waterworld;

import segregation.Segregation;
import xml.WaTorWorldXMLFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import base.UserInput;

public class WaTorWorldInput extends UserInput{
	private Scene waterScene;
	private WaTorWorldSimulation waterSim;
	private GridPane grid = new GridPane();
	private Spinner<Double> fracFishSpinner, fracSharkSpinner;
	private Spinner<Integer> fishBreedSpinner, sharkBreedSpinner, starveSpinner, gridSizeSpinner;
	
	public WaTorWorldInput(Stage s, WaTorWorldXMLFactory factory, WaTorWorldSimulation mySim) {
		super(s);
		this.waterSim = mySim;
	}

	public void selectFracFish(){
		fracFishSpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		fracFishSpinner.setEditable(true);
		grid.add(new Label("% Fish Population"), 0, 0);
		grid.add(fracFishSpinner, 1, 0);
		
	}
	
	public void selectFracShark(){
		fracSharkSpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		fracSharkSpinner.setEditable(true);
		grid.add(new Label("% Shark Population"), 0, 1);
		grid.add(fracSharkSpinner, 1, 1);
		
	}
	
	public void selectFishBreedTime(){
		fishBreedSpinner = new Spinner<>(1, 20, 3, 1);
		fishBreedSpinner.setEditable(true);
		grid.add(new Label("Fish Breed Time"), 0, 2);
		grid.add(fishBreedSpinner, 1, 2);
		
	}
	
	public void selectSharkBreedTime(){
		sharkBreedSpinner = new Spinner<>(1, 20, 15, 1);
		sharkBreedSpinner.setEditable(true);
		grid.add(new Label("Shark Breed Time"), 0, 3);
		grid.add(sharkBreedSpinner, 1, 3);
		
	}
	
	public void selectStarveTime(){
		starveSpinner = new Spinner<>(1, 20, 3, 1);
		starveSpinner.setEditable(true);
		grid.add(new Label("Starvation Time"), 0, 4);
		grid.add(starveSpinner, 1, 4);
		
	}
	
	@Override
	public void selectGridSize() {
		// TODO Auto-generated method stub
		gridSizeSpinner = new Spinner<>(10, 100, 50, 5);
		gridSizeSpinner.setEditable(true);
		grid.add(new Label("Size of Square Grid"), 0, 5);
		grid.add(gridSizeSpinner, 1, 5);
		
	}

	@Override
	public void manualInput() {
		waterScene = new Scene(grid, INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);
		grid.setStyle("-fx-background-color: #a0c6ed;");
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		selectFracFish();
		selectFracShark();
		selectFishBreedTime();
		selectSharkBreedTime();
		selectStarveTime();
		selectGridSize();
		//		selectColors();
		grid.add(beginButton(), 0, 6);
		stage.setScene(waterScene);
		stage.show();
		
	}
	
	@Override
	public void startXMLSimulation() {
		waterScene = waterSim.init(stage);
		stage.setScene(waterScene);
		stage.show();
		
	}

	@Override
	public void startManualSimulation() {
		waterSim = new WaTorWorldSimulation(gridSizeSpinner.getValue(), 
				fracFishSpinner.getValue(),	fracSharkSpinner.getValue(), 
				fishBreedSpinner.getValue(), sharkBreedSpinner.getValue(), 
				starveSpinner.getValue());
		waterScene = waterSim.init(stage);
		stage.setScene(waterScene);
		stage.show();
		
	}

	@Override
	public Button beginButton() {
		Button beginSim = new Button("Start Predator-Prey Simulation");
		beginSim.setOnMouseClicked(e -> startManualSimulation());
		return beginSim;
	}
}
