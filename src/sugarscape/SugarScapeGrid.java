package sugarscape;

import java.awt.Point;

import base.Location;
import sugarscape.SugarScapeCell.State;
import base.CellShape;
import base.Grid;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;

/**
 * @author Delia
 *
 */
public class SugarScapeGrid extends Grid {
	private SugarScapeSimulation sim;
	private CellType type;

	/**
	 * @param gridLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 * @param edgeType
	 * @param sim
	 */
	public SugarScapeGrid(int gridLength, int sizeOfCell, Pane rootElement,
			int initialX, int initialY, gridEdgeType edgeType, SugarScapeSimulation sim) {
		super(gridLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
	}

	/**
	 * @param location
	 * @return cell located at those coordinates
	 */
	public SugarScapeCell getCell(Location location) {
		return (SugarScapeCell) super.getCell(location);
	}

	@Override
	public void initializeGrid(CellType type) {
		this.type = type;
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				int horizontalOffset = getInitialX();
				double horizontalShift = getSizeOfCell();
				double verticalShift = getSizeOfCell();
				if(type == CellType.HEX){  
					horizontalShift = getSizeOfCell() * CellShape.horizontalOffsetHexagon;
					verticalShift = CellShape.verticalOffsetHexagon * getSizeOfCell();
					if(i % 2 == 0){
						horizontalOffset = getInitialX() + getSizeOfCell();

					}
				}
				SugarScapeCell gridCell = new SugarScapeCell(getSizeOfCell(), getRootElement(), 
						verticalShift * (j) + horizontalOffset,
						horizontalShift * (i) + getInitialY(), getGridLength(),
						sim.getMaxPatchSugar(), sim.getAgentMaxCarbs(),
						sim.getAgentMinCarbs(), sim.getMetabRate(), type);
				gridCell.addToScene();
				setUpListener(gridCell);   
				setCell(new Location(i, j), gridCell);
			}
		}      
	}

	//fix agent state change later
	/**
	 * @param gridCell
	 */
	private void setUpListener(SugarScapeCell gridCell) {
		gridCell.getBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModifiedByUser();
			if(gridCell.getState() == State.PATCH) {
				gridCell.setState(State.AGENT);
			}
			else {
				gridCell.setState(State.PATCH);
			}
			gridCell.updateColor();
			sim.updateGraph();
		});
	}

	/**
	 * Switches two cells on the grid.
	 * Stores the destination color as local Paint object
	 * Sets destination color with color of moving cell
	 * Updates original position of mover with stored destination color
	 * @param p1	coordinates of point that is moving
	 * @param p2	coordinates of point that is destination
	 */
	public void moveAgent(Point p1, Point p2) {
		Location p1location = new Location(p1.x, p1.y);
		Location p2location = new Location(p2.x, p2.y);
		int origCarbs = getCell(p1location).getAgentCarbs();
		int distanceMoved = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
		if(getCell(p2location).getState() != State.AGENT) {
			//set destination with all attributes of agent, except for metabolized sugar
			getCell(p2location).setMovedAgent(origCarbs, getCell(p1location).getSugarAmount());
			getCell(p2location).burnAgentCalories(distanceMoved);
			//set agent original position as empty patch
			getCell(p1location).setAgentMovedPatch();
		}
	}

	/**
	 * @param location
	 * @param cellState
	 */
	public void updateCell(Location location, int cellState) {
		if(cellState == 0) {
			getCell(location).setState(State.PATCH);
		}
		else {
			getCell(location).setState(State.AGENT);
		}
	}
}
