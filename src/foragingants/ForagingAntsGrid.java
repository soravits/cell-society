package foragingants;

import base.CellShape;
import base.Grid;
import base.Location;
import base.Simulation;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by Soravit on 10/2/2016.
 */
public class ForagingAntsGrid extends Grid {

	private ForagingAntsSimulation sim;
	private Location nest;
	private Location foodSource;

	/**
	 * @param rowLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 * @param edgeType
	 * @param sim
	 * @param nest
	 * @param foodSource
	 */
	public ForagingAntsGrid(int rowLength, int sizeOfCell, Pane rootElement, int initialX, int initialY, 
			gridEdgeType edgeType, ForagingAntsSimulation sim,
			Location nest, Location foodSource) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
		this.nest = nest;
		this.foodSource = foodSource;
	}

	/* (non-Javadoc)
	 * @see base.Grid#getCell(int, int)
	 */
	public ForagingAntsCell getCell(Location location) {
		return (ForagingAntsCell) super.getCell(location);
	}

	/* (non-Javadoc)
	 * @see base.Grid#initializeGrid(base.Simulation.CellType)
	 */
	@Override
	public void initializeGrid(Simulation.CellType type) {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				int horizontalOffset = getInitialX();
				double horizontalShift = getSizeOfCell();
				double verticalShift = getSizeOfCell();

				if(type == Simulation.CellType.HEX) {
					horizontalShift = getSizeOfCell() * CellShape.horizontalOffsetHexagon;
					verticalShift = CellShape.verticalOffsetHexagon * getSizeOfCell();
					if(i % 2 == 0){
						horizontalOffset= getInitialX() + getSizeOfCell();

					}
				}
				ForagingAntsCell gridCell = new ForagingAntsCell(getSizeOfCell(), getRootElement(),
						verticalShift * (j) + horizontalOffset,
						horizontalShift * (i) + getInitialY(), getGridLength(), type);
				Location cellLocation = new Location(i,j);
				gridCell.addToScene();
				setCell(cellLocation, gridCell);
			}
		}
		getCell(nest).setColor(Color.BROWN);
		getCell(foodSource).setColor(Color.YELLOW);
	}

	/**
	 * @param location
	 */
	public void updateCell(Location location) {
		ForagingAntsCell gridCell = getCell(location);
		if (gridCell != getCell(nest)
				&& gridCell != getCell(foodSource)) {
			if (gridCell.getAntCount() > 0) {
				gridCell.setColor(Color.RED);
			} 
			else if(gridCell.getFoodPheromoneCount() > 0 || gridCell.getHomePheromoneCount() > 0) {
				gridCell.setColor(Color.GREY);
			}
			else {
				gridCell.setColor(Color.FORESTGREEN);
			}
		}
	}
}
