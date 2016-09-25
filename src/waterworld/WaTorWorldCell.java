package waterworld;

import base.Cell;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class WaTorWorldCell extends Cell{
    
    private int breedTime;
    private int starveTime = -1;
    
    public enum State{EMPTY, FISH, SHARK};
    
    private State currState;

	public WaTorWorldCell(int sizeOfCell, Pane rootElement, int xCoord,
			int yCoord, State currState) {
		super(sizeOfCell, rootElement, xCoord, yCoord);
		this.currState = currState;
	}

    public State getState () {
        return currState;
    }

    public void setState (State currState) {
        this.currState = currState;
        updateColor();
    }

    public int getBreedTime () {
        return breedTime;
    }

    public void setBreedTime (int breedTime) {
        this.breedTime = breedTime;
    }

    public int getStarveTime () {
        return starveTime;
    }

    public void setStarveTime (int starveTime) {
        this.starveTime = starveTime;
    }
    
    public void decrementBreedTime(){
        breedTime --;
    }
    
    public void decrementStarveTime(){
        starveTime --;
    }
    
    public void updateColor(){
        if(currState == State.EMPTY){
            setColor(Color.BLUE);
        }else if(currState == State.FISH){
            setColor(Color.GREEN);
        }else{
            setColor(Color.YELLOW);
        }
    }
    




}
