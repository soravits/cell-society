package slimemolds;

import base.CellShape;
import base.Grid;
import base.Grid.gridEdgeType;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slimemolds.SlimeMoldsCell.MoldStatus;
import spreadingoffire.SpreadingOfFireCell;
import spreadingoffire.SpreadingOfFireSimulation;
import spreadingoffire.SpreadingOfFireCell.States;

public class SlimeMoldsGrid extends Grid{

	private SlimeMoldsSimulation sim;
	
	public SlimeMoldsGrid(int gridLength, int sizeOfCell, Pane rootElement, int initialX, int initialY, SlimeMoldsSimulation sim) {
		super(gridLength, sizeOfCell, rootElement, initialX, initialY,  Grid.gridEdgeType.finite);
		this.sim = sim;
	}


	public SlimeMoldsCell getCell(int row, int col) {
		return (SlimeMoldsCell) super.getCell(row,col);
	}
	
	/* (non-Javadoc)
	 * @see base.Grid#initializeGrid()
	 */
	@Override
	public void initializeGrid(CellType type) {
		for(int i = 0; i < getColumnLength(); i++) {
            for(int j = 0; j < getColumnLength(); j++) {
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
                                                             horizontalShift * (j) + getInitialY(),getRowLength(),type);
                gridCell.addToScene();
                setCell(i,j,gridCell);		
                setUpListener(gridCell);
            }
        }	      
	}
	private void setUpListener(SlimeMoldsCell gridCell) {
		gridCell.returnBlock().setOnMousePressed(event -> {
			gridCell.setAsManuallyModified();
			if(gridCell.getState() == MoldStatus.MOLD) {
				gridCell.killMold();
				gridCell.setColor(Color.WHITE);
			}
			else {
				gridCell.moldify();
				gridCell.setColor(Color.RED);
			}
			sim.checkUpdatedStatesAfterManualMod();
			sim.updateGraph();
		});
	}
	
	/**
	 * @param x
	 * @param y
	 * @param cellState
	 */
	public void updateCell(int x, int y, MoldStatus state, double threshold) {
		SlimeMoldsCell cell = getCell(x,y);
		if(state == MoldStatus.MOLD) {
			cell.setColor(Color.RED);
		}
		else if(cell.getChemicalAmount() >= threshold) {
		        getCell(x,y).setColor(Color.DARKGREEN);
		}
		else if(cell.getChemicalAmount() >= (threshold/2)) {
	        getCell(x,y).setColor(Color.GREEN);
		}
		else if((cell.getChemicalAmount() >= (threshold/4)) && (cell.getChemicalAmount() > 0) ) {
	        getCell(x,y).setColor(Color.LIGHTGREEN);
		}
		else {
		    getCell(x,y).setColor(Color.WHITE);
		}
	}
}
