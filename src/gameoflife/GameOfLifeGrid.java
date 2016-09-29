package gameoflife;

import base.Cell;
import base.Grid;
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
	private GameOfLifeSimulation sim; //make instance of itself instead of using a getter all the time
    /**
     * @param rowLength
     * @param sizeOfCell
     * @param rootElement
     * @param initialX
     * @param initialY
     */
    public GameOfLifeGrid(int rowLength, int sizeOfCell, Pane rootElement, 
                          int initialX, int initialY, GameOfLifeSimulation sim) {
        super(rowLength, sizeOfCell, rootElement, initialX, initialY);
        this.sim = sim;
    }
    
    public GameOfLifeCell getCell(int row, int col) {
    	return (GameOfLifeCell) getGrid()[row][col];
    }

    /**
     * @param row
     * @param col
     * @param cellstate
     */
    public void updateCell(int row, int col) {
    	GameOfLifeCell myCell = getCell(row, col);
        if(myCell.getState() == States.ALIVE) {
            getGrid()[row][col].setColor(Color.WHITE);
        }
        else {
            getGrid()[row][col].setColor(Color.BLACK);
        }
        getGrid()[row][col].setBorder(Color.WHITE);
    }

    /* (non-Javadoc)
     * @see base.Grid#initializeGrid()
     */
    @Override
    public void initializeGrid() {
        for(int i = 0; i < getGrid().length; i++) {
            for(int j = 0; j < getGrid()[0].length; j++) {
                GameOfLifeCell gridCell = new GameOfLifeCell(getSizeOfCell(), getRootElement(), 
                		getSizeOfCell() * (i) + getInitialX(), getSizeOfCell()* (j) + getInitialY());
                gridCell.fillCellWithColors();
                gridCell.addToScene();
                getGrid()[i][j] = gridCell;		
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