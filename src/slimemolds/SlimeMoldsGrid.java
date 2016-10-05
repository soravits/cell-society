package slimemolds;

import base.CellShape;
import base.Grid;
import base.Location;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slimemolds.SlimeMoldsCell.MoldStatus;

public class SlimeMoldsGrid extends Grid {

	private SlimeMoldsSimulation sim;

	/**
	 * @param gridLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 * @param sim
	 */
	public SlimeMoldsGrid(int gridLength, int sizeOfCell, Pane rootElement, int initialX, 
			int initialY, SlimeMoldsSimulation sim) {
		super(gridLength, sizeOfCell, rootElement, initialX, initialY, Grid.gridEdgeType.finite);
		this.sim = sim;
	}


	/* (non-Javadoc)
	 * @see base.Grid#getCell(int, int)
	 */
	public SlimeMoldsCell getCell(Location location) {
		return (SlimeMoldsCell) super.getCell(location);
	}

	/* (non-Javadoc)
	 * @see base.Grid#initializeGrid()
	 */
	@Override
	public void initializeGrid(CellType type) {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				int horizontalOffset = getInitialX();
				double horizontalShift = getSizeOfCell();
				double verticalShift = getSizeOfCell();
				if(type == CellType.HEX){
					horizontalShift = getSizeOfCell()* CellShape.horizontalOffsetHexagon;
					verticalShift = CellShape.verticalOffsetHexagon * getSizeOfCell();
					if(j%2 == 0){
						horizontalOffset= getInitialX() + getSizeOfCell();

					}
				}
				SlimeMoldsCell gridCell = new SlimeMoldsCell(getSizeOfCell(), getRootElement(), 
						verticalShift * (i) + horizontalOffset, 
						horizontalShift * (j) + getInitialY(), getGridLength(), type);
				gridCell.addToScene();
				setCell(new Location(i, j), gridCell);
				setUpListener(gridCell);
			}
		}	      
	}

	/**
	 * @param gridCell
	 */
	private void setUpListener(SlimeMoldsCell gridCell) {
		gridCell.getBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModifiedByUser();
			if(gridCell.getState() == MoldStatus.MOLD) {
				gridCell.killMold();
				gridCell.setColor(Color.WHITE);
			}
			else {
				gridCell.moldify();
				gridCell.setColor(Color.RED);
			}
			sim.checkUpdatedStatesAfterManualMod();
			sim.updateGraph();
		});
	}

	/**
	 * @param location
	 */
	public void updateCell(Location location, MoldStatus state, double threshold) {
		SlimeMoldsCell cell = getCell(location);
		if(state == MoldStatus.MOLD) {
			cell.setColor(Color.RED);
		}
		else if(cell.getChemicalAmount() >= threshold) {
			getCell(location).setColor(Color.DARKGREEN);
		}
		else if(cell.getChemicalAmount() >= (threshold / 2)) {
			getCell(location).setColor(Color.GREEN);
		}
		else if((cell.getChemicalAmount() >= (threshold / 4)) 
				&& (cell.getChemicalAmount() > 0) ) {
			getCell(location).setColor(Color.LIGHTGREEN);
		}
		else {
			getCell(location).setColor(Color.WHITE);
		}
	}
}
