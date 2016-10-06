package base;
import base.Simulation.CellType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

/**
 * @author Delia, Soravit, Brian
 *
 */
public class Cell {
    public static final double STROKE_WIDTH = 1;
    
    private boolean manuallyModifiedByUser = false;
    private Pane rootElement;
    private Polygon block;

    /**
     * @param sizeOfCell The size of the cell
     * @param rootElement The JavaFX pane
     * @param xCoord x coordinate
     * @param yCoord y coordinate
     */
    public Cell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord,
                int gridLength, CellType type) {
        this.rootElement = rootElement;
        CellShape cellShape = new CellShape(gridLength);
        if(type == CellType.HEX) {
            cellShape.setHexagonalCell();
        }
        else if(type == CellType.TRIANGLE) {
            cellShape.setTriangularCell();
        }
        else {
            cellShape.setSquareCell();
        }
        cellShape.setCoords(xCoord, yCoord);
        this.block = cellShape.returnShape();
    }

    /**
     * @return JavaFX block
     */
    public Polygon getBlock() {
        return this.block;
    }

    /**
     * @param color The desired color of the border
     */
    public void setBorder(Color color) {
        block.setStroke(color);
        block.setStrokeWidth(STROKE_WIDTH);
    }

    /**
     * @param color The desired color of the block
     */
    public void setColor(Color color) {
        block.setFill(color);
    }

    /**
     * @return The color of the block
     */
    public Paint getColor() {
        return block.getFill();
    }

    /**
     * Sets the cell as manually modified
     */
    public void setAsManuallyModifiedByUser() {
        this.manuallyModifiedByUser = true;
    }

    /**
     * @return Whether the block is manually modified
     */
    public boolean isManuallyModifiedByUser() {
        return manuallyModifiedByUser;
    }

    /**
     * Sets the cell as not manually modified
     */
    public void noLongerManuallyModified() {
        this.manuallyModifiedByUser = false;
    }

    /**
     * Adds the block to the JavaFX scene
     */
    public void addToScene() {
        rootElement.getChildren().add(block);
    }
}