package spreadingoffire;

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
import spreadingoffire.SpreadingOfFireCell.States;

/**
 * @author Soravit
 *
 */
public class SpreadingOfFireSimulation extends Simulation{
	private static final String fire = "Fire: ";
	private static final String dead = "Dead: ";
	private static final String alive = "Alive: ";
	
    private int numberAlive;
    private int numberDead;
    private int numberFire;
    
    private XYChart.Series fireLine;
    private XYChart.Series yellowLine;
    private XYChart.Series aliveLine;
    private int stepCount = 0;
    
    private static final Text numFireText = new Text(
    		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
    		0 + (7 / 5 * dimensionsOfCellCounterBox) - 3 * marginBoxTop, fire);
    private static final Text numDeadText = new Text(
    		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
    		0 + (7 / 5 * dimensionsOfCellCounterBox) - 2 * marginBoxTop, dead);
    private static final Text numAliveText = new Text(
    		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
    		0 + (7 / 5 * dimensionsOfCellCounterBox) - marginBoxTop, alive);

    private double probCatch;
    private SpreadingOfFireGrid myGrid;
    private int[][] cellStates;
    // 0 is empty
    // 1 is tree
    // 2 is burning tree

    /**
     * @param gridLength
     * @param probCatch
     */
    public SpreadingOfFireSimulation(int gridLength, double probCatch) {
        super(gridLength);
        this.probCatch = probCatch;
    }

    @Override
    public Scene init (Stage s) {
        setStage(s);
        makeNewRootElement();
        setMyScene(new Scene(getRootElement(), SIMULATION_WINDOW_WIDTH, 
        		SIMULATION_WINDOW_HEIGHT, Color.WHITE)); 
        setTopMargin(getTopMargin() + marginBoxTop * 4);
        this.myGrid = new SpreadingOfFireGrid(getGridLength(), getCellSize(), getRootElement(), 
        		getLeftMargin(), getTopMargin(), this);
        myGrid.setBackground(SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT);
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        cellStates = new int[getGridLength()][getGridLength()];
        setInitialEnvironment();

        return getMyScene();
    }

    /**
     * @param x
     * @param y
     */
    public void spawnTree(int x, int y) {
        cellStates[x][y] = 1;
        myGrid.updateCell(x,y,1);
        numberAlive++;
        myGrid.getCell(x, y).spawn();
    }
    
    public void checkUpdatedStatesAfterManualMod() {
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
            	if(manuallyModified(i,j)){
            		States cellState = myGrid.getCell(i, j).getState();
            		noLongerModified(i,j);
            		if(cellState == States.FIRE) {
                    	cellStates[i][j] = 2;
                    	numberFire++;
                    	numberAlive--;
                    }
                    else if (cellState == States.DEAD) {
                    	cellStates[i][j] = 0;
                    	numberDead++;
                    	numberFire--;
                    }
                    else {
                    	cellStates[i][j] = 1;
                    	numberAlive++;
                    	numberDead--;
                    }
            	}
            }
        }
    }
    
    private boolean manuallyModified(int row,int col) {
    	return (myGrid.getCell(row, col).isManuallyModified());
    }
    
    private void noLongerModified(int row, int col) {
    	myGrid.getCell(row, col).noLongerManuallyModified();
    }

    /**
     * @param x
     * @param y
     * @param forceBurn
     */
    public void burnTree(int x, int y, boolean forceBurn) {
        double rand = Math.random();
        if(rand < probCatch || forceBurn){
        	myGrid.getCell(x, y).burn();
            cellStates[x][y] = 3;
            myGrid.updateCell(x, y, 2);
            numberFire++;
            numberAlive--;
        }
    }

    /**
     * @param x
     * @param y
     */
    public void clearCell(int x, int y) {
        cellStates[x][y] = 0;
        myGrid.updateCell(x, y, 0);
        myGrid.getCell(x, y).burnout();
        numberDead++;
        if(numberFire > 0){
        	numberFire--;
        }
    }

    /* (non-Javadoc)
     * @see base.Simulation#setInitialEnvironment()
     */
    public void setInitialEnvironment() {
    	numberAlive = 0;
    	numberDead = 0;
    	
    	createGraph();
    	int fireBurnedInitially = 0;
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                if(i == 0 || i == getGridLength() - 1 
                		|| j == 0 || j == getGridLength()-1) {
                    clearCell(i, j);
                }
                else if(i == getGridLength() / 2 
                		&& j == getGridLength() / 2) {
                    burnTree(i, j, true);
                    fireBurnedInitially++;
                }
                else {
                    spawnTree(i, j);
                }
                myGrid.updateCell(i, j, cellStates[i][j]);
            }
        }
        
        numberFire = fireBurnedInitially;
    }

    /**
     * 
     */
    public void updateState() {
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                if(cellStates[i][j] == 1) {
                    if(cellStates[i - 1][j] == 2) {
                        burnTree(i, j, false);
                    } 
                    else if(cellStates[i + 1][j] == 2) {
                        burnTree(i, j, false);
                    }  
                    else if(cellStates[i][j - 1] == 2) {
                        burnTree(i, j, false);
                    }  
                    else if(cellStates[i][j + 1] == 2) {
                        burnTree(i, j, false);
                    }               
                } 
            }
        }

        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                if(cellStates[i][j] == 2) {
                    clearCell(i, j);
                } 
                else if(cellStates[i][j] == 3) {
                    cellStates[i][j] = 2;
                }
            }
        }
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
        fireLine = new XYChart.Series();
        fireLine.setName("Fire");
        yellowLine = new XYChart.Series();
        yellowLine.setName("Dead");
        aliveLine = new XYChart.Series();
        aliveLine.setName("Alive");
        
        
        //populating the series with data
        //series.getData().add(new XYChart.Data(1, 23));
        lineChart.getData().add(fireLine);
        lineChart.getData().add(yellowLine);
        lineChart.getData().add(aliveLine);
        
        lineChart.setLayoutX(25);
        lineChart.setPrefSize(500, 100);
        lineChart.setLegendVisible(true);
        lineChart.setLegendSide(Side.RIGHT);
        getRootElement().getChildren().add(lineChart);
        
        
        Rectangle cellCounter = new Rectangle(
        		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + 2 * marginBoxTop, 
        		(dimensionsOfCellCounterBox / 5), dimensionsOfCellCounterBox * 3 / 2,
        		dimensionsOfCellCounterBox);
        cellCounter.setFill(Color.WHITE);
        cellCounter.setStyle(
			    "-fx-background-radius: 8,7,6;" + 
			    "-fx-background-insets: 0,1,2;" +
			    "-fx-text-fill: black;" +
			    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
		);
        getRootElement().getChildren().add(cellCounter);
        numFireText.setFill(Color.RED);
        numDeadText.setFill(Color.ORANGE);
        numAliveText.setFill(Color.GREEN);
        updateText();
        getRootElement().getChildren().add(numFireText);
        getRootElement().getChildren().add(numDeadText);
        getRootElement().getChildren().add(numAliveText);
        
        
    }
    
    private void updateText() {
    	numFireText.setText(fire + numberFire);
    	numDeadText.setText(dead + numberDead);
    	numAliveText.setText(alive + numberAlive);
    }

    /**
     * 
     */
    public void updateGraph() {
    	fireLine.getData().add(new XYChart.Data(stepCount, numberFire));
    	yellowLine.getData().add(new XYChart.Data(stepCount, numberDead));
    	aliveLine.getData().add(new XYChart.Data(stepCount, numberAlive));
    	updateText();
    }

    @Override
    public void step () {
        updateState();
        updateGraph();
        stepCount++;
    }
}