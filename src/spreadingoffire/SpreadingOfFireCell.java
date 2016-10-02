package spreadingoffire;
import base.Cell;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
public class SpreadingOfFireCell extends Cell {
	public enum States{BURNING, DEAD, ALIVE, CAUGHTFIRE};
    private States states;
    
    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     */
    public SpreadingOfFireCell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord,int gridLength,CellType type) {
        super(sizeOfCell, rootElement, xCoord, yCoord,gridLength,type);
        this.states = States.ALIVE;
    }
    
    /**
     * 
     */
    public void burn() {
        this.states = States.BURNING;
    }

    public void catchfire(){
        this.states = States.CAUGHTFIRE;
    }
    /**
     * 
     */
    public void spawn() {
        this.states = States.ALIVE;
    }
    
    public void burnout() {
    	this.states = States.DEAD;
    }
    /**
     * @return
     */
    public States getState() {
        return this.states;
    }
}