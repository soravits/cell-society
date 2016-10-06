package waterworld;
import base.CellShape;
import base.Grid;
import base.Location;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import waterworld.WaTorWorldCell.State;
/**
 * The graphical representation of the Predator Prey Simulation
 * @author Soravit
 *
 */
public class WaTorWorldGrid extends Grid {
	private WaTorWorldSimulation sim;
	/**
	 * @param gridLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 */
	public WaTorWorldGrid(int gridLength, int sizeOfCell, Pane rootElement,
			int initialX, int initialY, gridEdgeType edgeType, WaTorWorldSimulation sim) {
		super(gridLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
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
					horizontalShift = getSizeOfCell() * CellShape.horizontalOffsetHexagon;
					verticalShift = CellShape.verticalOffsetHexagon * getSizeOfCell();
					if(i % 2 == 0){
						horizontalOffset= getInitialX() + getSizeOfCell();

					}
				}
				WaTorWorldCell gridCell = new WaTorWorldCell(getSizeOfCell(), getRootElement(), 
						verticalShift * (j) + horizontalOffset,
						horizontalShift * (i) + getInitialY(), State.EMPTY,getGridLength(), type);

				gridCell.addToScene();
				setCell(new Location(i, j),gridCell);
				setUpListener(gridCell);
			}
		} 
	}

	/**
	 * @param gridCell The cell to be set as a mouse listener
	 */
	private void setUpListener(WaTorWorldCell gridCell) {
		gridCell.getBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModifiedByUser();
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
			sim.manuallyModifyStateOfGrid();
			sim.updateGraph();
		});
	}
	
	/**
	 * @param location The desired location
	 * @return The WatorWorldCell at the desired location
	 */
	public WaTorWorldCell getCell(Location location) {
		return (WaTorWorldCell) super.getCell(location);
	}
}