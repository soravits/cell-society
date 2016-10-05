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
	 * @param rowLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 */
	public SpreadingOfFireGrid(int rowLength, int sizeOfCell, Pane rootElement,int initialX, 
			int initialY, gridEdgeType edgeType, SpreadingOfFireSimulation sim) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
	}

	/* (non-Javadoc)
	 * @see base.Grid#getCell(int, int)
	 */
	public SpreadingOfFireCell getCell(Location location) {
		return (SpreadingOfFireCell) super.getCell(location);
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
	
	/**
	 * @param gridCell
	 */
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
	 * @param location
	 * @param state
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