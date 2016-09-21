package cellsociety_team04;

import javafx.scene.layout.Pane;

public class SegregationGrid extends Grid{

	public SegregationGrid(int rowLength, int sizeOfCell, Pane rootElement,
			int initialX, int initialY) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY);
	}
	
	public SegregationCell getCell(int row, int column){
		return (SegregationCell) grid[row][column];
	}

	@Override
	public void initializeGrid() {
		// TODO Auto-generated method stub
		
	}

}
