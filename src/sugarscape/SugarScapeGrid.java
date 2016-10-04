package sugarscape;

import java.awt.Point;

import sugarscape.SugarScapeCell.State;
import base.Cell;
import base.CellShape;
import base.Grid;
import base.Simulation;
import base.Grid.gridEdgeType;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
	 * @param row
	 * @param col
	 * @return cell located at those coordinates
	 */
	public SugarScapeCell getCell(int row, int col) {
		return (SugarScapeCell) super.getCell(row, col);
	}

	@Override
	public void initializeGrid(CellType type) {
		this.type = type;
		for(int i = 0; i < getColumnLength(); i++) {
			for(int j = 0; j < getRowLength(); j++) {
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
						horizontalShift * (i) + getInitialY(), getRowLength(), 
						sim.getMaxPatchSugar(), sim.getAgentMaxCarbs(),
						sim.getAgentMinCarbs(), sim.getMetabRate(), type);
				gridCell.addToScene();
				setUpListener(gridCell);   
				setCell(i, j, gridCell);
			}
		}      
	}

	//fix agent state change later
	/**
	 * @param gridCell
	 */
	private void setUpListener(SugarScapeCell gridCell) {
		gridCell.returnBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModified();
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
		int origCarbs = getCell(p1.x, p1.y).getAgentCarbs();
		int distanceMoved = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
		if(getCell(p2.x, p2.y).getState() != State.AGENT) {
			//set destination with all attributes of agent, except for metabolized sugar
			getCell(p2.x, p2.y).setMovedAgent(origCarbs, getCell(p1.x, p1.y).getSugarAmount());
			getCell(p2.x, p2.y).burnAgentCalories(distanceMoved);
			//set agent original position as empty patch
			getCell(p1.x, p1.y).setAgentMovedPatch();
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param cellState
	 */
	public void updateCell(int x, int y, int cellState) {
		if(cellState == 0) {
			getCell(x, y).setState(State.PATCH);
		}
		else {
			getCell(x, y).setState(State.AGENT);
		}
	}
}
