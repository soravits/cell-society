package segregation;

import base.Cell;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SegregationCell extends Cell {
	
	public enum State {EMPTY, COLORA, COLORB};
//    private boolean manuallyModified = false;
    private State currState;
    
    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     */
    public SegregationCell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord,int gridLength,CellType type) {
        super(sizeOfCell, rootElement, xCoord, yCoord,gridLength,type);
        this.currState = State.EMPTY;
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
    public void updateColor() {
        if(currState == State.EMPTY) {
            setColor(Color.WHITE);
        }
        else if(currState == State.COLORA) {
            setColor(Color.DARKBLUE);
        }
        else {
            setColor(Color.LIMEGREEN);
        }
    }

    /**
     * @return
     */
    public State getState() {
        return this.currState;
    }
}
