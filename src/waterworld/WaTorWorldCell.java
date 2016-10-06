package waterworld;
import base.Cell;
import base.Simulation.CellType;
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
     * @param sizeOfCell The size of the cell
     * @param rootElement The JavaFX pane
     * @param xCoord The x position
     * @param yCoord The y position
     * @param currState The state of the cell
     * @param type The shape of a cell
     */
	public WaTorWorldCell(int sizeOfCell, Pane rootElement, double xCoord,
			double yCoord, State currState, int gridLength, CellType type) {
		super(sizeOfCell, rootElement, xCoord, yCoord, gridLength, type);
		this.currState = currState;
	}
	 
    /**
     * @return The state of the cell
     */
    public State getState () {
        return currState;
    }
    
    /**
     * @param currState The desired state of a cell
     */
    public void setState (State currState) {
        this.currState = currState;
        updateColor();
    }
    
    /**
     * @return The time it takes to breed
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
     * 
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
     * Decrements the breed time
     */
    public void decrementBreedTime() {
        breedTime --;
    }
    
    /**
     * Decrements the starve time
     */
    public void decrementStarveTime() {
        starveTime --;
    }
    
    /**
     * Updates the color based on current state
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