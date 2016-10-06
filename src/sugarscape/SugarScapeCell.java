package sugarscape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import base.Cell;
import base.Simulation.CellType;
import java.util.Random;

/**
 * This is the Cell class for the SugarScape simulation.
 * @author Delia
 *
 */
public class SugarScapeCell extends Cell {
	public enum State {PATCH, AGENT};
	private State currState;
    private Random random = new Random();
    private static final int RED = 0;
    private static final int BLUE = 255;
    private static final int COLOR_CHANGE = 20;
    private static final int INITIAL_GREEN = 275;
    
	//Patch variables
	private int maxSugar = 4;
	private int sugarAmt = 0;
	private int growback = 1;
	
	//Agent variables
	private int agentCarbs = 1;
	private int metabRate = 1;
	private int vision = 1;
	private int agentMaxCarbs = 25;
	private int agentMinCarbs = 5;

	/**
	 * @param sizeOfCell	int, just read the variable name
	 * @param rootElement	Pane, where nodes are added 
	 * @param xCoord		int, x coordinate of cell's location
	 * @param yCoord		int, y ''
	 * @param gridLength	int, total length of grid, which should always be 51
	 * @param maxSugar		int, highest amount of sugar a patch can have
	 * @param maxCarbs		int, highest amount of carbs an agent can start with
	 * @param minCarbs		int, lowest ''
	 * @param metabRate		int, amount of carbs burned for each unit of distance agent moves
	 * @param type			CellType, shape of this cell
	 */
	public SugarScapeCell(int sizeOfCell, Pane rootElement, double xCoord,
			double yCoord, int gridLength, int maxSugar, int maxCarbs, int minCarbs, 
			int metabRate, CellType type) {
		super(sizeOfCell, rootElement, xCoord, yCoord, gridLength, type);
		this.maxSugar = maxSugar;
		this.agentMaxCarbs = maxCarbs;
		this.agentMinCarbs = minCarbs;
		this.metabRate = metabRate;
	}
	
	/**
	 * @return	whether cell is patch or agent
	 */
	public State getState() {
		return this.currState;
	}

	/**
	 * Sets cell's state, gives cell random sugar if patch, 
	 * random carbs if agent, and updates the color
	 * @param currState
	 */
	public void setState (State currState) {
		this.currState = currState;
		if(currState == State.PATCH) {
			setSugarAmount(random.nextInt(maxSugar));
		}
		else {
			setAgentCarbs(random.nextInt(agentMaxCarbs - agentMinCarbs) + agentMinCarbs);
		}
		updateColor();
	}
	
	/**
	 * Sets cell as empty patch after an agent has just moved out
	 */
	public void setAgentMovedPatch() {
		this.currState = State.PATCH;
		setSugarAmount(0);
		updateColor();
	}
	
	/**
	 * Sets cell as agent after an agent moves in and absorbs the sugar. 
	 * Updates agent data with added sugar amount from when this cell was a patch
	 * @param origCarbs
	 * @param newSugar
	 */
	public void setMovedAgent(int origCarbs, int newSugar) {
		this.currState = State.AGENT;
		this.agentCarbs = origCarbs;
		this.agentCarbs += newSugar;
		updateColor();
	}
	
	/**
	 * @return	amount of sugar patch contains
	 */
	public int getSugarAmount() {
		return sugarAmt;
	}
	
	/**
	 * Set patch with an amount of sugar
	 * @param sugarAmount
	 */
	private void setSugarAmount(int sugarAmount) {
		sugarAmt = sugarAmount;
	}
	
	/**
	 * Adds set amount of sugar to the patch until it reaches the maximum sugar it can hold
	 */
	public void growSugarBack() {
		if(sugarAmt < maxSugar) {
			sugarAmt += growback;
		}
		updateColor();
	}
	
	/**
	 * Sets amount of carbs an agent has
	 * @param agentPasta
	 */
	private void setAgentCarbs(int agentPasta) {
		agentCarbs = agentPasta;
	}
	
	/**
	 * @return	current amount of carbs an agent has
	 */
	public int getAgentCarbs() {
		return agentCarbs;
	}
	
	/**
	 * Sets amount of neighbors an agent can consider in any cardinal direction
	 * @param agentVision
	 */
	public void setAgentVision(int agentVision) {
		vision = agentVision;
	}
	
	/**
	 * @return	maximum distance an agent can choose to move
	 */
	public int getAgentVision() {
		return vision;
	}
	
	/**
	 * Subtracts carbs for distance an agent just moved, 
	 * removes agent if it runs out of carbs
	 * @param distanceMoved
	 */
	public void burnAgentCalories(int distanceMoved) {
		agentCarbs -= (metabRate * distanceMoved);
		if(agentCarbs < 0) killAgent();
	}
	
	/**
	 * Remove agent by setting it as an empty patch
	 */
	private void killAgent() {
		setSugarAmount(0);
		setState(State.PATCH);
	}

	/**
	 * updates color of cell based on its state and amount of sugar
	 */
	public void updateColor() {
		if(currState == State.PATCH && sugarAmt == 0) {
			setColor(Color.WHITE);
		}
		else if(currState == State.PATCH) {
			setColor(Color.rgb(RED, INITIAL_GREEN - sugarAmt * COLOR_CHANGE, BLUE));
		}
		else {
			setColor(Color.BLACK);
		}
	}
}
