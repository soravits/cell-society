package spreadingoffire;

import base.Grid;
import gameoflife.GameOfLifeCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SpreadingOfFireGrid extends Grid{

    public SpreadingOfFireGrid(int rowLength, int sizeOfCell, Pane rootElement,
                           int initialX, int initialY) {
                   super(rowLength, sizeOfCell, rootElement, initialX, initialY);
           }

    @Override
    public void initializeGrid() {
            for(int i=0; i<grid.length;i++){
                    for(int j=0;j<grid[0].length;j++){
                            GameOfLifeCell gridCell = new GameOfLifeCell(sizeOfCell, rootElement, sizeOfCell 
                                            * (i) + initialX,sizeOfCell* (j) + initialY);
                            gridCell.fillCellWithColors();
                            gridCell.addToScene();
                            grid[i][j] = gridCell;                          
                    }
            }       
    }
    
    public void updateCell(int x, int y, int cellState){
        if(cellState == 0){
            grid[x][y].setColor(Color.YELLOW);
        }else if(cellState == 1){
            grid[x][y].setColor(Color.FORESTGREEN);
        }else{
            grid[x][y].setColor(Color.BROWN);
        }
    }
    
}
