package slimemolds;

import base.Cell;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;

//This entire file, along with SlimeMoldsGrid is part of my masterpiece.
//BRIAN ZHOU
	/**
	* This class's purpose is to extend out the superclass Cell and establish different enumerated states for the different conditions that each cell could be in. I'm proud of the enumerated
	* states within the class as it both simplifies and clarifies the purpose of the cell, while combining all required information of the cell (required by controller) into one place. As a result,
	* developing the back-end was easy because the enumerated types and status functions made it easy to keep track of each cell's individual state and spore amount, allowing the cells in the grid to be autonomous
	* while letting the controller easily manage their states and check their statuses at each step (and thereby execute proper behavior).
    */
    

/**
 * Represents a cell in the Slime Molds simulation
 * @author Brian
 */

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