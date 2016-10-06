package slimemolds;

import base.Cell;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;

public class SlimeMoldsCell extends Cell {
	public enum MoldStatus{MOLD, EMPTY};
	
    private MoldStatus moldStatus;
    private double chemicalAmount = 0;
    
    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     */
    public SlimeMoldsCell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord, 
    		int gridLength, CellType type) {
        super(sizeOfCell, rootElement, xCoord, yCoord,gridLength,type);
        this.moldStatus = MoldStatus.EMPTY;
    }
    
    /**
     * @param threshold Value at which other cells will be attracted to this one
     * @return Whether or not the cell is above threshold and attracting other mold cells
     */
    public boolean isAttracting(double threshold) {
    	return (chemicalAmount > threshold); 	
    }
    
    /**
     * Diffuse out spores from the drop-off point by the mold
     */
    public void diffuse(double diffuseAmount) {
        this.chemicalAmount += diffuseAmount;
    }
    
    /**
     * @param dissipateAmount Amount of spores that each cell loses per step
     * Every step cycle remove chemical amount equal to the dissipateAmount from the cell
     */
    public void dissipate(double dissipateAmount) {
    	this.chemicalAmount -= dissipateAmount;
    }
    
    /**
     * @return Current spore/chemical amount
     */
    public double getChemicalAmount() {
    	return this.chemicalAmount;
    }

    /**
     * @param stepChemicalAmount
     * Spore just stepped on this cell, increment amount of spores on this cell and diffuse outwards
     */
    public void pollute(double stepChemicalAmount) {
        this.chemicalAmount += stepChemicalAmount;
    }
    
    /**
     * Turn a cell into a mold cell
     */
    public void moldify() {
        this.moldStatus = MoldStatus.MOLD;
    }
    
    /**
     * Turn a cell into an empty cell
     */
    public void killMold() {
    	this.moldStatus = MoldStatus.EMPTY;
    	cleanCell();
    }
    
    /**
     * @return State of this cell
     */
    public MoldStatus getState() {
        return this.moldStatus;
    }
    
    private void cleanCell(){
    	this.chemicalAmount = 0;
    }

}
