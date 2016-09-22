package segregation;

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
		// TODO Auto-generated method stub
		
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
