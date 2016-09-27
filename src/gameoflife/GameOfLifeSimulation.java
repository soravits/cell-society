package gameoflife;

import java.util.Arrays;
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

/**
 * @author Brian
 *
 */
public class GameOfLifeSimulation extends Simulation{
	private static final String dead = "Dead: ";
	private static final String alive = "Alive: ";
	
	
    private int numberAlive;
    private int numberDead;
    
    private XYChart.Series deadLine;
    private XYChart.Series aliveLine;
    private int stepCount = 0;
    
    private static final Text numDeadText = new Text(SIMULATION_WINDOW_WIDTH - (2*dimensionsOfCellCounterBox) + marginBoxTop*3, 0+(7/5*dimensionsOfCellCounterBox) - 2*marginBoxTop,dead);
    private static final Text numAliveText = new Text(SIMULATION_WINDOW_WIDTH - (2*dimensionsOfCellCounterBox) + marginBoxTop*3, 0+(7/5*dimensionsOfCellCounterBox) - marginBoxTop,alive);

    
    
    
    private GameOfLifeGrid myGrid;
    private boolean[][] deadOrAlive;
    private boolean[][] DoATimeBuffer;

    /**
     * @param gridLength
     */
    public GameOfLifeSimulation(int gridLength) {
        super(gridLength);
    }

    /* (non-Javadoc)
     * @see base.Simulation#init(javafx.stage.Stage)
     */
    @Override
    public Scene init(Stage s) {
        setStage(s);
        setMyScene(new Scene(getRootElement(), SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE));  
        setTopMargin(getTopMargin() + marginBoxTop*4);
        this.myGrid = new GameOfLifeGrid(getGridLength(),getCellSize(),getRootElement(),
        		getLeftMargin(),getTopMargin());
        
        myGrid.setBackground(SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT);
        deadOrAlive = new boolean[getGridLength()][getGridLength()];
        DoATimeBuffer = new boolean[getGridLength()][getGridLength()];
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        setInitialEnvironment();

        return getMyScene();
    }


    
    /**
     * 
     */
    public void createGraph(){
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setMinorTickVisible(false);
        
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
        deadLine = new XYChart.Series();
        deadLine.setName("Dead");
        aliveLine = new XYChart.Series();
        aliveLine.setName("Alive");
             
        //populating the series with data
        //series.getData().add(new XYChart.Data(1, 23));
        lineChart.getData().add(deadLine);
        lineChart.getData().add(aliveLine);
        
        lineChart.setLayoutX(25);
        lineChart.setPrefSize(500, 100);
        lineChart.setLegendVisible(true);
        lineChart.setLegendSide(Side.RIGHT);
        getRootElement().getChildren().add(lineChart);
        
        
        Rectangle cellCounter = new Rectangle(SIMULATION_WINDOW_WIDTH - (2*dimensionsOfCellCounterBox) + 2*marginBoxTop, (dimensionsOfCellCounterBox/5),dimensionsOfCellCounterBox*3/2,dimensionsOfCellCounterBox);
        cellCounter.setFill(Color.WHITE);
        cellCounter.setStyle(
			    "-fx-background-radius: 8,7,6;" + 
			    "-fx-background-insets: 0,1,2;" +
			    "-fx-text-fill: black;" +
			    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
		);
        getRootElement().getChildren().add(cellCounter);
        numDeadText.setFill(Color.BLACK);
        numAliveText.setFill(Color.GRAY);
        updateText();
        getRootElement().getChildren().add(numDeadText);
        getRootElement().getChildren().add(numAliveText);
        
        
    }
    
    private void updateText(){
    	numDeadText.setText(dead + numberDead);
    	numAliveText.setText(alive + numberAlive);
    }

    /**
     * 
     */
    public void updateGraph(){
    	deadLine.getData().add(new XYChart.Data(stepCount, numberAlive));
    	aliveLine.getData().add(new XYChart.Data(stepCount, numberDead));
    	updateText();
    }

    /* (non-Javadoc)
     * @see base.Simulation#step()
     */
    @Override
    public void step () {
    	updateStateOfCells();
        updateCellUI();
        updateGraph();
        stepCount++;
    }



    /* (non-Javadoc)
     * @see base.Simulation#setInitialEnvironment()
     */
    public void setInitialEnvironment(){
    	numberAlive = 5;
    	numberDead = (int) Math.pow(getGridLength(), 2)-numberAlive;
    	
        for (boolean[] row: deadOrAlive){
            Arrays.fill(row, false);
        }
        for (boolean[] row: DoATimeBuffer){
            Arrays.fill(row, false);
        }
        deadOrAlive[deadOrAlive.length/2][deadOrAlive.length/2] = true;
        deadOrAlive[deadOrAlive.length/2 + 1][deadOrAlive.length/2] = true;
        deadOrAlive[deadOrAlive.length/2][deadOrAlive.length/2 + 1] = true;
        deadOrAlive[deadOrAlive.length/2 - 1 ][deadOrAlive.length/2] = true;
        deadOrAlive[deadOrAlive.length/2][deadOrAlive.length/2 - 1] = true;

        DoATimeBuffer[deadOrAlive.length/2][deadOrAlive.length/2] = true;
        DoATimeBuffer[deadOrAlive.length/2 + 1][deadOrAlive.length/2] = true;
        DoATimeBuffer[deadOrAlive.length/2][deadOrAlive.length/2 + 1] = true;
        DoATimeBuffer[deadOrAlive.length/2 - 1 ][deadOrAlive.length/2] = true;
        DoATimeBuffer[deadOrAlive.length/2][deadOrAlive.length/2 - 1] = true;
        updateCellUI();
        createGraph();
    }

    /**
     * 
     */
    public void updateCellUI(){
        for(int i = 0; i<getGridLength();i++){
            for(int j=0; j<getGridLength();j++){
                if(DoATimeBuffer[i][j] == true){
                    deadOrAlive[i][j] = true;
                }
                else{
                    deadOrAlive[i][j] = false;
                }
            }
        }

        for(int i = 0; i<getGridLength();i++){
            for(int j=0; j<getGridLength();j++){
                if(deadOrAlive[i][j] == true){
                    reviveCell(i,j);
                }
                else{
                    killCell(i,j);
                }
            }
        }	
    }

    /**
     * @param row
     * @param col
     */
    private void killCell(int row, int col){
        myGrid.updateCell(row,col,false);
    }

    /**
     * @param row
     * @param col
     */
    private void reviveCell(int row, int col){
        myGrid.updateCell(row,col,true);
    }

    /**
     * @param row
     * @param column
     * @param aliveSurroundingCells
     */
    public void updateCurrentCellState(int row, int column, int aliveSurroundingCells) {
        if(deadOrAlive[row][column] == true){
            DoATimeBuffer[row][column] = true;
            if((aliveSurroundingCells >= 3) || (aliveSurroundingCells < 2)){
            	numberDead++;
            	numberAlive--;
                DoATimeBuffer[row][column] = false;
            }
        }
        else{
            if((aliveSurroundingCells == 3)){
            	numberDead--;
            	numberAlive++;
                DoATimeBuffer[row][column] = true;
            }
        }
    }

    /**
     * 
     */
    public void updateStateOfCells(){
        for(int i = 0; i<getGridLength();i++){
            for(int j=0; j<getGridLength();j++){
                int aliveSurroundingCells = 0;
                aliveSurroundingCells += checkNearbyCells(i,j);
                updateCurrentCellState(i,j,aliveSurroundingCells);
            }
        }
    }

    /**
     * @param row
     * @param column
     * @return
     */
    private int checkNearbyCells(int row, int column){
        int aliveNearbyCells = 0;
        //BE CAREFUL, THIS ALGORITHM's DIAGANOL DETECTION DEPENDS ON FOUR SIDED BOUNDARIES
        
        aliveNearbyCells += ifAliveReturn1(row-1,column);
        aliveNearbyCells += ifAliveReturn1(row,column-1);
        aliveNearbyCells += ifAliveReturn1(row+1,column);
        aliveNearbyCells += ifAliveReturn1(row,column+1);
        aliveNearbyCells += ifAliveReturn1(row-1,column+1);
        aliveNearbyCells += ifAliveReturn1(row+1,column+1);
        aliveNearbyCells += ifAliveReturn1(row-1,column-1);
        aliveNearbyCells += ifAliveReturn1(row+1,column-1);
        return aliveNearbyCells;
    }

    /**
     * @param row
     * @param column
     * @return
     */
    private int ifAliveReturn1(int row, int column){
        boolean leftIsInBounds = ((row-1)>=0);
        boolean rightIsInBounds = ((row+1)<getGridLength());
        boolean upIsInBounds = ((column-1)>=0);
        boolean downIsInBounds = ((column+1)<getGridLength());
        if(!(leftIsInBounds && rightIsInBounds && upIsInBounds && downIsInBounds)){
        	return 0;
        }
        if(deadOrAlive[row][column] == true){
            return 1;
        }
        return 0;
    }
}