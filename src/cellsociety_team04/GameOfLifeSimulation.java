package balloonDodge;

import java.util.Arrays;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameOfLifeSimulation extends Simulation{
	
	private GameOfLifeGrid myGrid;
	
	public GameOfLifeSimulation(int gridLength) {
		super(gridLength);
	}

	public void beginSimulation(){
		System.out.println("Doing actual simulation");
		boolean[][] deadOrAlive = new boolean[gridLength][gridLength];
		for (boolean[] row: deadOrAlive){
		    Arrays.fill(row, false);
		}
		for(int i = 0; i<gridLength;i++){
			for(int j=0; j<gridLength;j++){
				deadOrAlive[i][j] = checkStateOfCell(i,j);
			}
		}
		
		for(int i = 0; i<gridLength;i++){
			for(int j=0; j<gridLength;j++){
				if(deadOrAlive[i][j] == true){
					System.out.println("Reviving Cell");
					myGrid.getCell(i, j).reviveCell();
				}
				else{
					System.out.println("Reviving Cell");
					myGrid.getCell(j, j).killCell();
				}
			}
		}
		
	}
	public boolean checkStateOfCell(int row, int column){
		GameOfLifeCell currentCell = myGrid.getCell(row,column);
		int aliveSurroundingCells = 0;
		aliveSurroundingCells += checkNearbyCells(row,column);
		return currentCell.checkCurrentCellState(aliveSurroundingCells);
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
		return myGrid.getCell(row,column).getStatusCode();
	}
	
	public void startSimulation(){
		beginSimulation();
		/*long startingTime = System.currentTimeMillis();
		stillSimulating = true;
		while(stillSimulating){
			if(System.currentTimeMillis() - startingTime >= 5000){
				System.out.println("Starting new Simulation");
				startingTime = System.currentTimeMillis();
				
			}
		}*/
	}

	public void stopSimulation(){
		//stillSimulating = false;
	}
	
	@Override
	public Scene init(Stage s) {
		stage = s;
        myScene = new Scene(rootElement, SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE);  
        int lengthOfGridInPixels = gridLength * Cell.cellSize - 100;
        int marginOnSidesOfGrid = (SIMULATION_WINDOW_WIDTH - lengthOfGridInPixels)/2;
        int marginTop = SIMULATION_WINDOW_HEIGHT/8;
        
        this.myGrid = new GameOfLifeGrid(gridLength,Cell.cellSize,rootElement,marginOnSidesOfGrid,marginTop);
        myGrid.initializeGrid();
        myGrid.giveSimulationProfile(this);
        myGrid.setUpButtons();

        return myScene;
	}
}
