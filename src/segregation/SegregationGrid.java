package segregation;
import java.awt.Point;

import base.Location;
import segregation.SegregationCell.State;
import base.CellShape;
import base.Grid;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
/**
 * This is the Grid class for segregation. 
 * It's responsible for some specific behaviors such as switching cells,
 * setting and updating the statistics, etc.
 * @author Delia
 *
 */
public class SegregationGrid extends Grid {
	private Text stats;
	private SegregationSimulation sim;
	private CellType type;

	/**
	 * @param rowLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 */
	public SegregationGrid(int rowLength, int sizeOfCell, Pane rootElement,
			int initialX, int initialY, gridEdgeType edgeType, 
			SegregationSimulation sim) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
	}

	/**
	 * @param location
	 * @return cell located at those coordinates
	 */
	public SegregationCell getCell(Location location) {
		return (SegregationCell) super.getCell(location);
	}

	/**
	 * @see base.Grid#initializeGrid()
	 */
	@Override
	public void initializeGrid(CellType type) {
		this.type = type;
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
				SegregationCell gridCell = new SegregationCell(getSizeOfCell(), getRootElement(), 
						verticalShift * (i) + horizontalOffset, 
						horizontalShift * (j) + getInitialY(), getGridLength(), type);
				gridCell.addToScene();
				setCell(new Location(i, j), gridCell);
				setUpListener(gridCell);                    
			}
		}      
		setStats();
	}
	
	/**
	 * @param gridCell
	 */
	private void setUpListener(SegregationCell gridCell) {
		gridCell.getBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModifiedByUser();
			if(gridCell.getState() == State.EMPTY) {
				gridCell.setState(State.COLORA);
			}
			else if(gridCell.getState() == State.COLORA) {
				gridCell.setState(State.COLORB);
			}
			else {
				gridCell.setState(State.EMPTY);
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
	public void switchCells(Point p1, Point p2) {
//		State destination = getCell(dest).getState();
//		State origin = getCell(location).getState();
		Location source = new Location(p1.x, p1.y);
		Location dest = new Location(p2.x, p2.y);
		State destination = getCell(dest).getState();
		State origin = getCell(source).getState();

		updateCell(source, destination);
		//		System.out.println(destination);
		updateCell(dest, origin);
		//		System.out.println(origin);
	}
	
	/**
	 * Sets the text that will display grid statistics
	 */
	public void setStats() {
		stats = new Text(75, 155, "Round");
		stats.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		stats.setFill(Color.WHITE);
		getRootElement().getChildren().add(stats);
	}
	
	/**
	 * Updates the stats for the grid
	 * @param stepNumber			Which step in the simulation it is
	 * @param numberUnsatisfied		Number of cells that aren't satisfied
	 */
	public void updateStats(int stepNumber, int numberUnsatisfied) {
		String currentStat = "Round " + stepNumber;
		stats.setText(currentStat);
	}
	
	/**
	 * @param location
	 * @param cellState
	 */
	public void updateCell(Location location, State cellState) {
		if(cellState.equals(State.EMPTY)) {
			getCell(location).setColor(Color.WHITE);
			getCell(location).setState(State.EMPTY);
		}
		else if(cellState.equals(State.COLORA)) {
			getCell(location).setColor(Color.DARKBLUE);
			getCell(location).setState(State.COLORA);
		}
		else {
			getCell(location).setColor(Color.LIMEGREEN);
			getCell(location).setState(State.COLORB);
		}
	}
	
	/**
	 * Sets the color of cell at those coordinates based on its int state
	 * @param location
	 * @param cellState
	 */
	public void updateCell(Location location, int cellState) {
		if(cellState == 0) 
			updateCell(location, State.EMPTY);
		else if(cellState == 1) 
			updateCell(location, State.COLORA);
		else 
			updateCell(location, State.COLORB);
	}
}