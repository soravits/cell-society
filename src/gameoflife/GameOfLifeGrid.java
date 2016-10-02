package gameoflife;

import base.Cell;
import base.Grid;
import base.Simulation.CellType;
import gameoflife.GameOfLifeCell.States;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import waterworld.WaTorWorldCell;
import waterworld.WaTorWorldCell.State;

/**
 * @author Brian
 *
 */
public class GameOfLifeGrid extends Grid{
    private GameOfLifeSimulation sim; 
    //make instance of itself instead of using a getter all the time?

    /**
     * @param rowLength
     * @param sizeOfCell
     * @param rootElement
     * @param initialX
     * @param initialY
     */
    public GameOfLifeGrid(int rowLength, int sizeOfCell, Pane rootElement, 
                          int initialX, int initialY, gridEdgeType edgeType, GameOfLifeSimulation sim) {
        super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
        this.sim = sim;
    }

    public GameOfLifeCell gridCell(int row, int col) {
        return (GameOfLifeCell) super.gridCell(row,col);
    }

    /**
     * @param row
     * @param col
     */
    public void updateCell(int row, int col) {
        GameOfLifeCell myCell = gridCell(row, col);
        if(myCell.getState() == States.ALIVE) {
            gridCell(row,col).setColor(Color.WHITE);
        }
        else {
            gridCell(row,col).setColor(Color.BLACK);
        }
        gridCell(row,col).setBorder(Color.WHITE);
    }

    /* (non-Javadoc)
     * @see base.Grid#initializeGrid()
     */
    @Override
    public void initializeGrid(CellType type) {
        for(int i = 0; i < getColumnLength(); i++) {
            for(int j = 0; j < getColumnLength(); j++) {
            	int horizontalOffset = getInitialX();
            	double horizontalShift = getSizeOfCell();
            	double verticalShift = getSizeOfCell();
            	if(type == CellType.HEX){
            		horizontalShift = getSizeOfCell()* 6/10;
            		verticalShift = 1.925* getSizeOfCell();
	            	if(j%2 == 0){
	            		horizontalOffset= getInitialX() + getSizeOfCell();
	            		
	            	}
            	}
                GameOfLifeCell gridCell = new GameOfLifeCell(getSizeOfCell(), getRootElement(), 
                                                             verticalShift * (i) + horizontalOffset, 
                                                             horizontalShift * (j) + getInitialY(),getRowLength(),type);
                gridCell.fillCellWithColors();
                gridCell.addToScene();
                setCell(i,j,gridCell);		
                setUpListener(gridCell);
            }
        }	
    }

    private void setUpListener(GameOfLifeCell gridCell) {
        gridCell.returnBlock().setOnMousePressed(event -> {
            gridCell.setAsManuallyModified();
            if(gridCell.getState() == States.ALIVE) {
                gridCell.killCell();
                gridCell.setColor(Color.BLACK);
            }
            else { 
                gridCell.reviveCell();
                gridCell.setColor(Color.WHITE);
            }
            sim.updateStateOnClick();
            sim.updateGraph();
        });
    }
}