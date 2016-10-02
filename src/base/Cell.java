package base;

import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * @author Delia, Soravit, Brian
 *
 */
public class Cell {
    private Pane rootElement;
    private Polygon block;
    private int gridLength;
    public static final double STROKE_WIDTH = 1;
    private boolean manuallyModified = false;

    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     */
    public Cell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord, int gridLength, CellType type) {
        this.rootElement = rootElement;
        //this.block = new Rectangle(xCoord, yCoord, sizeOfCell, sizeOfCell);
        CellShape cellShape = new CellShape(gridLength);
        if(type == CellType.HEX){
        	cellShape.setHexagonalCell();
        }
        else if(type == CellType.TRIANGLE){
        	cellShape.setTriangularCell();
        }
        else{
        	cellShape.setSquareCell();
        }
        
        cellShape.setCoords(xCoord,yCoord);
        this.block = cellShape.returnShape();
        
    }

    public Polygon returnBlock() {
    	return this.block;
    }
    
    /**
     * 
     */
    public void fillCellWithColors() {
        block.setFill(Color.WHITE);
        block.setStroke(Color.BLACK);
        block.setStrokeWidth(STROKE_WIDTH);
    }

    /**
     * @param color
     */
    public void setBorder(Color color) {
        block.setStroke(color);
        block.setStrokeWidth(STROKE_WIDTH);
    }

    /**
     * @param color
     */
    public void setColor(Color color) {
        block.setFill(color);
    }

    /**
     * @param color
     */
    public void setColor(Paint color) {
        block.setFill(color);
    }

    /**
     * @return
     */
    public Paint getColor() {
        return block.getFill();
    }

    /**
     * 
     */
    public void addToScene() {
        rootElement.getChildren().add(block);
    }
    
    /**
     * 
     */
    public void setAsManuallyModified() {
    	this.manuallyModified = true;
    }
    
    /**
     * @return
     */
    public boolean isManuallyModified() {
    	return manuallyModified;
    }
    
    /**
     * 
     */
    public void noLongerManuallyModified() {
    	this.manuallyModified = false;
    }

}