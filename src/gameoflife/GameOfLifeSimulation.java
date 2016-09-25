package gameoflife;

import java.util.Arrays;
import base.Cell;
import base.Simulation;
import controller.MainMenu;
import gameoflife.GameOfLifeCell.States;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOfLifeSimulation extends Simulation{
    private GameOfLifeGrid myGrid;
    private boolean[][] deadOrAlive;
    private boolean[][] DoATimeBuffer;

    public GameOfLifeSimulation(int gridLength) {
        super(gridLength);
    }

    @Override
    public Scene init(Stage s) {
        stage = s;
        myScene = new Scene(rootElement, SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE);  

        this.myGrid = new GameOfLifeGrid(gridLength,cellSize,rootElement,leftMargin,topMargin);
        deadOrAlive = new boolean[gridLength][gridLength];
        DoATimeBuffer = new boolean[gridLength][gridLength];
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        setInitialEnvironment();

        return myScene;
    }


    @Override
    public void step() {
        updateStateOfCells();
        updateCellStatus();
    }

    public void setInitialEnvironment(){
        for (boolean[] row: deadOrAlive){
            Arrays.fill(row, false);
        }
        for (boolean[] row: DoATimeBuffer){
            Arrays.fill(row, false);
        }
        deadOrAlive[deadOrAlive.length/2][deadOrAlive.length/2] = true;
        deadOrAlive[deadOrAlive.length/2 + 1][deadOrAlive.length/2] = true;
        deadOrAlive[deadOrAlive.length/2][deadOrAlive.length/2 + 1] = true;
        deadOrAlive[deadOrAlive.length/2 - 1 ][deadOrAlive.length/2] = true;
        deadOrAlive[deadOrAlive.length/2][deadOrAlive.length/2 - 1] = true;

        DoATimeBuffer[deadOrAlive.length/2][deadOrAlive.length/2] = true;
        DoATimeBuffer[deadOrAlive.length/2 + 1][deadOrAlive.length/2] = true;
        DoATimeBuffer[deadOrAlive.length/2][deadOrAlive.length/2 + 1] = true;
        DoATimeBuffer[deadOrAlive.length/2 - 1 ][deadOrAlive.length/2] = true;
        DoATimeBuffer[deadOrAlive.length/2][deadOrAlive.length/2 - 1] = true;
        updateCellStatus();
    }

    public void updateCellStatus(){
        for(int i = 0; i<gridLength;i++){
            for(int j=0; j<gridLength;j++){
                if(DoATimeBuffer[i][j] == true){
                    deadOrAlive[i][j] = true;
                }
                else{
                    deadOrAlive[i][j] = false;
                }
            }
        }

        for(int i = 0; i<gridLength;i++){
            for(int j=0; j<gridLength;j++){
                if(deadOrAlive[i][j] == true){
                    reviveCell(i,j);
                }
                else{
                    killCell(i,j);
                }
            }
        }	
    }

    private void killCell(int row, int col){
        myGrid.updateCell(row,col,false);
    }

    private void reviveCell(int row, int col){
        myGrid.updateCell(row,col,true);
    }

    public void updateCurrentCellState(int row, int column, int aliveSurroundingCells) {
        if(deadOrAlive[row][column] == true){
            DoATimeBuffer[row][column] = true;
            if((aliveSurroundingCells >= 3) || (aliveSurroundingCells < 2)){
                DoATimeBuffer[row][column] = false;
            }
        }
        else{
            if((aliveSurroundingCells == 3)){
                DoATimeBuffer[row][column] = true;
            }
        }
    }

    public void updateStateOfCells(){
        for(int i = 0; i<gridLength;i++){
            for(int j=0; j<gridLength;j++){
                int aliveSurroundingCells = 0;
                aliveSurroundingCells += checkNearbyCells(i,j);
                updateCurrentCellState(i,j,aliveSurroundingCells);
            }
        }
    }

    private int checkNearbyCells(int row, int column){
        int aliveNearbyCells = 0;
        //BE CAREFUL, THIS ALGORITHM's DIAGANOL DETECTION DEPENDS ON FOUR SIDED BOUNDARIES
        boolean leftIsInBounds = ((row-1)>=0);
        boolean rightIsInBounds = ((row+1)<gridLength);
        boolean upIsInBounds = ((column-1)>=0);
        boolean downIsInBounds = ((column+1)<gridLength);

        if(leftIsInBounds){
            aliveNearbyCells += ifAliveReturn1(row-1,column);
        }
        if(upIsInBounds){
            aliveNearbyCells += ifAliveReturn1(row,column-1);
        }
        if(rightIsInBounds){
            aliveNearbyCells += ifAliveReturn1(row+1,column);
        }
        if(downIsInBounds){
            aliveNearbyCells += ifAliveReturn1(row,column+1);
        }
        if(downIsInBounds && leftIsInBounds){
            aliveNearbyCells += ifAliveReturn1(row-1,column+1);
        }
        if(downIsInBounds && rightIsInBounds){
            aliveNearbyCells += ifAliveReturn1(row+1,column+1);
        }
        if(upIsInBounds && leftIsInBounds){
            aliveNearbyCells += ifAliveReturn1(row-1,column-1);
        }
        if(upIsInBounds && rightIsInBounds){
            aliveNearbyCells += ifAliveReturn1(row+1,column-1);
        }
        return aliveNearbyCells;
    }

    private int ifAliveReturn1(int row, int column){
        if(deadOrAlive[row][column] == true){
            return 1;
        }
        return 0;
    }
}
