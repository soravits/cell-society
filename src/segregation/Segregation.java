package segregation;

import java.util.Arrays;

import controller.MainMenu;
import spreadingoffire.SpreadingOfFireGrid;
import base.Cell;
import base.Simulation;
import gameoflife.GameOfLifeCell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Segregation extends Simulation{
	
    private SegregationGrid grid;
    private int[][] cellStates;
    private Timeline animation;
    private double satisfyThresh, percA, percB, percEmpty;
    // 0 is empty
    // 1 is group 1
    // 2 is group 2
	
	public Segregation(int gridLength, double threshold, double percentA, double percentEmpty) {
		super(gridLength);
		this.satisfyThresh = threshold;
		this.percA = percentA;
//		this.percB = percentB;
		this.percEmpty = percentEmpty;
	}

	private SegregationGrid myGrid;

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startSimulation() {
        KeyFrame frame = new KeyFrame(Duration.millis(MainMenu.MILLISECOND_DELAY * 100),
                e -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	@Override
	public void stopSimulation() {
		// TODO Auto-generated method stub
		animation.stop();
		
	}

	@Override
	public Scene init(Stage s) {
        stage = s;
        myScene = new Scene(rootElement, SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE);  
        int lengthOfGridInPixels = gridLength * Cell.cellSize - 100;
        int marginOnSidesOfGrid = (SIMULATION_WINDOW_WIDTH - lengthOfGridInPixels)/2;
        int marginTop = SIMULATION_WINDOW_HEIGHT/8;

        this.myGrid = new SegregationGrid(gridLength,Cell.cellSize,rootElement,marginOnSidesOfGrid - 50, 
        		marginTop - 50);
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        cellStates = new int[gridLength][gridLength];
        setInitialEnvironment();

        return myScene;
	}
	
	public void setInitialEnvironment(){
		int numWhite = (int) (percEmpty * totalCells);
		int numA = (int) (percA * (totalCells - numWhite));
//		int numB = totalCells - numWhite - numA;
		int currentCell = 0;

        for(int i = 0; i < gridLength; i++){
            for(int j = 0; j < gridLength; j++){
            	if(currentCell <= numWhite){
            		myGrid.updateCell(i, j, 0);
            	}
            	else if(currentCell <= numWhite + numA){
            		myGrid.updateCell(i, j, 1);
            	}
            	else myGrid.updateCell(i, j, 2);
            	
            	currentCell++;
            }
        }
        
        myGrid.randomize();
	}
	


}
