package sugarscape;

import javafx.scene.Scene;
import javafx.stage.Stage;
import base.Simulation;

/**
 * PLAN preset 1: implement like segregation
 * Cell: 2 states- patch and agent
 * 	patch data: amount of sugar, max sugar capacity, color indicating amount of sugar, sugar growback rate
 * 	agent data: current amount of sugar stored, rate of sugar metabolism, and vision
 * 
 * plan preset 2: pretty much the same but with different parameter ranges
 */

public class SugarScapeSimulation extends Simulation {

	public SugarScapeSimulation(int myGridLength, CellType type) {
		super(myGridLength, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Scene init(Stage s, CellType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInitialEnvironment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
