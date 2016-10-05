package sugarscape;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import base.Location;
import sugarscape.SugarScapeCell.State;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import base.Grid;
import base.Simulation;

/**
 * @author Delia
 *
 */
public class SugarScapeSimulation extends Simulation {
    private static final int PATCH = 0;
    private static final int AGENT = 1;
    
	private static final String sugar = "Patch sugar: ";
	private static final String carbs = "Agent carbs: ";
//	
    private int patchTotalSugar = 0;
    private int agentTotalCarbs = 0;
    private int numTotalAgents = 0;
    
    private XYChart.Series agentSugarLine;
    private XYChart.Series agentPopulationLine;
    private XYChart.Series patchLine;
    
    private static final Text amtSugarText = new Text(
    		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
    		0 + (7 / 5 * dimensionsOfCellCounterBox) - 3 * marginBoxTop, sugar);
    private static final Text amtCarbsText = new Text(
    		SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
    		0 + (7 / 5 * dimensionsOfCellCounterBox) - 2 * marginBoxTop, carbs);
    
    private SugarScapeGrid myGrid;
    private int maxPatchSugar, growBackRate, numAgents, agentMaxCarbs, agentMinCarbs, 
    	agentMetabRate, agentVision, preset;
    private double percAgents;
    private int totalSteps = 0;
    private Random random = new Random();
    
	/**
	 * @param myGridLength
	 * @param maxSugarPerPatch
	 * @param totalAgents
	 * @param growSugarBackRate
	 * @param agentMaxCarbs
	 * @param agentMinCarbs
	 * @param agentMetabRate
	 * @param agentVision
	 * @param preset
	 * @param type
	 */
	public SugarScapeSimulation(int myGridLength, int maxSugarPerPatch, int totalAgents, 
			int growSugarBackRate, int agentMaxCarbs, int agentMinCarbs, int agentMetabRate, 
			int agentVision, int preset, CellType type) {
		super(myGridLength, type);
		this.maxPatchSugar = maxSugarPerPatch;
		this.numAgents = totalAgents;
		this.growBackRate = growSugarBackRate;
		this.percAgents = (double) totalAgents / (double) (myGridLength * myGridLength);
		this.agentMaxCarbs = agentMaxCarbs;
		this.agentMinCarbs = agentMinCarbs;
		this.agentMetabRate = agentMetabRate;
		this.agentVision = agentVision;
		this.preset = preset;
	}

	/* (non-Javadoc)
	 * @see base.Simulation#init(javafx.stage.Stage, base.Simulation.CellType)
	 */
	@Override
	public Scene init(Stage s, CellType type) {
        setStage(s);
        setNewRootElement();

		int screenWidth = SIMULATION_WINDOW_WIDTH;
		if(type == CellType.HEX){
			screenWidth *= 1.75;
		}
		
		setMyScene(new Scene(getRootElement(), screenWidth, 
				SIMULATION_WINDOW_HEIGHT, Color.WHITE)); 
        setTopMargin(getTopMargin() + marginBoxTop * 4);
        this.myGrid = new SugarScapeGrid(getGridLength(), getCellSize(), getRootElement(), 
        		getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
        myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
        myGrid.initializeGrid(type);
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        createGraph();
        setInitialEnvironment();
        if(preset == 2) setClusterEnvironment();
        return getMyScene();
	}

	/* (non-Javadoc)
	 * @see base.Simulation#setInitialEnvironment()
	 */
	@Override
	public void setInitialEnvironment() {
        int cellType;
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                int cellLottery = random.nextInt(100);
                //if the cell is an agent
                if(preset == 1 && cellLottery <= (percAgents * 100)) {
                    cellType = 1;
                }
                else { //if the cell is a patch
                    cellType = 0;
                }
                myGrid.updateCell(new Location(i, j), cellType);
            }
        }
        updateText();

	}
	
	/**
	 * 
	 */
	public void setClusterEnvironment() {
		int clusterGrid = 20;
		int cellType;
		percAgents = (double) numAgents / (double) (clusterGrid * clusterGrid);
        for(int i = 0; i < clusterGrid; i++) {
            for(int j = 0; j < clusterGrid; j++) {
                int cellLottery = random.nextInt(100);
                //if the cell is an agent
                if(cellLottery <= (percAgents * 100)) {
                    cellType = 1;
//                    numberEmpty++;
                }
                else { //if the cell is a patch
                    cellType = 0;
                }
                myGrid.updateCell(new Location(i, j), cellType);
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
    public void findAgentDestination(int row, int col) {
        Point origin = new Point(row, col);
        Point destination = new Point(row, col);
        ArrayList<Point> destChoices = new ArrayList<>();
        int maxNeighborSugar = -1;
        
        //checks north
        checkNorthernNeighbors(row, col, maxNeighborSugar, destination, destChoices);
        checkSouthernNeighbors(row, col, maxNeighborSugar, destination, destChoices);
        checkEasternNeighbors(row, col, maxNeighborSugar, destination, destChoices);
        checkWesternNeighbors(row, col, maxNeighborSugar, destination, destChoices);

        if(destChoices.size() > 0){
        	destination = destChoices.get(random.nextInt(destChoices.size()));
        }
        if(!(origin.x == destination.x && origin.y == destination.y)){
        	myGrid.moveAgent(origin, destination);
		}
    }
    
    /**
     * @param row
     * @param col
     * @param maxNeighborSugar
     * @param destination
     * @param destChoices
     */
    public void checkNorthernNeighbors(int row, int col, int maxNeighborSugar, 
    		Point destination, ArrayList<Point> destChoices) {
    	SugarScapeCell myNorth;
    	for(int i = 0; i < agentVision; i++){
			Location location = new Location(row - i, col);
    		if(row - i >= 0 && myGrid.getNorthernNeighbor(location) != null) {
            	myNorth = myGrid.getCell(myGrid.getNorthernNeighbor(location));
            //myNorth.getColor().equals(Color.BLACK)
            	if(myNorth.getState() != State.AGENT && myNorth.getSugarAmount() >= maxNeighborSugar) {
                	destination = new Point(row - i, col);
                	maxNeighborSugar = myNorth.getSugarAmount();
                	if(myNorth.getSugarAmount() == maxPatchSugar){
                		destChoices.add(destination);
                	}
                }
            }
    	}
    }
    
    /**
     * @param row
     * @param col
     * @param maxNeighborSugar
     * @param destination
     * @param destChoices
     */
    public void checkSouthernNeighbors(int row, int col, int maxNeighborSugar, 
    		Point destination, ArrayList<Point> destChoices) {
    	SugarScapeCell mySouth;
    	for(int i = 0; i < agentVision; i++){
			Location location = new Location(row + i, col);
    		if(row + i < getGridLength() && myGrid.getSouthernNeighbor(location) != null) {
            	mySouth = myGrid.getCell(myGrid.getSouthernNeighbor(location));
            
            	if(mySouth.getState() != State.AGENT 
            			&& mySouth.getSugarAmount() >= maxNeighborSugar) {
                	destination = new Point(row + i, col);
                	maxNeighborSugar = mySouth.getSugarAmount();
                	if(mySouth.getSugarAmount() == maxPatchSugar) {
                		destChoices.add(destination);
                	}
                }
            }
    	}
    }
    
    /**
     * @param row
     * @param col
     * @param maxNeighborSugar
     * @param destination
     * @param destChoices
     */
    public void checkEasternNeighbors(int row, int col, int maxNeighborSugar, 
    		Point destination, ArrayList<Point> destChoices) {
    	SugarScapeCell myEast;
    	for(int i = 0; i < agentVision; i++) {
			Location location = new Location(row, col + i);
    		if(col + i < getGridLength() && myGrid.getEasternNeighbor(location) != null) {
            	myEast = myGrid.getCell(myGrid.getEasternNeighbor(location));
            	if(myEast.getState() != State.AGENT && myEast.getSugarAmount() >= maxNeighborSugar) {
                	destination = new Point(row, col + i);
                	maxNeighborSugar = myEast.getSugarAmount();
                	if(myEast.getSugarAmount() == maxPatchSugar) {
                		destChoices.add(destination);
                	}
                }
            }
    	}
    }
    
    /**
     * @param row
     * @param col
     * @param maxNeighborSugar
     * @param destination
     * @param destChoices
     */
    public void checkWesternNeighbors(int row, int col, int maxNeighborSugar, 
    		Point destination, ArrayList<Point> destChoices){
    	SugarScapeCell myWest;
    	for(int i = 0; i < agentVision; i++){
			Location location = new Location(row, col - i);
    		if(col - i >= 0 && myGrid.getWesternNeighbor(location) != null) {
            	myWest = myGrid.getCell(myGrid.getWesternNeighbor(location));
            
            	if(myWest.getState() != State.AGENT && myWest.getSugarAmount() >= maxNeighborSugar){
                	destination = new Point(row, col - i);
                	maxNeighborSugar = myWest.getSugarAmount();
                	if(myWest.getSugarAmount() == maxPatchSugar){
                		destChoices.add(destination);
                	}
                }
            }
    	}
    }
	
	/**
	 * 
	 */
	public void updateState(){
		int updateSugarAmt = 0;
		int updateCarbAmt = 0;
		int updateAgentPop = 0;
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
				Location location = new Location(i, j);
            	if(myGrid.getCell(location).getState() == State.PATCH){
            		myGrid.getCell(location).growSugarBack();
            		updateSugarAmt += myGrid.getCell(location).getSugarAmount();
            	}
            	else {
            		updateCarbAmt += myGrid.getCell(location).getAgentCarbs();
            		updateAgentPop++;
            		findAgentDestination(i, j);
            	}
            }
        }
        patchTotalSugar = updateSugarAmt;
        agentTotalCarbs = updateCarbAmt;
        numTotalAgents = updateAgentPop;
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
        agentSugarLine = new XYChart.Series();
        agentSugarLine.setName("Agent Carbs");
        patchLine = new XYChart.Series();
        patchLine.setName("Patch Sugar");
        agentPopulationLine = new XYChart.Series();
        agentPopulationLine.setName("Agent Population");
             
        //populating the series with data
        //series.getData().add(new XYChart.Data(1, 23));
        lineChart.getData().add(agentSugarLine);
        lineChart.getData().add(patchLine);
        lineChart.getData().add(agentPopulationLine);
        
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
        amtSugarText.setFill(Color.GRAY);
        amtCarbsText.setFill(Color.DARKBLUE);
//        agentPopText.setFill(Color.BLACK);
        updateText();
        getRootElement().getChildren().add(amtSugarText);
        getRootElement().getChildren().add(amtCarbsText);
//        getRootElement().getChildren().add(agentPopText);
    }
	
    private void updateText(){
    	amtSugarText.setText(sugar + patchTotalSugar);
        amtCarbsText.setText(carbs + agentTotalCarbs);
//        agentPopText.setText(agents + numTotalAgents); 
    }
    
    /**
     * 
     */
    public void updateGraph() {
    	patchLine.getData().add(new XYChart.Data(totalSteps, patchTotalSugar));
    	agentSugarLine.getData().add(new XYChart.Data(totalSteps, agentTotalCarbs));
    	agentPopulationLine.getData().add(new XYChart.Data(totalSteps, numTotalAgents));
    	updateText();
    }

	@Override
	public void step() {
        totalSteps++;
//        setSatisfiedGrid();
        updateState();
        updateGraph();
//        if(numberUnsatisfied == 0) stopSimulation();
		
	}

	/**
	 * @return
	 */
	public int getMaxPatchSugar() {
		return maxPatchSugar;
	}
	
	/**
	 * @return
	 */
	public int getAgentMaxCarbs() {
		return agentMaxCarbs;
	}
	
	/**
	 * @return
	 */
	public int getAgentMinCarbs() {
		return agentMinCarbs;
	}
	
	/**
	 * @return
	 */
	public int getMetabRate() {
		return agentMetabRate;
	}
	
	/**
	 * @param newSugar
	 */
	public void updateTotalSugar(int newSugar) {
		patchTotalSugar += newSugar;
	}
	
	/**
	 * @param newCarbs
	 */
	public void updateTotalCarbs(int newCarbs) {
		agentTotalCarbs += newCarbs;
	}
}
