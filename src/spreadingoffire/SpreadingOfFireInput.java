package spreadingoffire;

import xml.FireXMLFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import base.UserInput;

public class SpreadingOfFireInput extends UserInput{
	private Scene fireScene;
	private SpreadingOfFireSimulation fireSim;
	
	public SpreadingOfFireInput(Stage s, FireXMLFactory factory, 
			SpreadingOfFireSimulation mySim) {
		super(s);
		this.fireSim = mySim;
	}

	public void selectProbCatch(){
		
	}

	@Override
	public void manualInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startXMLSimulation() {
		// TODO Auto-generated method stub
		fireScene = fireSim.init(stage);
		stage.setScene(fireScene);
		stage.show();
		
	}
}
