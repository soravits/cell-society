package base;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell {
    private Pane rootElement;
    private Rectangle block;
    public static final double STROKE_WIDTH = 2;

    public Cell(int sizeOfCell,Pane rootElement,int xCoord,int yCoord){
        this.rootElement = rootElement;
        this.block = new Rectangle(xCoord,yCoord,sizeOfCell,sizeOfCell);
    }

    public void fillCellWithColors() {
        block.setFill(Color.WHITE);
        block.setStroke(Color.BLACK);
        block.setStrokeWidth(STROKE_WIDTH);
    }

    public void setBorder(Color color){
        block.setStroke(color);
        block.setStrokeWidth(STROKE_WIDTH);
    }

    public void setColor(Color color){
        block.setFill(color);
    }

    public void setColor(Paint color){
        block.setFill(color);
    }

    public Paint getColor(){
        return block.getFill();
    }

    public void addToScene(){
        rootElement.getChildren().add(block);
    }

}