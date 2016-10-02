package spreadingoffire;

//Hex under user input still doesn't work. everything else looks good

import xml.FireXMLFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import base.UserInput;
import base.Simulation.CellType;

public class SpreadingOfFireInput extends UserInput{
	private Scene fireScene;
	private String fireString = "Spreading of Fire";
	private SpreadingOfFireSimulation fireSim;
	private Spinner<Double> probCatchSpinner;
	
	public SpreadingOfFireInput(Stage s, FireXMLFactory factory, 
			SpreadingOfFireSimulation mySim) {
		super(s);
		this.fireSim = mySim;
	}

	public void selectProbCatch(){
		probCatchSpinner = new Spinner<>(0.05, 0.95, 0.5, 0.05);
		probCatchSpinner.setEditable(true);
		getGrid().add(new Label("Probability tree ignites"), 0, 1);
		getGrid().add(probCatchSpinner, 1, 1);
	}
	

	@Override
	public void startXMLSimulation() {
		// TODO Auto-generated method stub
		fireScene = fireSim.init(stage,CellType.SQUARE);
		stage.setScene(fireScene);
		stage.show();
		
	}

	@Override
	public void startManualSimulation(CellType type) {
//		int inputValue = gridSizeSpinner.getValue();
//		if((gridSizeSpinner.getValue() > 45)){
//			inputValue = 45;
//		}
		fireSim = new SpreadingOfFireSimulation(getGridSize(), 
				probCatchSpinner.getValue(),type);
		fireScene = fireSim.init(stage,type);
		stage.setScene(fireScene);
		stage.show();
		
	}


	@Override
	public void generateNodes() {
		selectGridSize();
		selectProbCatch();
		//		selectColors();
		getGrid().add(beginHexButton(fireString), 0, 6);
		getGrid().add(beginTriangleButton(fireString), 0, 7);
		getGrid().add(beginSquareButton(fireString), 0, 8);
		
	}
}