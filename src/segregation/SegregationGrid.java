package segregation;
import java.awt.Point;
import base.Cell;
import base.Grid;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This is the Grid class for segregation. 
 * It's responsible for some specific behaviors such as switching cells,
 * setting and updating the statistics, etc.
 * @author Delia
 *
 */
public class SegregationGrid extends Grid{
    private Text stats;
    
    /**
     * @param rowLength
     * @param sizeOfCell
     * @param rootElement
     * @param initialX
     * @param initialY
     */
    public SegregationGrid(int rowLength, int sizeOfCell, Pane rootElement,
                           int initialX, int initialY) {
        super(rowLength, sizeOfCell, rootElement, initialX, initialY);
    }
    
    /**
     * @param row
     * @param column
     * @return cell located at those coordinates
     */
    public Cell getCell(int row, int column){
        return getGrid()[row][column];
    }
    
    /* (non-Javadoc)
     * @see base.Grid#initializeGrid()
     * maybe we should put this in the superclass and have separate methods 
     * in each of our grids that call this. 
     */
    @Override
    public void initializeGrid() {
        for(int i = 0; i < getGrid().length; i++){
            for(int j = 0; j < getGrid()[0].length; j++){
                Cell gridCell = new Cell(getSizeOfCell(), getRootElement(), 
                		getSizeOfCell() * (i) + getInitialX(), 
                		getSizeOfCell()* (j) + getInitialY());
                gridCell.addToScene();
                getGrid()[i][j] = gridCell;                          
            }
        }      
        setStats();
    }
    
    /**
     * Switches two cells on the grid.
     * Stores the destination color as local Paint object
     * Sets destination color with color of moving cell
     * Updates original position of mover with stored destination color
     * @param p1	coordinates of point that is moving
     * @param p2	coordinates of point that is destination
     */
    public void switchCells(Point p1, Point p2){
        Paint destination = getGrid()[p2.x][p2.y].getColor();
        getGrid()[p2.x][p2.y].setColor(getGrid()[p1.x][p1.y].getColor());
        getGrid()[p1.x][p1.y].setColor(destination);
    }
    
    /**
     * Sets the text that will display grid statistics
     */
    public void setStats(){
    	//stats = new Text(60, 150, "Round \nUnsatisfied Cells");
        stats = new Text(75, 155, "Round");
        stats.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
        stats.setFill(Color.WHITE);
        getRootElement().getChildren().add(stats);
    }
    
    /**
     * Updates the stats for the grid
     * @param stepNumber			Which step in the simulation it is
     * @param numberUnsatisfied		Number of cells that aren't satisfied
     */
    public void updateStats(int stepNumber, int numberUnsatisfied){
        //String currentStat = "Round " + stepNumber + "\nUnsatisfied Cells " + numberUnsatisfied;
    	String currentStat = "Round " + stepNumber;
    	stats.setText(currentStat);
    }
    
    /**
     * Sets the color of cell at those coordinates based on its state
     * @param x
     * @param y
     * @param cellState
     */
    public void updateCell(int x, int y, int cellState){
        if(cellState == 0)
            getGrid()[x][y].setColor(Color.WHITE);
        else if(cellState == 1)
            getGrid()[x][y].setColor(Color.DARKBLUE);
        else
            getGrid()[x][y].setColor(Color.LIMEGREEN);
    }
}