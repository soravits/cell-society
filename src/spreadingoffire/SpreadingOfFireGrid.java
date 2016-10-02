package spreadingoffire;

import base.Cell;
import base.Grid;
import base.Simulation.CellType;
import gameoflife.GameOfLifeCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import spreadingoffire.SpreadingOfFireCell.States;
import waterworld.WaTorWorldCell;
import waterworld.WaTorWorldCell.State;

/**
 * @author Soravit
 *
 */
public class SpreadingOfFireGrid extends Grid {
	private SpreadingOfFireSimulation sim;
	/**
	 * @param rowLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 */
	public SpreadingOfFireGrid(int rowLength, int sizeOfCell, Pane rootElement, int initialX, 
			int initialY, gridEdgeType edgeType, SpreadingOfFireSimulation sim) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
	}

	public SpreadingOfFireCell gridCell(int row, int col) {
		return (SpreadingOfFireCell) super.getCell(row,col);
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
                SpreadingOfFireCell gridCell = new SpreadingOfFireCell(getSizeOfCell(), getRootElement(), 
                                                             verticalShift * (i) + horizontalOffset, 
                                                             horizontalShift * (j) + getInitialY(),getRowLength(),type);
                gridCell.addToScene();
                setCell(i,j,gridCell);		
                setUpListener(gridCell);
            }
        }	      
	}

	private void setUpListener(SpreadingOfFireCell gridCell) {
		gridCell.returnBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModified();
			if(gridCell.getState() == States.ALIVE) {
				gridCell.burn();
				gridCell.setColor(Color.RED);
			}
			else if(gridCell.getState() == States.DEAD) {
				gridCell.spawn();
				gridCell.setColor(Color.FORESTGREEN);
			}
			else {
				gridCell.burnout();
				gridCell.setColor(Color.YELLOW);
			}
			sim.checkUpdatedStatesAfterManualMod();
			sim.updateGraph();
		});
	}
	
	/**
	 * @param x
	 * @param y
	 * @param cellState
	 */
	public void updateCell(int x, int y, int cellState) {
		if(cellState == 0) {
			gridCell(x,y).setColor(Color.YELLOW);
		}
		else if(cellState == 1 || cellState == 3) {
		        gridCell(x,y).setColor(Color.FORESTGREEN);
		}
		else {
		        gridCell(x,y).setColor(Color.BROWN);
		}
	}

}