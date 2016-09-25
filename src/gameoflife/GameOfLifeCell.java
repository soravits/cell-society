package gameoflife;

import base.Cell;
import javafx.scene.layout.Pane;

public class GameOfLifeCell extends Cell{
    public enum States{ALIVE, DEAD};

    private States states;
    public GameOfLifeCell(int sizeOfCell, Pane rootElement, int xCoord, int yCoord) {
        super(sizeOfCell, rootElement, xCoord, yCoord);
        this.states= States.DEAD;
    }

    public void killCell(){
        this.states = States.DEAD;
    }

    public void reviveCell(){
        this.states = States.ALIVE;
    }

    public States getStates(){
        return this.states;
    }

    public boolean checkCurrentCellState(int aliveSurroundingCells) {
        boolean isAlive = true;
        if(this.states == States.ALIVE){
            if((aliveSurroundingCells >= 3) || (aliveSurroundingCells < 2)){
                isAlive = false;
            }
        }
        else{
            if((aliveSurroundingCells == 3)){
                isAlive = true;
            }
        }
        return isAlive;
    }



}
