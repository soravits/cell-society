package spreadingoffire;

import java.util.Random;
import base.Cell;
import base.Simulation;
import controller.MainMenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SpreadingOfFireSimulation extends Simulation{

    private double probCatch;
    private SpreadingOfFireGrid myGrid;
    private int[][] cellStates;
    // 0 is empty
    // 1 is tree
    // 2 is burning tree

    public SpreadingOfFireSimulation(int gridLength, double probCatch) {
        super(gridLength);
        this.probCatch = probCatch;
    }

    @Override
    public Scene init (Stage s) {
        stage = s;
        myScene = new Scene(rootElement, SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE);  
        int lengthOfGridInPixels = gridLength * Cell.cellSize - 100;
        int marginOnSidesOfGrid = 200;
        int marginTop = SIMULATION_WINDOW_HEIGHT/8;

        this.myGrid = new SpreadingOfFireGrid(gridLength,cellSize,rootElement,marginOnSidesOfGrid, marginTop - 50);
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        cellStates = new int[gridLength][gridLength];
        setInitialEnvironment();

        return myScene;
    }

    public void spawnTree(int x, int y){
        cellStates[x][y] = 1;
        myGrid.updateCell(x,y,1);
    }

    public void burnTree(int x, int y, boolean forceBurn){
        double rand = Math.random();
        if(rand < probCatch || forceBurn){
            cellStates[x][y] = 3;
            myGrid.updateCell(x,y,2);
        }
    }

    public void clearCell(int x, int y){
        cellStates[x][y] = 0;
        myGrid.updateCell(x,y,0);
    }

    public void setInitialEnvironment(){
        for(int i = 0; i<gridLength; i++){
            for(int j = 0;j<gridLength; j++){
                if(i == 0 || i == gridLength-1 || j == 0 || j == gridLength-1){
                    clearCell(i,j);
                }else if(i == gridLength/2 && j == gridLength/2){
                    burnTree(i,j,true);
                }else{
                    spawnTree(i,j);
                }
                myGrid.updateCell(i, j, cellStates[i][j]);
            }
        }
    }

    public void updateState(){
        for(int i = 0; i<gridLength; i++){
            for(int j = 0; j<gridLength; j++){
                if(cellStates[i][j] == 1){
                    if(cellStates[i-1][j] == 2){
                        burnTree(i,j,false);
                    } 
                    else if(cellStates[i+1][j] == 2){
                        burnTree(i,j,false);
                    }  
                    else if(cellStates[i][j-1] == 2){
                        burnTree(i,j,false);
                    }  
                    else if(cellStates[i][j+1] == 2){
                        burnTree(i,j,false);
                    }               
                } 
            }
        }

        for(int i = 0; i<gridLength; i++){
            for(int j = 0; j<gridLength; j++){
                if(cellStates[i][j] == 2){
                    clearCell(i,j);
                } else if(cellStates[i][j] == 3){
                    cellStates[i][j] = 2;
                }
            }
        }
    }

    @Override
    public void step () {
        updateState();
    }


}
