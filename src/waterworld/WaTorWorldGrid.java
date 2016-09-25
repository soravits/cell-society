package waterworld;

import base.Cell;
import base.Grid;
import javafx.scene.layout.Pane;
import waterworld.WaTorWorldCell.State;

public class WaTorWorldGrid extends Grid{

    public Cell[][] getGrid(){
        return grid;
    }

    public WaTorWorldGrid(int rowLength, int sizeOfCell, Pane rootElement,
                          int initialX, int initialY) {
        super(rowLength, sizeOfCell, rootElement, initialX, initialY);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void initializeGrid() {
        for(int i=0; i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                WaTorWorldCell gridCell = new WaTorWorldCell(sizeOfCell, rootElement, sizeOfCell 
                                                             * (i) + initialX,sizeOfCell* (j) + initialY, State.EMPTY);
                gridCell.addToScene();
                grid[i][j] = gridCell;                          
            }
        } 
    }

    public WaTorWorldCell getCell(int x, int y){
        return (WaTorWorldCell) grid[x][y];
    }
}
