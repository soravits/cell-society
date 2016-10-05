package gameoflife;
import java.lang.reflect.Array;
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
	private static final Text numDeadText = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox)+ marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - 2 * marginBoxTop, dead);
	private static final Text numAliveText = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox)+ marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - marginBoxTop, alive);
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
	/* (non-Javadoc)
	 * @see base.Simulation#init(javafx.stage.Stage)
	 */
	public Scene init(Stage s, CellType type) {
		super.init(s, type);
		return getMyScene();
	}

	public Grid instantiateGrid(){
		this.myGrid = new GameOfLifeGrid(getGridLength(), getCellSize(), getRootElement(),
				getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
		deadOrAlive = new boolean[getGridLength()][getGridLength()];
		return myGrid;
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
		final LineChart<Number,Number> lineChart = 
				new LineChart <Number,Number> (xAxis, yAxis);
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
		Rectangle cellCounter = new Rectangle(
				SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + 2 * marginBoxTop, 
				(dimensionsOfCellCounterBox / 5), dimensionsOfCellCounterBox * 3/2,
				dimensionsOfCellCounterBox);
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
	
	/**
	 * 
	 */
	private void updateText() {
		numDeadText.setText(dead + numberDead);
		numAliveText.setText(alive + numberAlive);
	}
	/**
	 * 
	 */
	public void updateGraph() {
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
	 * 
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
	 * @param aliveSurroundingCells
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
	 * 
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
	 * 
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
}