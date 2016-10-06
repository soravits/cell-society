package gameoflife;
import java.util.ArrayList;
import java.util.Random;

import base.Grid;
import base.Location;
import base.Simulation;
import gameoflife.GameOfLifeCell.States;
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
public class GameOfLifeSimulation extends Simulation {
	private static final String dead = "Dead: ";
	private static final String alive = "Alive: ";
	private static final Random random = new Random();

	private CellType type;

	private int numberAlive;
	private int numberDead;
	private XYChart.Series deadLine;
	private XYChart.Series aliveLine;
	private int stepCount = 0;
	private double percentageAlive;
	
	
	private static final Text numDeadText = new Text(textPositionHorizontal,textPositionVertical,dead);
	private static final Text numAliveText = new Text(textPositionHorizontal,textPositionVertical + MARGIN_BOX_TOP,alive);
	private GameOfLifeGrid myGrid;
	private boolean[][] deadOrAlive;
	
	/**
	 * @param gridLength
	 */
	public GameOfLifeSimulation(int gridLength,double percentageAlive, CellType type) {
		super(gridLength, type);
		this.type = type;
		this.percentageAlive = percentageAlive;
	}
	
	/** 
	 * Initialize Game Of Life
	 */
	public Scene init(Stage s, CellType type) {
		super.init(s, type);
		return getMyScene();
	}

	/** 
	 * Set Up Grid for GOL Simulation
	 */
	public Grid instantiateGrid(){
		this.myGrid = new GameOfLifeGrid(getGridLength(), getCellSize(), getRootElement(),
				getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
		deadOrAlive = new boolean[getGridLength()][getGridLength()];
		return myGrid;
	}
	
	@Override
	public void createSeries(LineChart lineChart) {
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
	}
	
	/**
	 * Create the counter at top right that shows current amount of each cell present in simulation
	 */
	@Override
	public void createCellCounter() {
		Rectangle cellCounter = new Rectangle(
				SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + 2 * MARGIN_BOX_TOP,
				(DIMENSIONS_OF_CELL_COUNTER / 5), DIMENSIONS_OF_CELL_COUNTER * 3/2,
				DIMENSIONS_OF_CELL_COUNTER);
		cellCounter.setFill(Color.WHITE);
		cellCounter.setStyle(getCellCounterStyle());
		getRootElement().getChildren().add(cellCounter);
		numDeadText.setFill(Color.BLACK);
		numAliveText.setFill(Color.GRAY);
		updateText();
		getRootElement().getChildren().add(numDeadText);
		getRootElement().getChildren().add(numAliveText);
	}
	
	
	/**
	 * Updates graph with new values
	 */
	public void updateGraph() {
		deadLine.getData().add(new XYChart.Data(stepCount, numberAlive));
		aliveLine.getData().add(new XYChart.Data(stepCount, numberDead));
		updateText();
	}
	
	@Override
	public void step () {
		updateStateOfCells();
		updateCellUI();
		updateGraph();
		stepCount++;
	}

	/**
	 * Sets up grid, background boolean grid, and initial environment/first step of simulation
	 */
	public void setInitialEnvironment() {
		numberAlive = 0;
		numberDead = (int) Math.pow(getGridLength(), 2) - numberAlive;

		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				int cellLottery = random.nextInt(100);
				if(cellLottery <= (percentageAlive * 100)) {
					deadOrAlive[i][j] = true;
					numberAlive++;
				}
				else {
					numberDead++;
					deadOrAlive[i][j] = false;
				}
			}
		}

		updateCellUI();
		createGraph();
	}
	
	/**
	 * Every step cycle this runs and modifies states of simulation depending on rules
	 */
	public void updateCellUI() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				if(deadOrAlive[i][j] == true) {
					reviveCell(new Location(i, j));
				}
				else {
					killCell(new Location(i, j));
				}
			}
		}	
	}
	
	
	/**
	 * @param location
	 * @param aliveSurroundingCells
	 * Establish rules for each state and whether or not it should die in next step
	 */
	public void updateCurrentCellState(Location location, int aliveSurroundingCells) {
		if(isAlive(location)) {
			if((aliveSurroundingCells >= 3) || (aliveSurroundingCells < 2)) {
				numberDead++;
				numberAlive--;
				deadOrAlive[location.getRow()][location.getColumn()] = false;
			}
		}
		else {
			if((aliveSurroundingCells == 3)) {
				numberDead--;
				numberAlive++;
				deadOrAlive[location.getRow()][location.getColumn()] = true;
			}
		}
	}
	
	/**
	 * Creates on click ability to change classes depending on which cell was clicked
	 */
	public void updateStateOnClick() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
				if(manuallyModified(location)) {
					if(isAlive(location)) {
						noLongerModified(location);
						deadOrAlive[i][j] = true;
						numberDead--;
						numberAlive++;
					}
					else {
						noLongerModified(location);
						numberDead++;
						numberAlive--;
					}
				}
			}
		}
	}
	
	/**
	 * Iterates through and checks every location to see if nearby cells are doing anything
	 */
	public void updateStateOfCells() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				int aliveSurroundingCells = 0;
                Location location = new Location(i, j);
				aliveSurroundingCells += checkNearbyCells(location);
				updateCurrentCellState(location, aliveSurroundingCells);
			}
		}
	}
	

	
	/**
	 * @param location
	 */
	private void killCell(Location location) {
		myGrid.getCell(location).killCell();
		myGrid.updateCell(location);
	}
	
	/**
	 * @param location
	 */
	private void reviveCell(Location location) {
		myGrid.getCell(location).reviveCell();
		myGrid.updateCell(location);
	}
	
	/**
	 * @param location
	 * @return
	 */
	private boolean isAlive(Location location) {
		return (myGrid.getCell(location).getState() == States.ALIVE);
	}
	
	/**
	 * @param location
	 * @return
	 */
	private boolean manuallyModified(Location location) {
		return (myGrid.getCell(location).isManuallyModifiedByUser());
	}
	
	/**
	 * @param location
	 */
	private void noLongerModified(Location location) {
		myGrid.getCell(location).noLongerManuallyModified();
	}
	
	
	/**
	 * @param location
	 * @return
	 */
	private int checkNearbyCells(Location location) {
		int aliveNearbyCells = 0;
        ArrayList<Location> neighbors = myGrid.getAllNeighbors(location);
        for(int i = 0; i < neighbors.size(); i++){
            if(neighbors.get(i) != null && myGrid.getCell(neighbors.get(i)).getState() == States.ALIVE){
                aliveNearbyCells ++;
            }
        }
		return aliveNearbyCells;
	}
	
	/**
	 * Updates text in cell counter with new values
	 */
	private void updateText() {
		numDeadText.setText(dead + numberDead);
		numAliveText.setText(alive + numberAlive);
	}
}