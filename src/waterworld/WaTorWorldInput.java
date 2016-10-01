package waterworld;

import xml.WaTorWorldXMLFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import base.UserInput;

public class WaTorWorldInput extends UserInput{
	private Scene waterScene;
	private WaTorWorldSimulation waterSim;
	
	public WaTorWorldInput(Stage s, WaTorWorldXMLFactory factory, WaTorWorldSimulation mySim) {
		super(s);
		this.waterSim = mySim;
	}

	public void selectFracFish(){
		
	}
	
	public void selectFracShark(){
		
	}
	
	public void selectFishBreedTime(){
		
	}
	
	public void selectSharkBreedTime(){
		
	}
	
	public void selectStarveTime(){
		
	}

	@Override
	public void manualInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startXMLSimulation() {
		// TODO Auto-generated method stub
		waterScene = waterSim.init(stage);
		stage.setScene(waterScene);
		stage.show();
		
	}

	@Override
	public void selectGridSize() {
		// TODO Auto-generated method stub
		
	}
}
