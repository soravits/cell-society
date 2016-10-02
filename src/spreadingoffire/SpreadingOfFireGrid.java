package spreadingoffire;

import base.Cell;
import base.Grid;
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
			int initialY, SpreadingOfFireSimulation sim) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY);
		this.sim = sim;
	}

	public SpreadingOfFireCell gridCell(int row, int col) {
		return (SpreadingOfFireCell) super.gridCell(row,col);
	}

	/* (non-Javadoc)
	 * @see base.Grid#initializeGrid()
	 */
	@Override
	public void initializeGrid() {
		for(int i = 0; i < getColumnLength(); i++) {
			for(int j = 0; j < getRowLength(); j++) {
				SpreadingOfFireCell gridCell = new SpreadingOfFireCell(
						getSizeOfCell(), getRootElement(), 
						getSizeOfCell() * (i) + getInitialX(),
						getSizeOfCell()* (j) + getInitialY());
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