package segregation;

import xml.SegregationXMLFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import base.UserInput;

public class SegregationInput extends UserInput{
	private Scene segScene;
	private Segregation segregation;
	
	public SegregationInput(Stage s, SegregationXMLFactory factory, Segregation mySim) {
		super(s);
		this.segregation = mySim;

//		System.out.println(stage);
		// TODO Auto-generated constructor stub
	}

	public void selectPercEmpty() {
		
	}
	
	public void selectPercA() {
		
	}
	
	public void selectPercB() {
		
	}
	
	public void selectSatisfyThresh() {
		
	}
	
	public void selectColors(){
		
	}

	@Override
	public void manualInput() {
		// TODO Auto-generated method stub
		selectPercEmpty();
		selectPercA();
		selectPercB();
		selectSatisfyThresh();
		selectColors();
	}

	@Override
	public void startXMLSimulation() {
		// TODO Auto-generated method stub
		segScene = segregation.init(stage);
		stage.setScene(segScene);
		stage.show();
	}
}
