package gameoflife;

import base.Cell;
import javafx.scene.layout.Pane;

/**
 * @author Brian
 *
 */
public class GameOfLifeCell extends Cell{
    public enum States{ALIVE, DEAD};
    private States states;
    private boolean manuallyModified = false;
    
    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     */
    public GameOfLifeCell(int sizeOfCell, Pane rootElement, int xCoord, int yCoord) {
        super(sizeOfCell, rootElement, xCoord, yCoord);
        this.states= States.DEAD;
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
    public void killCell(){
        this.states = States.DEAD;
    }

    /**
     * 
     */
    public void reviveCell(){
        this.states = States.ALIVE;
    }

    /**
     * @return
     */
    public States getState(){
        return this.states;
    }
}