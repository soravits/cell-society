package sugarscape;

import java.awt.Point;

import base.Location;
import sugarscape.SugarScapeCell.State;
import base.CellShape;
import base.Grid;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;

/**
 * This is the grid class for the SugarScape simulation.
 * @author Delia
 *
 */
public class SugarScapeGrid extends Grid {
	private SugarScapeSimulation sim;
	private CellType type;

	/**
	 * @param gridLength	int, number of cells along a side of the grid
	 * @param sizeOfCell	int, just read the var name
	 * @param rootElement	Pane, where nodes will be added
	 * @param initialX		int, x coordinate where the grid begins
	 * @param initialY		int, y ''
	 * @param edgeType		gridEdgeType, describing whether edges are finite or toroidal
	 * @param sim			SugarScapeSimulation, the simulation containing this grid
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

	/**
	 * Set each cell so that it'll change states when clicked
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
	 * Relocates agent to a patch on the grid, updates data for the agent
	 * and the cell it left behind
	 * @param p1	coordinates of agent that is moving
	 * @param p2	coordinates of patch that is destination
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
	 * update state of a cell at a certain location
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
