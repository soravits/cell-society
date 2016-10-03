package sugarscape;

import base.Grid;
import base.Simulation;
import base.Grid.gridEdgeType;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;

public class SugarScapeGrid extends Grid {

	public SugarScapeGrid(int gridLength, int sizeOfCell, Pane rootElement,
			int initialX, int initialY, gridEdgeType edgeType) {
		super(gridLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeGrid(CellType type) {
		// TODO Auto-generated method stub
		
	}

}
