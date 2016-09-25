package waterworld;

import base.Grid;
import javafx.scene.layout.Pane;
import waterworld.WaTorWorldCell.State;

public class WaTorWorldGrid extends Grid{

    public WaTorWorldGrid(int rowLength, int sizeOfCell, Pane rootElement,
                          int initialX, int initialY) {
        super(rowLength, sizeOfCell, rootElement, initialX, initialY);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void initializeGrid() {
        for(int i=0; i<getGrid().length;i++){
            for(int j=0;j<getGrid()[0].length;j++){
                WaTorWorldCell gridCell = new WaTorWorldCell(getSizeOfCell(), getRootElement(), getSizeOfCell() 
                                                             * (i) + getInitialX(),getSizeOfCell()* (j) + getInitialY(), State.EMPTY);
                gridCell.addToScene();
                getGrid()[i][j] = gridCell;                          
            }
        } 
    }

    public WaTorWorldCell getCell(int x, int y){
        return (WaTorWorldCell) getGrid()[x][y];
    }
}