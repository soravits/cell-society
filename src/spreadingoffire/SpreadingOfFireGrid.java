package spreadingoffire;
import base.CellShape;
import base.Grid;
import base.Location;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import spreadingoffire.SpreadingOfFireCell.States;
/**
 * @author Soravit
 *
 */
public class SpreadingOfFireGrid extends Grid {
	private SpreadingOfFireSimulation sim;

    /**
     * @param gridLength The length of a side of the grid
     * @param sizeOfCell The size of each cell
     * @param rootElement The JavaFX pane
     * @param initialX The initial x position of the grid
     * @param initialY The initial y position of the grid
     * @param edgeType The grid edge type of the grid
     * @param sim The simulation that uses the grid
     */
	public SpreadingOfFireGrid(int gridLength, int sizeOfCell, Pane rootElement,int initialX,
			int initialY, gridEdgeType edgeType, SpreadingOfFireSimulation sim) {
		super(gridLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
	}

    /**
     *
     * @param location The location of the desired cell
     * @return A SpreadingOfFire cell at the spcified location
     */
	public SpreadingOfFireCell getCell(Location location) {
		return (SpreadingOfFireCell) super.getCell(location);
	}

    /**
     * Sets up the initial grid
     * @param type The shape of the cells in the grid
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
					if(i % 2 == 0){
						horizontalOffset= getInitialX() + getSizeOfCell();

					}
				}
				SpreadingOfFireCell gridCell = new SpreadingOfFireCell(getSizeOfCell(), getRootElement(),
						verticalShift * (j) + horizontalOffset,
						horizontalShift * (i) + getInitialY(), getGridLength(),type);
				gridCell.addToScene();
				setCell(new Location(i, j), gridCell);
				setUpListener(gridCell);
			}
		}	      
	}

	private void setUpListener(SpreadingOfFireCell gridCell) {
		gridCell.getBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModifiedByUser();
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
	 * @param location The location of the cell
	 * @param state The state the cell should be updated to
	 */
	public void updateCell(Location location, States state) {
		if(state == States.DEAD) {
			getCell(location).setColor(Color.YELLOW);
		}
		else if(state == States.ALIVE || state == States.CAUGHTFIRE) {
			getCell(location).setColor(Color.FORESTGREEN);
		}
		else {
			getCell(location).setColor(Color.BROWN);
		}
	}
}