package gameoflife;

import java.util.Arrays;
import base.Simulation;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Brian
 *
 */
public class GameOfLifeSimulation extends Simulation{
    private GameOfLifeGrid myGrid;
    private boolean[][] deadOrAlive;
    private boolean[][] DoATimeBuffer;

    /**
     * @param gridLength
     */
    public GameOfLifeSimulation(int gridLength) {
        super(gridLength);
    }

    /* (non-Javadoc)
     * @see base.Simulation#init(javafx.stage.Stage)
     */
    @Override
    public Scene init(Stage s) {
        setStage(s);
        setMyScene(new Scene(getRootElement(), SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE));  

        this.myGrid = new GameOfLifeGrid(getGridLength(),getCellSize(),getRootElement(),
        		getLeftMargin(),getTopMargin());
        myGrid.setBackground(SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT);
        deadOrAlive = new boolean[getGridLength()][getGridLength()];
        DoATimeBuffer = new boolean[getGridLength()][getGridLength()];
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        setInitialEnvironment();

        return getMyScene();
    }


    /* (non-Javadoc)
     * @see base.Simulation#step()
     */
    @Override
    public void step() {
        updateStateOfCells();
        updateCellStatus();
    }

    /* (non-Javadoc)
     * @see base.Simulation#setInitialEnvironment()
     */
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

    /**
     * 
     */
    public void updateCellStatus(){
        for(int i = 0; i<getGridLength();i++){
            for(int j=0; j<getGridLength();j++){
                if(DoATimeBuffer[i][j] == true){
                    deadOrAlive[i][j] = true;
                }
                else{
                    deadOrAlive[i][j] = false;
                }
            }
        }

        for(int i = 0; i<getGridLength();i++){
            for(int j=0; j<getGridLength();j++){
                if(deadOrAlive[i][j] == true){
                    reviveCell(i,j);
                }
                else{
                    killCell(i,j);
                }
            }
        }	
    }

    /**
     * @param row
     * @param col
     */
    private void killCell(int row, int col){
        myGrid.updateCell(row,col,false);
    }

    /**
     * @param row
     * @param col
     */
    private void reviveCell(int row, int col){
        myGrid.updateCell(row,col,true);
    }

    /**
     * @param row
     * @param column
     * @param aliveSurroundingCells
     */
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

    /**
     * 
     */
    public void updateStateOfCells(){
        for(int i = 0; i<getGridLength();i++){
            for(int j=0; j<getGridLength();j++){
                int aliveSurroundingCells = 0;
                aliveSurroundingCells += checkNearbyCells(i,j);
                updateCurrentCellState(i,j,aliveSurroundingCells);
            }
        }
    }

    /**
     * @param row
     * @param column
     * @return
     */
    private int checkNearbyCells(int row, int column){
        int aliveNearbyCells = 0;
        //BE CAREFUL, THIS ALGORITHM's DIAGANOL DETECTION DEPENDS ON FOUR SIDED BOUNDARIES
        boolean leftIsInBounds = ((row-1)>=0);
        boolean rightIsInBounds = ((row+1)<getGridLength());
        boolean upIsInBounds = ((column-1)>=0);
        boolean downIsInBounds = ((column+1)<getGridLength());

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

    /**
     * @param row
     * @param column
     * @return
     */
    private int ifAliveReturn1(int row, int column){
        if(deadOrAlive[row][column] == true){
            return 1;
        }
        return 0;
    }
}