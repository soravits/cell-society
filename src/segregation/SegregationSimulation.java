package segregation;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import base.Cell;
import base.Grid;
import base.Simulation;
import base.Simulation.CellType;
import javafx.animation.Timeline;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import segregation.SegregationCell.State;
import waterworld.WaTorWorldCell;

/**
 * This is the SegregationSimulation class of Cell Society.
 * This runs the simulation for a grid that consists of 2 cell types, red and blue, and empty spots.
 * The cells will move into empty spots based on a set of criteria determining whether they are 
 * "satisfied" or not, thus eventually forming clusters of cells that are similar.
 * 
 * This models segregation within communities that are composed of different types. 
 * @author Delia
 *
 */
public class SegregationSimulation extends Simulation{
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
    
    private static final Text numEmptyText = new Text(
    		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
    		0 + (7 / 5 * dimensionsOfCellCounterBox) - 3 * marginBoxTop, empty);
    private static final Text numSatisfiedText = new Text(
    		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
    		0 + (7 / 5 * dimensionsOfCellCounterBox) - 2 * marginBoxTop, satisfied);
    private static final Text numUnsatisfiedText = new Text(
    		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
    		0 + (7 / 5 * dimensionsOfCellCounterBox) - marginBoxTop, unsatisfied);
    
    private SegregationGrid myGrid;
    private int[][] cellSatisfied;
    private double satisfyThresh, percA, percEmpty;
    private int totalSteps = 0;
    private Random random = new Random();
    
    /**
     * @param gridLength
     * @param threshold
     * @param percentA
     * @param percentB
     * @param percentEmpty
     */
    public SegregationSimulation(int gridLength, double threshold, double percentA,
                                 double percentB, double percentEmpty, CellType type) {
        super(gridLength,type);
        this.satisfyThresh = threshold;
        this.percA = percentA * (1 - percentEmpty);
        this.percEmpty = percentEmpty;
    }

    @Override
    public Scene init(Stage s,CellType type) {
        setStage(s);
        makeNewRootElement();

		int screenWidth = SIMULATION_WINDOW_WIDTH;
		if(type == CellType.HEX){
			screenWidth *= 1.75;
		}

		setMyScene(new Scene(getRootElement(), screenWidth,
				SIMULATION_WINDOW_HEIGHT, Color.WHITE));
        setTopMargin(getTopMargin() + marginBoxTop*4);
        this.myGrid = new SegregationGrid(getGridLength(), getCellSize(), getRootElement(),
        		getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
        myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
        myGrid.initializeGrid(type);
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        cellSatisfied = new int[getGridLength()][getGridLength()];
        setInitialEnvironment();
        return getMyScene();
    }


    /* (non-Javadoc)
     * @see base.Simulation#setInitialEnvironment()
     */
    public void setInitialEnvironment() {
    	createGraph();
        int cellType;
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                int cellLottery = random.nextInt(100);
                //if the cell is white
                if(cellLottery <= (percEmpty * 100)) {
                    cellType = 0;
                    numberEmpty++;
                }
                //if the cell is blue
                else if (cellLottery <= ((percEmpty + percA) * 100)) {
                    cellType = 1;
                    numberUnsatisfied++;
                }
                else {
                    cellType = 2;
                }
                myGrid.updateCell(i, j, cellType);
            }
        }
        numberSatisfied = (int) Math.pow(getGridLength(), 2) - numberEmpty - numberUnsatisfied;
        updateText();

    }
    
    /**
     * Loop that sets 2D array with 0, 1, or 2 for each possible state
     * 0 = empty
     * 1 = satisfied
     * 2 = unsatisfied
     */
    public void setSatisfiedGrid() {
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                cellSatisfied[i][j] = setSatisfiedState(i, j);
            }
        }
    }
    
    /**
     * MUST CHANGE THIS
     * WORST METHOD EVER BUT IT WORKS
     * @param row
     * @param col
     * @return int value indicating cell's state
     */
    public int setSatisfiedState(int row, int col) {
        SegregationCell current = myGrid.getCell(row, col);
        State currentState = current.getState();
        int sameColor = 0;
        int totalNeighbors = 0;
        //if the cell is uninhabited, can't be satisfied or unsatisfied
        if(current.getColor().equals(Color.WHITE)){ 
            return EMPTY;
        }
        //checks north

        if(myGrid.getNorthernNeighbor(row, col) != null && !myGrid.getCell(myGrid.getNorthernNeighbor(row, col).getRow(),
                myGrid.getNorthernNeighbor(row, col).getColumn()).getColor().equals(Color.WHITE)) {
            totalNeighbors++;
            if(myGrid.getCell(myGrid.getNorthernNeighbor(row, col).getRow(),
                    myGrid.getNorthernNeighbor(row, col).getColumn()).getState().equals(currentState))
                sameColor++;
        }
        //checks south
        if(myGrid.getSouthernNeighbor(row, col) != null && !myGrid.getCell(myGrid.getSouthernNeighbor(row, col).getRow(),
                myGrid.getSouthernNeighbor(row, col).getColumn()).getColor().equals(Color.WHITE)) {
            totalNeighbors++;
            if(myGrid.getCell(myGrid.getSouthernNeighbor(row, col).getRow(),
                    myGrid.getSouthernNeighbor(row, col).getColumn()).getState().equals(currentState))
                sameColor++;
        }
        //checks west
        if(myGrid.getWesternNeighbor(row, col) != null && !myGrid.getCell(myGrid.getWesternNeighbor(row, col).getRow(),
                myGrid.getWesternNeighbor(row, col).getColumn()).getColor().equals(Color.WHITE)) {
            totalNeighbors++;
            if(myGrid.getCell(myGrid.getWesternNeighbor(row, col).getRow(),
                    myGrid.getWesternNeighbor(row, col).getColumn()).getState().equals(currentState))
                sameColor++;
        }
        //checks east
        if(myGrid.getEasternNeighbor(row, col) != null && !myGrid.getCell(myGrid.getEasternNeighbor(row, col).getRow(),
                myGrid.getEasternNeighbor(row, col).getColumn()).getColor().equals(Color.WHITE)) {
            totalNeighbors++;
            if(myGrid.getCell(myGrid.getEasternNeighbor(row, col).getRow(),
                    myGrid.getEasternNeighbor(row, col).getColumn()).getState().equals(currentState))
                sameColor++;
        }
        //checks northwest
        if(myGrid.getNorthwesternNeighbor(row, col) != null && !myGrid.getCell(myGrid.getNorthwesternNeighbor(row, col).getRow(),
                myGrid.getNorthwesternNeighbor(row, col).getColumn()).getColor().equals(Color.WHITE)) {
            totalNeighbors++;
            if(myGrid.getCell(myGrid.getNorthwesternNeighbor(row, col).getRow(),
                    myGrid.getNorthwesternNeighbor(row, col).getColumn()).getState().equals(currentState))
                sameColor++;
        }
        //checks southwest
        if(myGrid.getSouthwesternNeighbor(row, col) != null && !myGrid.getCell(myGrid.getSouthwesternNeighbor(row, col).getRow(),
                myGrid.getSouthwesternNeighbor(row, col).getColumn()).getColor().equals(Color.WHITE)) {
            totalNeighbors++;
            if(myGrid.getCell(myGrid.getSouthwesternNeighbor(row, col).getRow(),
                    myGrid.getSouthwesternNeighbor(row, col).getColumn()).getState().equals(currentState))
                sameColor++;
        }
        //checks northeast
        if(myGrid.getNortheasternNeighbor(row, col) != null && !myGrid.getCell(myGrid.getNortheasternNeighbor(row, col).getRow(),
                myGrid.getNortheasternNeighbor(row, col).getColumn()).getColor().equals(Color.WHITE)) {
            totalNeighbors++;
            if(myGrid.getCell(myGrid.getNortheasternNeighbor(row, col).getRow(),
                    myGrid.getNortheasternNeighbor(row, col).getColumn()).getState().equals(currentState))
                sameColor++;
        }
        //checks southeast
        if(myGrid.getSoutheasternNeighbor(row, col) != null && !myGrid.getCell(myGrid.getSoutheasternNeighbor(row, col).getRow(),
                myGrid.getSoutheasternNeighbor(row, col).getColumn()).getColor().equals(Color.WHITE)) {
            totalNeighbors++;
            if(myGrid.getCell(myGrid.getSoutheasternNeighbor(row, col).getRow(),
                    myGrid.getSoutheasternNeighbor(row, col).getColumn()).getState().equals(currentState))
                sameColor++;
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
    public void updateState() {
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
//            System.out.println("destination " + myGrid.getCell(emptySpots.get(destinationIndex).x, 
//            		emptySpots.get(destinationIndex).y).getState());
//            System.out.println("origin " + myGrid.getCell(unhappySpots.get(i).x, 
//            		unhappySpots.get(i).y).getState());
            myGrid.switchCells(unhappySpots.get(i), emptySpots.get(destinationIndex));
            emptySpots.remove(destinationIndex);
        }

        numberEmpty = emptySpots.size();
        numberUnsatisfied = unhappySpots.size();
        numberSatisfied = (int) Math.pow(getGridLength(), 2) - numberEmpty - numberUnsatisfied;
        myGrid.updateStats(totalSteps, numberUnsatisfied);
    }
    
    
    /**
     * 
     */
    public void createGraph() {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setMinorTickVisible(false);
        
        //creating the chart
        final LineChart <Number, Number> lineChart = 
                new LineChart <Number, Number> (xAxis, yAxis);
        emptyLine = new XYChart.Series();
        emptyLine.setName("Empty");
        satisfiedLine = new XYChart.Series();
        satisfiedLine.setName("Satisfied");
        unsatisfiedLine = new XYChart.Series();
        unsatisfiedLine.setName("Unsatisfied");
             
        //populating the series with data
        //series.getData().add(new XYChart.Data(1, 23));
        lineChart.getData().add(emptyLine);
        lineChart.getData().add(satisfiedLine);
        lineChart.getData().add(unsatisfiedLine);
        
        lineChart.setLayoutX(25);
        lineChart.setPrefSize(500, 100);
        lineChart.setLegendVisible(true);
        lineChart.setLegendSide(Side.RIGHT);
        getRootElement().getChildren().add(lineChart);
        
        
        Rectangle cellCounter = new Rectangle(
        		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) 
        		+ 2 * marginBoxTop, (dimensionsOfCellCounterBox / 5), 
        		dimensionsOfCellCounterBox * 3 / 2, dimensionsOfCellCounterBox);
        cellCounter.setFill(Color.WHITE);
        cellCounter.setStyle(
			    "-fx-background-radius: 8,7,6;" + 
			    "-fx-background-insets: 0,1,2;" +
			    "-fx-text-fill: black;" +
			    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
		);
        getRootElement().getChildren().add(cellCounter);
        numEmptyText.setFill(Color.GRAY);
        numSatisfiedText.setFill(Color.DARKBLUE);
        numUnsatisfiedText.setFill(Color.LIGHTGREEN);
        updateText();
        getRootElement().getChildren().add(numEmptyText);
        getRootElement().getChildren().add(numSatisfiedText);
        getRootElement().getChildren().add(numUnsatisfiedText);
        
        
    }
    
    private void updateText(){
    	numEmptyText.setText(empty + numberEmpty);
        numSatisfiedText.setText(satisfied + numberSatisfied);
        numUnsatisfiedText.setText(unsatisfied + numberUnsatisfied);
    }

    /**
     * 
     */
    public void updateGraph(){
    	emptyLine.getData().add(new XYChart.Data(totalSteps, numberEmpty));
    	satisfiedLine.getData().add(new XYChart.Data(totalSteps, numberSatisfied));
    	unsatisfiedLine.getData().add(new XYChart.Data(totalSteps, numberUnsatisfied));
    	updateText();
    }
    
    /* (non-Javadoc)
     * @see base.Simulation#step()
     */
    @Override
    public void step() {
        totalSteps++;
        setSatisfiedGrid();
        updateState();
        updateGraph();
        if(numberUnsatisfied == 0) stopSimulation();
    }
    
}