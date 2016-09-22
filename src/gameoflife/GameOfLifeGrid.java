package gameoflife;

import base.Cell;
import base.Grid;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameOfLifeGrid extends Grid{

	public GameOfLifeGrid(int rowLength, int sizeOfCell, Pane rootElement, 
			int initialX, int initialY) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY);
	}
	
	 public void updateCell(int row, int col, boolean cellstate){
	        if(cellstate){
	            grid[row][col].setColor(Color.WHITE);
	        }
	        else{
	        	grid[row][col].setColor(Color.BLACK);
	        }
	    }

	@Override
	public void initializeGrid() {
		for(int i=0; i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				Cell gridCell = new Cell(sizeOfCell, rootElement, sizeOfCell 
						* (i) + initialX,sizeOfCell* (j) + initialY);
				gridCell.fillCellWithColors();
				gridCell.addToScene();
				grid[i][j] = gridCell;				
			}
		}	
	}

}
