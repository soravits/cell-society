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
     * @param threshold
     * @return
     */
    public boolean isAttracting(double threshold) {
    	return (chemicalAmount > threshold); 	
    }
    
    /**
     * 
     */
    public void diffuse(double diffuseAmount) {
        this.chemicalAmount += diffuseAmount;
    }
    
    /**
     * @param dissipateAmount
     */
    public void dissipate(double dissipateAmount) {
    	this.chemicalAmount -= dissipateAmount;
    }
    
    /**
     * @return
     */
    public double getChemicalAmount() {
    	return this.chemicalAmount;
    }

    /**
     * @param stepChemicalAmount
     */
    public void pollute(double stepChemicalAmount) {
        this.chemicalAmount += stepChemicalAmount;
    }
    
    /**
     * 
     */
    public void moldify() {
        this.moldStatus = MoldStatus.MOLD;
    }
    
    /**
     * 
     */
    public void killMold() {
    	this.moldStatus = MoldStatus.EMPTY;
    	this.chemicalAmount = 0;
    }
    
    /**
     * @return
     */
    public MoldStatus getState() {
        return this.moldStatus;
    }

}
