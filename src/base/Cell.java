package base;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author Delia, Soravit, Brian
 *
 */
public class Cell {
    private Pane rootElement;
    public Rectangle block;
    public static final double STROKE_WIDTH = 1;

    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     */
    public Cell(int sizeOfCell,Pane rootElement,int xCoord,int yCoord){
        this.rootElement = rootElement;
        this.block = new Rectangle(xCoord,yCoord,sizeOfCell,sizeOfCell);
    }
    
    //TODO: Make this abstract when all cells have their own thing
    public Rectangle returnBlock(){
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
    public void setBorder(Color color){
        block.setStroke(color);
        block.setStrokeWidth(STROKE_WIDTH);
    }

    /**
     * @param color
     */
    public void setColor(Color color){
        block.setFill(color);
    }

    /**
     * @param color
     */
    public void setColor(Paint color){
        block.setFill(color);
    }

    /**
     * @return
     */
    public Paint getColor(){
        return block.getFill();
    }

    /**
     * 
     */
    public void addToScene(){
        rootElement.getChildren().add(block);
    }

}