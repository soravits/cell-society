package spreadingoffire;

import base.Cell;
import javafx.scene.layout.Pane;

public class SpreadingOfFireCell extends Cell{
	public enum States{FIRE, DEAD, ALIVE};
    private States states;
    private boolean manuallyModified = false;
    
    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     */
    public SpreadingOfFireCell(int sizeOfCell, Pane rootElement, int xCoord, int yCoord) {
        super(sizeOfCell, rootElement, xCoord, yCoord);
        this.states= States.ALIVE;
    }
    
    public void setAsManuallyModified(){
    	this.manuallyModified = true;
    }
    
    public boolean isManuallyModified(){
    	return manuallyModified;
    }
    
    public void noLongerManuallyModified(){
    	this.manuallyModified = false;
    }

    /**
     * 
     */
    public void burn(){
        this.states = States.FIRE;
    }

    /**
     * 
     */
    public void spawn(){
        this.states = States.ALIVE;
    }
    
    public void burnout(){
    	this.states = States.DEAD;
    }

    /**
     * @return
     */
    public States getState(){
        return this.states;
    }
}
