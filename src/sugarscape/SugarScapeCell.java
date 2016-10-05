package sugarscape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import base.Cell;
import base.Simulation.CellType;
import java.util.Random;

/**
 * @author Delia
 *
 */
public class SugarScapeCell extends Cell {
	public enum State {PATCH, AGENT};
	
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
	
	private State currState;
    private Random random = new Random();

	/**
	 * @param sizeOfCell
	 * @param rootElement
	 * @param xCoord
	 * @param yCoord
	 * @param gridLength
	 * @param maxSugar
	 * @param maxCarbs
	 * @param minCarbs
	 * @param metabRate
	 * @param type
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
		if(currState == State.PATCH) {
			setSugarAmount(random.nextInt(maxSugar));
		}
		else {
			setAgentCarbs(random.nextInt(agentMaxCarbs - agentMinCarbs) + agentMinCarbs);
		}
		updateColor();
	}
	
	/**
	 * 
	 */
	public void setAgentMovedPatch() {
		this.currState = State.PATCH;
		setSugarAmount(0);
		updateColor();
	}
	
	/**
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
	 * @return
	 */
	public int getSugarAmount() {
		return sugarAmt;
	}
	
	/**
	 * @param sugarAmount
	 */
	public void setSugarAmount(int sugarAmount) {
		sugarAmt = sugarAmount;
	}
	
	/**
	 * 
	 */
	public void growSugarBack() {
		if(sugarAmt < maxSugar) {
			sugarAmt += growback;
		}
		updateColor();
	}
	
	/**
	 * @param agentPasta
	 */
	public void setAgentCarbs(int agentPasta) {
		agentCarbs = agentPasta;
	}
	
	/**
	 * @return
	 */
	public int getAgentCarbs() {
		return agentCarbs;
	}
	
	/**
	 * @param agentVision
	 */
	public void setAgentVision(int agentVision) {
		vision = agentVision;
	}
	
	/**
	 * @return
	 */
	public int getAgentVision() {
		return vision;
	}
	
	/**
	 * @param distanceMoved
	 */
	public void burnAgentCalories(int distanceMoved) {
		agentCarbs -= (metabRate * distanceMoved);
		if(agentCarbs < 0) killAgent();
	}
	
	/**
	 * 
	 */
	private void killAgent() {
		setSugarAmount(0);
		setState(State.PATCH);
	}

	/**
	 * 
	 */
	public void updateColor() {
		if(currState == State.PATCH && sugarAmt == 0) {
			setColor(Color.WHITE);
		}
		else if(currState == State.PATCH) {
			setColor(Color.rgb(0, 275 - sugarAmt * 20, 255));
		}
		else {
			setColor(Color.BLACK);
		}
	}
}
