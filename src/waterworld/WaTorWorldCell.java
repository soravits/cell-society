package waterworld;

import base.Cell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Soravit
 *
 */
public class WaTorWorldCell extends Cell {
    
    private int breedTime;
    private int starveTime = -1;
    
    public enum State{EMPTY, FISH, SHARK};
    
    private State currState;

	/**
	 * @param sizeOfCell
	 * @param rootElement
	 * @param xCoord
	 * @param yCoord
	 * @param currState
	 */
	public WaTorWorldCell(int sizeOfCell, Pane rootElement, int xCoord,
			int yCoord, State currState) {
		super(sizeOfCell, rootElement, xCoord, yCoord);
		this.currState = currState;
	}
	 
    /**
     * @return
     */
    public State getState () {
        return currState;
    }

    /**
     * @param currState
     */
    public void setState (State currState) {
        this.currState = currState;
        updateColor();
    }

    /**
     * @return
     */
    public int getBreedTime () {
        return breedTime;
    }

    /**
     * @param breedTime
     */
    public void setBreedTime (int breedTime) {
        this.breedTime = breedTime;
    }

    /**
     * @return
     */
    public int getStarveTime () {
        return starveTime;
    }

    /**
     * @param starveTime
     */
    public void setStarveTime (int starveTime) {
        this.starveTime = starveTime;
    }
    
    /**
     * 
     */
    public void decrementBreedTime() {
        breedTime --;
    }
    
    /**
     * 
     */
    public void decrementStarveTime() {
        starveTime --;
    }
    
    /**
     * 
     */
    public void updateColor() {
        if(currState == State.EMPTY) {
            setColor(Color.BLUE);
        }
        else if(currState == State.FISH) {
            setColor(Color.GREEN);
        }
        else {
            setColor(Color.YELLOW);
        }
    }
}