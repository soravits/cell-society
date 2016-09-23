package segregation;

import gameoflife.GameOfLifeCell;

import java.util.Arrays;
import java.util.Collections;

import base.Cell;
import base.Grid;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SegregationGrid extends Grid{

	public SegregationGrid(int rowLength, int sizeOfCell, Pane rootElement,
			int initialX, int initialY) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY);
	}
	
	public Cell getCell(int row, int column){
		return (Cell) grid[row][column];
	}

	@Override
	public void initializeGrid() {
        for(int i=0; i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                    Cell gridCell = new Cell(sizeOfCell, rootElement, sizeOfCell 
                                    * (i) + initialX,sizeOfCell* (j) + initialY);
//                    gridCell.fillCellWithColors();
                    gridCell.addToScene();
                    grid[i][j] = gridCell;                          
            }
    }      
		
	}
	
	//how should i make this work
	public void randomize(){
		for(int i = 0; i < rowLength; i++){
			Collections.shuffle(Arrays.asList(grid[i]));
		}
		Collections.shuffle(Arrays.asList(grid));
		
	}

	
    public void updateCell(int x, int y, int cellState){
        if(cellState == 0){
            grid[x][y].setColor(Color.WHITE);
        }else if(cellState == 1){
            grid[x][y].setColor(Color.BLUE);
        }else{
            grid[x][y].setColor(Color.RED);
        }
    }
}
