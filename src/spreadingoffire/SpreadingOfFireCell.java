package spreadingoffire;
import base.Cell;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;

/**
 * @author Soravit
 *
 */
public class SpreadingOfFireCell extends Cell {
	
	public enum States{BURNING, DEAD, ALIVE, CAUGHTFIRE};
    private States states;
    
    /**
     * @param sizeOfCell The size of the cell
     * @param rootElement The JavaFX pane
     * @param xCoord The x position
     * @param yCoord The y position
     * @param gridLength The length of a side of the grid
     * @param type The shape of a cell
     */
    public SpreadingOfFireCell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord, 
    		int gridLength, CellType type) {
        super(sizeOfCell, rootElement, xCoord, yCoord,gridLength,type);
        this.states = States.ALIVE;
    }
    
    /**
     * Sets the state of the cell to burning
     */
    public void burn() {
        this.states = States.BURNING;
    }

    /**
     * Sets the state of the cell to the tree catching fire but not burning
     */
    public void catchFire(){
        this.states = States.CAUGHTFIRE;
    }
    /**
     * Sets the state of the cell to an alive tree
     */
    public void spawn() {
        this.states = States.ALIVE;
    }
    
    /**
     * Sets the state of the cell to a dead tree or no tree
     */
    public void burnout() {
    	this.states = States.DEAD;
    }
    /**
     * @return Gets the current state of the cell
     */
    public States getState() {
        return this.states;
    }
}