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
			int initialX, int initialY, WaTorWorldSimulation sim) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY);
		this.sim = sim;
	}

	/* (non-Javadoc)
	 * @see base.Grid#initializeGrid()
	 */
	@Override
	public void initializeGrid() {
		for(int i = 0; i < getGrid().length; i++) {
			for(int j = 0; j < getGrid()[0].length; j++) {
				WaTorWorldCell gridCell = new WaTorWorldCell(getSizeOfCell(), 
						getRootElement(), getSizeOfCell() * (i) + getInitialX(),
						getSizeOfCell()* (j) + getInitialY(), State.EMPTY);
				gridCell.addToScene();
				getGrid()[i][j] = gridCell; 
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
	 * @param x
	 * @param y
	 * @return
	 */
	public WaTorWorldCell getCell(int x, int y) {
		return (WaTorWorldCell) getGrid()[x][y];
	}
}