package spreadingoffire;

import base.Simulation;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Soravit
 *
 */
public class SpreadingOfFireSimulation extends Simulation{

    private double probCatch;
    private SpreadingOfFireGrid myGrid;
    private int[][] cellStates;
    // 0 is empty
    // 1 is tree
    // 2 is burning tree

    /**
     * @param gridLength
     * @param probCatch
     */
    public SpreadingOfFireSimulation(int gridLength, double probCatch) {
        super(gridLength);
        this.probCatch = probCatch;
    }

    /* (non-Javadoc)
     * @see base.Simulation#init(javafx.stage.Stage)
     */
    @Override
    public Scene init (Stage s) {
        setStage(s);
        setMyScene(new Scene(getRootElement(), SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE));  
        this.myGrid = new SpreadingOfFireGrid(getGridLength(), getCellSize(), getRootElement(), getLeftMargin(), getTopMargin());
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        cellStates = new int[getGridLength()][getGridLength()];
        setInitialEnvironment();

        return getMyScene();
    }

    /**
     * @param x
     * @param y
     */
    public void spawnTree(int x, int y){
        cellStates[x][y] = 1;
        myGrid.updateCell(x,y,1);
    }

    /**
     * @param x
     * @param y
     * @param forceBurn
     */
    public void burnTree(int x, int y, boolean forceBurn){
        double rand = Math.random();
        if(rand < probCatch || forceBurn){
            cellStates[x][y] = 3;
            myGrid.updateCell(x,y,2);
        }
    }

    /**
     * @param x
     * @param y
     */
    public void clearCell(int x, int y){
        cellStates[x][y] = 0;
        myGrid.updateCell(x,y,0);
    }

    /* (non-Javadoc)
     * @see base.Simulation#setInitialEnvironment()
     */
    public void setInitialEnvironment(){
        for(int i = 0; i<getGridLength(); i++){
            for(int j = 0;j<getGridLength(); j++){
                if(i == 0 || i == getGridLength()-1 || j == 0 || j == getGridLength()-1){
                    clearCell(i,j);
                }else if(i == getGridLength()/2 && j == getGridLength()/2){
                    burnTree(i,j,true);
                }else{
                    spawnTree(i,j);
                }
                myGrid.updateCell(i, j, cellStates[i][j]);
            }
        }
    }

    /**
     * 
     */
    public void updateState(){
        for(int i = 0; i<getGridLength(); i++){
            for(int j = 0; j<getGridLength(); j++){
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

        for(int i = 0; i<getGridLength(); i++){
            for(int j = 0; j<getGridLength(); j++){
                if(cellStates[i][j] == 2){
                    clearCell(i,j);
                } else if(cellStates[i][j] == 3){
                    cellStates[i][j] = 2;
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see base.Simulation#step()
     */
    @Override
    public void step () {
        updateState();
    }


}