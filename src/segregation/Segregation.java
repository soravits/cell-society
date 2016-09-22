package segregation;

import java.util.Arrays;
import base.Simulation;
import gameoflife.GameOfLifeCell;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Segregation extends Simulation{
	private SegregationGrid myGrid;
	
	public Segregation(int gridLength) {
		super(gridLength);
	}

	@Override
	public void startSimulation() {
		System.out.println("Doing actual simulation");
		boolean[][] isSatisfied = new boolean[gridLength][gridLength];
		for (boolean[] row: isSatisfied){
		    Arrays.fill(row, false);
		}
		for(int i = 0; i<gridLength;i++){
			for(int j=0; j<gridLength;j++){
				isSatisfied[i][j] = checkStateOfCell(i,j);
			}
		}
		
		for(int i = 0; i<gridLength;i++){
			for(int j=0; j<gridLength;j++){
				if(isSatisfied[i][j] == true){
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

	@Override
	public void stopSimulation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Scene init(Stage s) {
		// TODO Auto-generated method stub
		return null;
	}

}
