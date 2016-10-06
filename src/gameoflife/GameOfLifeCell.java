package gameoflife;
import base.Cell;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;

/**
 * Represents a cell in the Game of Life grid.
 * @author Brian
 */
public class GameOfLifeCell extends Cell {
	
	public enum States{ALIVE, DEAD};
	private States states;
	
	/**
	 * @param sizeOfCell
	 * @param rootElement
	 * @param xCoord
	 * @param yCoord
	 * @param gridLength
	 * @param type
	 */
	public GameOfLifeCell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord, 
			int gridLength, CellType type) {
		super(sizeOfCell, rootElement, xCoord, yCoord,gridLength,type);
		this.states = States.DEAD;
	}
	
	/**
	 * Sets cell to dead state
	 */
	public void killCell() {
		this.states = States.DEAD;
	}
	
	/**
	 * Sets cell to alive state
	 */
	public void reviveCell() {
		this.states = States.ALIVE;
	}
	/**
	 * @return State of cell
	 */
	public States getState() {
		return this.states;
	}
}