package segregation;
import java.awt.Point;
import segregation.SegregationCell.State;
import base.Grid;
import base.Simulation.CellType;
import gameoflife.GameOfLifeCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
public class SegregationGrid extends Grid {
    private Text stats;
    private Segregation sim;
    private CellType type;

    /**
     * @param rowLength
     * @param sizeOfCell
     * @param rootElement
     * @param initialX
     * @param initialY
     */
    public SegregationGrid(int rowLength, int sizeOfCell, Pane rootElement,
                           int initialX, int initialY, gridEdgeType edgeType, Segregation sim) {
        super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
        this.sim = sim;
    }

    /**
     * @param row
     * @param col
     * @return cell located at those coordinates
     */
    public SegregationCell getCell(int row, int col) {
        return (SegregationCell) super.getCell(row, col);
    }

    /* (non-Javadoc)
     * @see base.Grid#initializeGrid()
     * maybe we should put this in the superclass and have separate methods 
     * in each of our grids that call this. 
     */
    @Override
    public void initializeGrid(CellType type) {
    	this.type = type;
        for(int i = 0; i < getColumnLength(); i++) {
            for(int j = 0; j < getRowLength(); j++) {
            	int horizontalOffset = getInitialX();
            	
            	 SegregationCell gridCell = new SegregationCell(getSizeOfCell(), getRootElement(), 
                         getSizeOfCell() * (i) + horizontalOffset, 
                         getSizeOfCell() * (j) + getInitialY(),getRowLength(),type);

                gridCell.addToScene();
                setCell(i,j,gridCell);    
                setUpListener(gridCell);                      
            }
        }      
        setStats();
    }
    
    private void createNewCell(int i,int j){
    	int horizontalOffset = getInitialX();
    	double horizontalShift = getSizeOfCell();
    	double verticalShift = getSizeOfCell();
    	if(type == CellType.HEX){
    		horizontalShift = getSizeOfCell()* 6/10;
    		verticalShift = 1.925* getSizeOfCell();
        	if(j%2 == 0){
        		horizontalOffset= getInitialX() + getSizeOfCell();
        		
        	}
    	}
    	
    	 SegregationCell gridCell = new SegregationCell(getSizeOfCell(), getRootElement(), 
                 verticalShift * (i) + horizontalOffset, 
                 horizontalShift * (j) + getInitialY(),getRowLength(),type);

        gridCell.addToScene();
        setCell(i,j,gridCell);    
        setUpListener(gridCell); 
    }

    private void setUpListener(SegregationCell gridCell) {
        gridCell.returnBlock().setOnMousePressed(event -> {
            gridCell.setAsManuallyModified();
            if(gridCell.getState() == State.EMPTY) {
                gridCell.setState(State.COLORA);
            }
            else if(gridCell.getState() == State.COLORA) {
                gridCell.setState(State.COLORB);
            }
            else {
                gridCell.setState(State.EMPTY);
            }
            gridCell.updateColor();
            sim.updateGraph();
        });
    }

    /**
     * Switches two cells on the grid.
     * Stores the destination color as local Paint object
     * Sets destination color with color of moving cell
     * Updates original position of mover with stored destination color
     * @param p1	coordinates of point that is moving
     * @param p2	coordinates of point that is destination
     */
    public void switchCells(Point p1, Point p2) {
        SegregationCell destCell = (SegregationCell) getCell(p2.x,p2.y);
        SegregationCell origCell = (SegregationCell) getCell(p1.x,p1.y);
        updateCell(p1.x, p1.y, destCell.getState());
        updateCell(p2.x, p2.y, origCell.getState());
    }

    /**
     * Sets the text that will display grid statistics
     */
    public void setStats() {
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
    public void updateStats(int stepNumber, int numberUnsatisfied) {
        String currentStat = "Round " + stepNumber;
        stats.setText(currentStat);
    }

    public void updateCell(int x, int y, State cellState) {
        SegregationCell gridCell = new SegregationCell(getSizeOfCell(), getRootElement(), 
                                                       getSizeOfCell() * (x) + getInitialX(), 
                                                       getSizeOfCell()* (y) + getInitialY(),getRowLength(),type);
        if(cellState.equals(State.EMPTY)) {
            gridCell.setColor(Color.WHITE);
            gridCell.setState(State.EMPTY);
        }
        else if(cellState.equals(State.COLORA)) {
            gridCell.setColor(Color.DARKBLUE);
            gridCell.setState(State.COLORA);

        }
        else {
            gridCell.setColor(Color.LIMEGREEN);
            gridCell.setState(State.COLORB);
        }

        gridCell.addToScene();
        setCell(x,y,gridCell);    
        setUpListener(gridCell);
    }

    /**
     * Sets the color of cell at those coordinates based on its state
     * @param x
     * @param y
     * @param cellState
     */
    public void updateCell(int x, int y, int cellState) {
        if(cellState == 0) 
            updateCell(x, y, State.EMPTY);
        else if(cellState == 1) 
            updateCell(x, y, State.COLORA);
        else 
            updateCell(x, y, State.COLORB);
    }
}