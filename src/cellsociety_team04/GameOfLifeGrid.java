package cellsociety_team04;

import javafx.scene.layout.Pane;

public class GameOfLifeGrid extends Grid{

	public GameOfLifeGrid(int rowLength, int sizeOfCell, Pane rootElement, int initialX, int initialY) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY);
	}
	
	public GameOfLifeCell getCell(int row, int column){
		return (GameOfLifeCell) grid[row][column];
	}

	@Override
	public void initializeGrid() {
		for(int i=0; i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				GameOfLifeCell gridCell = new GameOfLifeCell(sizeOfCell,rootElement,sizeOfCell * (i) + initialX,sizeOfCell*(j) + initialY);
				gridCell.fillCellWithColors();
				gridCell.addToScene();
				grid[i][j] = gridCell;				
			}
		}	
	}

}
