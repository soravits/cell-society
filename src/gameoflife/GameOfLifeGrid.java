package gameoflife;

import base.Cell;
import base.Grid;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Brian
 *
 */
public class GameOfLifeGrid extends Grid{

    /**
     * @param rowLength
     * @param sizeOfCell
     * @param rootElement
     * @param initialX
     * @param initialY
     */
    public GameOfLifeGrid(int rowLength, int sizeOfCell, Pane rootElement, 
                          int initialX, int initialY) {
        super(rowLength, sizeOfCell, rootElement, initialX, initialY);
    }

    /**
     * @param row
     * @param col
     * @param cellstate
     */
    public void updateCell(int row, int col, boolean cellstate){
        if(cellstate){
            getGrid()[row][col].setColor(Color.WHITE);
        }
        else{
            getGrid()[row][col].setColor(Color.BLACK);
            getGrid()[row][col].setBorder(Color.WHITE);
        }
    }

    /* (non-Javadoc)
     * @see base.Grid#initializeGrid()
     */
    @Override
    public void initializeGrid() {
        for(int i=0; i<getGrid().length;i++){
            for(int j=0;j<getGrid()[0].length;j++){
                Cell gridCell = new Cell(getSizeOfCell(), getRootElement(), getSizeOfCell() 
                                         * (i) + getInitialX(),getSizeOfCell()* (j) + getInitialY());
                gridCell.fillCellWithColors();
                gridCell.addToScene();
                getGrid()[i][j] = gridCell;				
            }
        }	
    }
}