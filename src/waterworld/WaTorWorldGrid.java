package waterworld;

import base.Grid;
import base.Simulation;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import waterworld.WaTorWorldCell.State;

/**
 * @author Soravit
 *
 */
public class WaTorWorldGrid extends Grid {
	private WaTorWorldSimulation sim;
	/**
	 * @param rowLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 */
	public WaTorWorldGrid(int rowLength, int sizeOfCell, Pane rootElement,
			int initialX, int initialY, gridEdgeType edgeType, WaTorWorldSimulation sim) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
	}

	/* (non-Javadoc)
	 * @see base.Grid#initializeGrid()
	 */
	@Override
	public void initializeGrid() {
		for(int i = 0; i < getColumnLength(); i++) {
			for(int j = 0; j < getRowLength(); j++) {
				WaTorWorldCell gridCell = new WaTorWorldCell(getSizeOfCell(), 
						getRootElement(), getSizeOfCell() * (i) + getInitialX(),
						getSizeOfCell()* (j) + getInitialY(), State.EMPTY);
				gridCell.addToScene();
				setCell(i,j,gridCell); 
				setUpListener(gridCell);
			}
		} 
	}

	private void setUpListener(WaTorWorldCell gridCell) {
		gridCell.returnBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModified();
			if(gridCell.getState() == State.EMPTY) {
				gridCell.setState(State.FISH);
			}
			else if(gridCell.getState() == State.FISH) {
				gridCell.setState(State.SHARK);
			}
			else{
				gridCell.setState(State.EMPTY);
			}
			gridCell.updateColor();
			//sim.manuallyModifyStateOfGrid();
			sim.updateGraph();
		});
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public WaTorWorldCell getCell(int row, int col) {
		return (WaTorWorldCell) super.getCell(row,col);
	}

}