package waterworld;

//NOTE: on manual input the numbers in the right hand corner change even when no steps are happening

import xml.WaTorWorldXMLFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import base.UserInput;
import base.Simulation.CellType;

public class WaTorWorldInput extends UserInput{
	private Scene waterScene;
	private String waterName = "Predator-Prey";
	private WaTorWorldSimulation waterSim;
	private Spinner<Double> fracFishSpinner, fracSharkSpinner;
	private Spinner<Integer> fishBreedSpinner, sharkBreedSpinner, starveSpinner;
	
	public WaTorWorldInput(Stage s, WaTorWorldXMLFactory factory, WaTorWorldSimulation mySim) {
		super(s);
		this.waterSim = mySim;
	}

	/**
	 * 
	 */
	public void selectFracFish(){
		fracFishSpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		fracFishSpinner.setEditable(true);
		getGrid().add(new Label("% Fish Population"), 0, 1);
		getGrid().add(fracFishSpinner, 1, 1);
		
	}
	
	/**
	 * 
	 */
	public void selectFracShark(){
		fracSharkSpinner = new Spinner<>(0.05, 0.95, 0.25, 0.05);
		fracSharkSpinner.setEditable(true);
		getGrid().add(new Label("% Shark Population"), 0, 2);
		getGrid().add(fracSharkSpinner, 1, 2);
		
	}
	
	/**
	 * 
	 */
	public void selectFishBreedTime(){
		fishBreedSpinner = new Spinner<>(1, 20, 3, 1);
		fishBreedSpinner.setEditable(true);
		getGrid().add(new Label("Fish Breed Time"), 0, 3);
		getGrid().add(fishBreedSpinner, 1, 3);
		
	}
	
	/**
	 * 
	 */
	public void selectSharkBreedTime(){
		sharkBreedSpinner = new Spinner<>(1, 20, 15, 1);
		sharkBreedSpinner.setEditable(true);
		getGrid().add(new Label("Shark Breed Time"), 0, 4);
		getGrid().add(sharkBreedSpinner, 1, 4);
		
	}
	
	/**
	 * 
	 */
	public void selectStarveTime(){
		starveSpinner = new Spinner<>(1, 20, 3, 1);
		starveSpinner.setEditable(true);
		getGrid().add(new Label("Starvation Time"), 0, 5);
		getGrid().add(starveSpinner, 1, 5);
		
	}	
	
	@Override
	public void startXMLSimulation() {
		waterScene = waterSim.init(stage,CellType.SQUARE);
		stage.setScene(waterScene);
		stage.show();
		
	}

	@Override
	public void startManualSimulation(CellType type) {
//		int inputValue = gridSizeSpinner.getValue();
//		if((gridSizeSpinner.getValue() > 45)){
//			inputValue = 45;
//		}
		waterSim = new WaTorWorldSimulation(getGridSize(), 
				fracFishSpinner.getValue(),	fracSharkSpinner.getValue(), 
				fishBreedSpinner.getValue(), sharkBreedSpinner.getValue(), 
				starveSpinner.getValue(), type);
		waterScene = waterSim.init(stage, type);
		stage.setScene(waterScene);
		stage.show();
		
	}

	@Override
	public void generateNodes() {
		selectFracFish();
		selectFracShark();
		selectFishBreedTime();
		selectSharkBreedTime();
		selectStarveTime();
		selectGridSize();
		//		selectColors();
		getGrid().add(beginHexButton(waterName), 0, 6);
		getGrid().add(beginTriangleButton(waterName), 0, 7);
		getGrid().add(beginSquareButton(waterName), 0, 8);
		
	}
}