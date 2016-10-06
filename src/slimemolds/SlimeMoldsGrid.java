package slimemolds;

import base.CellShape;
import base.Grid;
import base.Location;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slimemolds.SlimeMoldsCell.MoldStatus;

//This entire file, along with SlimeMoldsCell is part of my masterpiece.
//BRIAN ZHOU
	/**
	* This class's purpose is to extend out the Grid superclass and establish a dynamic UI view for the entire Slime Molds simulation. I'm proud of this class for two reasons; I really enjoy all of the extension and
	* inheritance work we did with this class in overriding abstract methods and replacing generic behavior with specific behavior to this simulation profile. In addition, what I love most 
	* about this class is the independence and autonomous behavior of the class for the manual click and event handler. The event handler handles the click purely on the front end, and signals to the back end that a click
	* has happened, thereby updating the UI asynchronously in preparation for the backend's response. As a result, we see the changes to the counter of the simulation on manual manipulation REAL TIME, so much so that the simulation
	* will update itself within the same step cycle. So, the reaction (turning empty cell into mold) will immediately propagate through the simulation.
  */
  
/**
 * The graphical representation of the Slime Molds simulation
 * @author Brian
 */

public class SlimeMoldsGrid extends Grid {
	private SlimeMoldsSimulation sim;

	/**
	 * @param gridLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 * @param sim
	 * Constructor for grid
	 */
	public SlimeMoldsGrid(int gridLength, int sizeOfCell, Pane rootElement, int initialX, 
			int initialY, SlimeMoldsSimulation sim) {
		super(gridLength, sizeOfCell, rootElement, initialX, initialY, Grid.gridEdgeType.finite);
		this.sim = sim;
	}


	/* (non-Javadoc)
	 * @see base.Grid#getCell(int, int)
	 * Returns cell at a specific location in the grid
	 */
	public SlimeMoldsCell getCell(Location location) {
		return (SlimeMoldsCell) super.getCell(location);
	}

	/* (non-Javadoc)
	 * @see base.Grid#initializeGrid()
	 * Initializes grid depending on initial state set by configuration file or input
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
	 * @param location
	 * @param state
	 * @param threshold
	 * Updates UI for cell depending on simulation behavior
	 */
	public void updateCell(Location location, MoldStatus state, double threshold) {
		SlimeMoldsCell cell = getCell(location);
		if(state == MoldStatus.MOLD) {
			cell.setColor(Color.RED);
		}
		else{
			changeCellColorDependingOnSpores(location,cell.getChemicalAmount(),threshold);
		}
	}
	
	/**
	 * @param location
	 * @param chemAmount
	 * @param threshold
	 */
	private void changeCellColorDependingOnSpores(Location location, double chemAmount, double threshold){
		Color colorOfCell = Color.WHITE;
		if(chemAmount > 0) {
			colorOfCell = Color.LIGHTGREEN;
			if(chemAmount >= (threshold / 2)) {
				colorOfCell = Color.GREEN;
				if(chemAmount >= threshold) {
					colorOfCell = Color.DARKGREEN;
				}
			}
		}	
		getCell(location).setColor(colorOfCell);
	}
	

	/**
	 * @param gridCell
	 */
	private void setUpListener(SlimeMoldsCell gridCell) {
		gridCell.getBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModifiedByUser();
			manuallyChangeStateOfCell(gridCell);
			
			sim.checkUpdatedStatesAfterManualMod();
			sim.updateGraph();
		});
	}
	
	/**
	 * @param gridCell
	 */
	private void manuallyChangeStateOfCell(SlimeMoldsCell gridCell){
		if(gridCell.getState() == MoldStatus.MOLD) {
			gridCell.killMold();
			gridCell.setColor(Color.WHITE);
		}
		else {
			gridCell.moldify();
			gridCell.setColor(Color.RED);
		}
	}
}