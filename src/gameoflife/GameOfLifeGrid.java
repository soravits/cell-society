package gameoflife;
import base.CellShape;
import base.Grid;
import base.Location;
import base.Simulation.CellType;
import gameoflife.GameOfLifeCell.States;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Brian
 *
 */
public class GameOfLifeGrid extends Grid{
	private GameOfLifeSimulation sim; 
	//make instance of itself instead of using a getter all the time?
	/**
	 * @param rowLength
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 */
	public GameOfLifeGrid(int rowLength, int sizeOfCell, Pane rootElement, 
			int initialX, int initialY, gridEdgeType edgeType, GameOfLifeSimulation sim) {
		super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		this.sim = sim;
	}

	/* (non-Javadoc)
	 * @see base.Grid#getCell(int, int)
	 */
	public GameOfLifeCell getCell(Location location) {
		return (GameOfLifeCell) super.getCell(location);
	}
	/**
	 * @param location
	 */
	public void updateCell(Location location) {
		GameOfLifeCell myCell = getCell(location);
		if(myCell.getState() == States.ALIVE) {
			getCell(location).setColor(Color.WHITE);
		}
		else {
			getCell(location).setColor(Color.BLACK);
		}
		getCell(location).setBorder(Color.WHITE);
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
					if(i%2 == 0){
						horizontalOffset= getInitialX() + getSizeOfCell();

					}
				}
				GameOfLifeCell gridCell = new GameOfLifeCell(getSizeOfCell(), getRootElement(), 
						verticalShift * (j) + horizontalOffset,
						horizontalShift * (i) + getInitialY(), getGridLength(),type);
				gridCell.addToScene();
                Location cellLocation = new Location(i, j);
				setCell(cellLocation, gridCell);
				setUpListener(gridCell);
			}
		}	
	}
	
	/**
	 * @param gridCell
	 */
	private void setUpListener(GameOfLifeCell gridCell) {
		gridCell.getBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModifiedByUser();
			if(gridCell.getState() == States.ALIVE) {
				gridCell.killCell();
				gridCell.setColor(Color.BLACK);
			}
			else { 
				gridCell.reviveCell();
				gridCell.setColor(Color.WHITE);
			}
			sim.updateStateOnClick();
			sim.updateGraph();
		});
	}
}