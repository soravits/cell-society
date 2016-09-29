package segregation;

import segregation.SegregationCell.State;
import base.Cell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SegregationCell extends Cell{
	public enum State{EMPTY, COLORA, COLORB};
    private boolean manuallyModified = false;
    private State currState;
    
    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     */
    public SegregationCell(int sizeOfCell, Pane rootElement, int xCoord, int yCoord) {
        super(sizeOfCell, rootElement, xCoord, yCoord);
        this.currState = State.EMPTY;
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
     * @param currState
     */
    public void setState (State currState) {
        this.currState = currState;
        updateColor();
    }
    
    /**
     * 
     */
    public void updateColor(){
        if(currState == State.EMPTY){
            setColor(Color.WHITE);
        }else if(currState == State.COLORA){
            setColor(Color.DARKBLUE);
        }else{
            setColor(Color.LIMEGREEN);
        }
    }

//    /**
//     * 
//     */
//    public void burn(){
//        this.states = States.FIRE;
//    }
//
//    /**
//     * 
//     */
//    public void spawn(){
//        this.states = States.ALIVE;
//    }
//    
//    public void burnout(){
//    	this.states = States.DEAD;
//    }

    /**
     * @return
     */
    public State getState(){
        return this.currState;
    }
}
