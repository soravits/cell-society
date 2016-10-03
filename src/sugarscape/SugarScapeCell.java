package sugarscape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import base.Cell;
import base.Simulation.CellType;

public class SugarScapeCell extends Cell {
	public enum State {PATCH, AGENT};
	
	//Patch variables
	private int maxSugar = 4;
	private int sugarAmt = 0;
	private int growback = 1;
	
	//Agent variables
	private int agentSugarStored = 1;
	private int metabRate = 1;
	private int vision = 1;
	
	private State currState;

	public SugarScapeCell(int sizeOfCell, Pane rootElement, double xCoord,
			double yCoord, int gridLength, CellType type) {
		super(sizeOfCell, rootElement, xCoord, yCoord, gridLength, type);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return
	 */
	public State getState() {
		return this.currState;
	}

	/**
	 * @param currState
	 */
	public void setState (State currState) {
		this.currState = currState;
		updateColor();
	}
	
	public int getSugarAmount(){
		return sugarAmt;
	}
	
	public void setSugarAmount(int sugarAmount){
		sugarAmt = sugarAmount;
	}
	
	public void growSugarBack(){
		sugarAmt += growback;
	}

	/**
	 * 
	 */
	public void updateColor() {
		if(currState == State.PATCH && sugarAmt == 0) {
			setColor(Color.WHITE);
		}
		else if(currState == State.PATCH) {
			setColor(Color.rgb(0, 255 - sugarAmt * 20, 255));
		}
		else {
			setColor(Color.BLACK);
		}
	}



}
