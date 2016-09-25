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
     * @return
     */
    public Cell getCell(int row, int column){
        return (Cell) getGrid()[row][column];
    }
    
    /* (non-Javadoc)
     * @see base.Grid#initializeGrid()
     */
    @Override
    public void initializeGrid() {
        for(int i = 0; i < getGrid().length; i++){
            for(int j = 0; j < getGrid()[0].length; j++){
                Cell gridCell = new Cell(getSizeOfCell(), getRootElement(),
                                         getSizeOfCell() * (i) + getInitialX(), getSizeOfCell()* (j) + getInitialY());
                //                    getGrid()Cell.fillCellWithColors();
                gridCell.addToScene();
                getGrid()[i][j] = gridCell;                          
            }
        }      
        setStats();
    }
    
    /**
     * @param p1
     * @param p2
     */
    public void switchCells(Point p1, Point p2){
        Paint destination = getGrid()[p2.x][p2.y].getColor(); //store destination color
        getGrid()[p2.x][p2.y].setColor(getGrid()[p1.x][p1.y].getColor()); //update destination color with mover
        getGrid()[p1.x][p1.y].setColor(destination); //update mover start position with destination original color
    }
    
    /**
     * 
     */
    public void setStats(){
        stats = new Text(60, 70, "Round \nUnsatisfied Cells");
        stats.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
        stats.setFill(Color.BLACK);
        getRootElement().getChildren().add(stats);
    }
    
    /**
     * @param stepNumber
     * @param numberUnsatisfied
     */
    public void updateStats(int stepNumber, int numberUnsatisfied){
        String currentStat = "Round " + stepNumber + "\nUnsatisfied Cells " + numberUnsatisfied;
        stats.setText(currentStat);
    }
    
    /**
     * @param x
     * @param y
     * @param cellState
     */
    public void updateCell(int x, int y, int cellState){
        if(cellState == 0){
            getGrid()[x][y].setColor(Color.WHITE);
        }else if(cellState == 1){
            getGrid()[x][y].setColor(Color.BLUE);
        }else{
            getGrid()[x][y].setColor(Color.RED);
        }
    }
}