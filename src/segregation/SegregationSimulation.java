package segregation;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import base.Grid;
import base.Location;
import base.Simulation;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import segregation.SegregationCell.State;

/**
 * This is the Segregation class of Cell Society. 
 * This runs the simulation for a grid that consists of 2 cell types, red and blue, and empty spots.
 * The cells will move into empty spots based on a set of criteria determining whether they are 
 * "satisfied" or not, thus eventually forming clusters of cells that are similar.
 *
 * This models segregation within communities that are composed of different types. 
 * @author Delia
 *
 */
public class SegregationSimulation extends Simulation {
    private static final int EMPTY = 0;
    private static final int SATISFIED = 1;
    private static final int UNSATISFIED = 2;

    private static final String empty = "Empty: ";
    private static final String satisfied = "Satisfied: ";
    private static final String unsatisfied = "Unsatisfied: ";

    private int numberEmpty = 0;
    private int numberSatisfied = 0;
    private int numberUnsatisfied = 0;

    private XYChart.Series emptyLine;
    private XYChart.Series satisfiedLine;
    private XYChart.Series unsatisfiedLine;

    private static final Text numEmptyText = new Text(textPositionHorizontal, 
    		textPositionVertical - MARGIN_BOX_TOP, empty);
    private static final Text numSatisfiedText = new Text(textPositionHorizontal, 
    		textPositionVertical, satisfied);
    private static final Text numUnsatisfiedText = new Text(textPositionHorizontal, 
    		textPositionVertical + MARGIN_BOX_TOP, unsatisfied);

    private SegregationGrid myGrid;
    private int[][] cellSatisfied;
    private double satisfyThresh, percA, percEmpty;
    private int totalSteps = 0;
    private Random random = new Random();

    /**
     * @param gridLength
     * @param threshold		double, number of same-colored neighbors for a cell to be satisfied
     * @param percentA		double, percentage of color A cells
     * @param percentB		double, percentage of color B cells
     * @param percentEmpty	double, percentage of empty cells
     */
    public SegregationSimulation(int gridLength, double threshold, double percentA,
                                 double percentB, double percentEmpty,CellType type) {
        super(gridLength,type);
        this.satisfyThresh = threshold;
        this.percA = percentA * (1 - percentEmpty);
        this.percEmpty = percentEmpty;
    }

	@Override
    public Scene init(Stage s, CellType type) {
        cellSatisfied = new int[getGridLength()][getGridLength()];
        super.init(s, type);
        return getMyScene();
    }

    /**
	Creates grid and positions it properly in scene
     */
	@Override
    public Grid instantiateGrid(){
        this.myGrid = new SegregationGrid(getGridLength(), getCellSize(), getRootElement(),
                getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
        return myGrid;
    }

    /**
     * Sets up Initial Environment for simulation/first step
     */
	@Override
    public void setInitialEnvironment() {
        createGraph();
        int cellType;
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                int cellLottery = random.nextInt(100);
                //if the cell is white
                if(cellLottery <= (percEmpty * 100)) {
                    cellType = 0;
                }
                //if the cell is blue
                else if (cellLottery <= ((percEmpty + percA) * 100)) {
                    cellType = 1;
                }
                else {
                    cellType = 2;
                }
                myGrid.updateCell(new Location(i, j), cellType);
            }
        }
        updateText();
    }

    private void setSatisfiedGrid() {
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                cellSatisfied[i][j] = setSatisfiedState(new Location(i, j));
            }
        }
    }

    /**
     * Changes state of cells depending on neighbor conditions/satisfaction
     * @param location
     * @return int value indicating cell's state
     */
    private int setSatisfiedState(Location location) {
        SegregationCell current = myGrid.getCell(location);
        State currentState = current.getState();
        int sameColor = 0;
        int totalNeighbors = 0;
        //if the cell is uninhabited, can't be satisfied or unsatisfied
        if(current.getColor().equals(Color.WHITE)){
            return EMPTY;
        }
        ArrayList<Location> neighbors = myGrid.getAllNeighbors(location);
        for(int i = 0; i < neighbors.size(); i++) {
            Location neighbor = neighbors.get(i);
            if (neighbor != null){
                if(!myGrid.getCell(neighbor).getColor().equals(Color.WHITE))
                    totalNeighbors++;
                if (myGrid.getCell(neighbor).getState().equals(currentState))
                    sameColor++;
            }
        }

        if((double) sameColor / (double) totalNeighbors >= satisfyThresh) {
            return SATISFIED;
        }

        return UNSATISFIED;
    }

    /**
     * Looks through cellsatisfied array and adds all empty and unsatisfied cells to arraylist.
     * Unsatisfied cells also count as empty because they're guaranteed to move in the next step,
     * thus leaving their spot open.
     * Adds all unsatisfied cells to their own arraylist.
     * Loops through unsatisfied arraylist and calls switch method on each one.
     */
    private void updateState() {
        //make a list of empty spots
        ArrayList<Point> emptySpots = new ArrayList<>();
        ArrayList<Point> unhappySpots = new ArrayList<>();
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                //make a list of dissatisfied cells
                if (cellSatisfied[i][j] == EMPTY
                        || cellSatisfied[i][j] == UNSATISFIED) {
                    emptySpots.add(new Point(i, j));
                }
                if(cellSatisfied[i][j] == UNSATISFIED) {
                    unhappySpots.add(new Point(i, j));
                }
            }
        }

        for(int i = 0; i < unhappySpots.size(); i++) {
            int destinationIndex = random.nextInt(emptySpots.size());
            myGrid.switchCells(unhappySpots.get(i), emptySpots.get(destinationIndex));
            emptySpots.remove(destinationIndex);
        }

        numberEmpty = emptySpots.size();
        numberUnsatisfied = unhappySpots.size();
        numberSatisfied = (int) Math.pow(getGridLength(), 2) - numberEmpty - numberUnsatisfied;
        myGrid.updateStats(totalSteps, numberUnsatisfied);
    }
    
    @Override
    public void createSeries(LineChart lineChart) {
    	emptyLine = new XYChart.Series();
        emptyLine.setName("Empty");
        satisfiedLine = new XYChart.Series();
        satisfiedLine.setName("Satisfied");
        unsatisfiedLine = new XYChart.Series();
        unsatisfiedLine.setName("Unsatisfied");

        //populating the series with data
        lineChart.getData().add(emptyLine);
        lineChart.getData().add(satisfiedLine);
        lineChart.getData().add(unsatisfiedLine);

    }
    
    /**
     * Updates graph with new data
     */
    public void updateGraph() {
        emptyLine.getData().add(new XYChart.Data(totalSteps, numberEmpty));
        satisfiedLine.getData().add(new XYChart.Data(totalSteps, numberSatisfied));
        unsatisfiedLine.getData().add(new XYChart.Data(totalSteps, numberUnsatisfied));
        updateText();
    }

    @Override
    public void step() {
        totalSteps++;
        setSatisfiedGrid();
        updateState();
        updateGraph();
        if(numberUnsatisfied == 0) stopSimulation();
    }

    /**
     * Creates cell counter at top right that keeps track of number of cells
     */
    @Override
    public void createCellCounter() {
        Rectangle cellCounter = new Rectangle(
                SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER)
                        + 2 * MARGIN_BOX_TOP, (DIMENSIONS_OF_CELL_COUNTER / 5),
                DIMENSIONS_OF_CELL_COUNTER * 3 / 2, DIMENSIONS_OF_CELL_COUNTER);
        cellCounter.setFill(Color.WHITE);
        cellCounter.setStyle(getCellCounterStyle());
        getRootElement().getChildren().add(cellCounter);
        
        numEmptyText.setFill(Color.GRAY);
        numSatisfiedText.setFill(Color.DARKBLUE);
        numUnsatisfiedText.setFill(Color.LIGHTGREEN);
        updateText();
        getRootElement().getChildren().add(numEmptyText);
        getRootElement().getChildren().add(numSatisfiedText);
        getRootElement().getChildren().add(numUnsatisfiedText);
    }

    private void updateText() {
        numEmptyText.setText(empty + numberEmpty);
        numSatisfiedText.setText(satisfied + numberSatisfied);
        numUnsatisfiedText.setText(unsatisfied + numberUnsatisfied);
    }
}