package gameoflife;
import base.Cell;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;

/**
 * @author Brian
 *
 */
public class GameOfLifeCell extends Cell {
	
	public enum States{ALIVE, DEAD};
	private States states;
	
	/**
	 * @param sizeOfCell
	 * @param rootElement
	 * @param d
	 * @param yCoord
	 */
	public GameOfLifeCell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord, 
			int gridLength, CellType type) {
		super(sizeOfCell, rootElement, xCoord, yCoord,gridLength,type);
		this.states = States.DEAD;
	}
	
	/**
	 * 
	 */
	public void killCell() {
		this.states = States.DEAD;
	}
	
	/**
	 * 
	 */
	public void reviveCell() {
		this.states = States.ALIVE;
	}
	/**
	 * @return
	 */
	public States getState() {
		return this.states;
	}
}