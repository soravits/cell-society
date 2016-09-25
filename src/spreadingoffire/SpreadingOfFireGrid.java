package spreadingoffire;

import base.Cell;
import base.Grid;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SpreadingOfFireGrid extends Grid{

    public SpreadingOfFireGrid(int rowLength, int sizeOfCell, Pane rootElement,
                           int initialX, int initialY) {
                   super(rowLength, sizeOfCell, rootElement, initialX, initialY);
           }

    @Override
    public void initializeGrid() {
            for(int i=0; i<getGrid().length;i++){
                    for(int j=0;j<getGrid()[0].length;j++){
                            Cell gridCell = new Cell(getSizeOfCell(), getRootElement(), getSizeOfCell()
                                            * (i) + getInitialX(),getSizeOfCell()* (j) + getInitialY());
                            gridCell.addToScene();
                            getGrid()[i][j] = gridCell;                          
                    }
            }       
    }
    
    public void updateCell(int x, int y, int cellState){
        if(cellState == 0){
            getGrid()[x][y].setColor(Color.YELLOW);
        }else if(cellState == 1 || cellState == 3){
            getGrid()[x][y].setColor(Color.FORESTGREEN);
        }else{
            getGrid()[x][y].setColor(Color.BROWN);
        }
    }
    
}