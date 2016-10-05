package waterworld;
import java.util.ArrayList;
import java.util.Collections;

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
import waterworld.WaTorWorldCell.State;
/**
 * @author Soravit
 *
 */
public class WaTorWorldSimulation extends Simulation {
	private static final String fish = "Fish: ";
	private static final String shark = "Shark: ";
	private static final String sea = "Sea: ";
	private static final Text numSea = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + MARGIN_BOX_TOP * 3,
			0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - 3 * MARGIN_BOX_TOP, sea);
	private static final Text numShark = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + MARGIN_BOX_TOP * 3,
			0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - 2 * MARGIN_BOX_TOP, shark);
	private static final Text numFish = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + MARGIN_BOX_TOP * 3,
			0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - MARGIN_BOX_TOP, fish);
	private WaTorWorldGrid myGrid;
	private double fracFish;
	private double fracShark;
	private int fishBreedTime;
	private int sharkBreedTime;
	private int starveTime;
	private int fishCount;
	private int seaCount;
	private int sharkCount;
	private XYChart.Series fishSeries;
	private XYChart.Series sharkSeries;
	private XYChart.Series seaSeries;
	private int stepCount = 0;

	/**
	 * @param gridLength
	 * @param fracFish
	 * @param fracShark
	 * @param fishBreedTime
	 * @param sharkBreedTime
	 * @param starveTime
	 */
	public WaTorWorldSimulation(int gridLength, double fracFish, double fracShark, 
			int fishBreedTime, int sharkBreedTime, int starveTime, CellType type) {
		super(gridLength,type);
		this.fracFish = fracFish;
		this.fracShark = fracShark;
		this.fishBreedTime = fishBreedTime;
		this.sharkBreedTime = sharkBreedTime;
		this.starveTime = starveTime;
	}

	public Scene init (Stage s,CellType type) {
		super.init(s, type);
		return getMyScene();
	}

	public Grid instantiateGrid(){
		this.myGrid = new WaTorWorldGrid(getGridLength(), getCellSize(), getRootElement(),
				getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
		return myGrid;
	}

	@Override
	public void setInitialEnvironment() {
		createGraph();
		sharkCount = 0;
		fishCount = 0;
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				Location location = new Location(i, j);
				double rand = Math.random();
				if(rand < fracFish) {
					breedFish(location);
				}
				else if(rand > fracFish 
						&& rand < fracFish + fracShark) {
					breedShark(location);
				}
				else {
					killCell(location);
				}
			}
		}
		seaCount = (int) Math.pow(getGridLength(), 2) - sharkCount - fishCount;
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
	public void manuallyModifyStateOfGrid() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				Location location = new Location(i, j);
				if(manuallyModified(location)) {
					System.out.println(i + " " + j);
					noLongerModified(location);
					if((myGrid.getCell(location)).getState() == State.SHARK) {
						sharkCount++;
						fishCount--;
					} 
					else if (myGrid.getCell(location).getState() == State.FISH) {
						fishCount++;
						seaCount--;
					}
					else {
						sharkCount--;
						killCell(new Location(i, j));
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	public void updateState() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				Location location = new Location(i, j);
				if(myGrid.getCell(location).getState() == State.SHARK) {
					updateShark(location);
				} 
				else if (myGrid.getCell(location).getState() == State.FISH) {
					updateFish(location);
				}
			}
		}
	}

	private void updateShark(Location location) {
		Location currLocation = new Location(location.getRow(), location.getColumn());
		ArrayList <Location> fish = new ArrayList <Location>();
		myGrid.getCell(location).decrementBreedTime();
		myGrid.getCell(location).decrementStarveTime();
		if(myGrid.getNorthernNeighbor(location) != null
				&& myGrid.getCell(myGrid.getNorthernNeighbor(location)).getState() == State.FISH) {
			fish.add(myGrid.getNorthernNeighbor(location));
		}
		if(myGrid.getSouthernNeighbor(location) != null
				&& myGrid.getCell(myGrid.getSouthernNeighbor(location)).getState() == State.FISH) {
			fish.add(myGrid.getSouthernNeighbor(location));
		}
		if(myGrid.getEasternNeighbor(location) != null
				&& myGrid.getCell(myGrid.getEasternNeighbor(location)).getState() == State.FISH) {
			fish.add(myGrid.getEasternNeighbor(location));
		}
		if(myGrid.getWesternNeighbor(location) != null
				&& myGrid.getCell(myGrid.getWesternNeighbor(location)).getState() == State.FISH) {
			fish.add(myGrid.getWesternNeighbor(location));
		}
		if(fish.size() == 0) {
			Location loc = getRandomEmptyNeighbor(location);
			if(loc != null) {
				moveFish(location, loc);
				currLocation.setRow(loc.getRow());
				currLocation.setCol(loc.getColumn());
			}
		}
		else if(fish.size() == 1) {
			killCell(fish.get(0));
			fishCount--;
			myGrid.getCell(currLocation).setStarveTime(starveTime);
		} 
		else {
			Collections.shuffle(fish);
			killCell(fish.get(0));
			fishCount--;
			myGrid.getCell(currLocation).setStarveTime(starveTime);
		}
		if(myGrid.getCell(currLocation).getStarveTime() == 0) {
			killCell(currLocation);
		} 
		else if(myGrid.getCell(currLocation).getBreedTime() == 0) {
			Location loc = getRandomEmptyNeighbor(currLocation);
			if(loc != null){
				breedShark(loc);
				seaCount--;
			}
			myGrid.getCell(currLocation).setBreedTime(sharkBreedTime);
		}
	}

	private void updateFish(Location location) {
		//Cell[][] grid = myGrid.getGrid();        
		myGrid.getCell(location).setBreedTime(myGrid.getCell(location).getBreedTime() - 1);
		Location loc = getRandomEmptyNeighbor(location);
		if(loc != null) {
			moveFish(location, loc);
			location = loc;
		}
		if(myGrid.getCell(location).getBreedTime() == 0) {
			Location l = getRandomEmptyNeighbor(location);
			if(l != null) {
				breedFish(l);
				seaCount--;
			}
			myGrid.getCell(location).setBreedTime(fishBreedTime);
		}
	}

	private Location getRandomEmptyNeighbor(Location location) {
		ArrayList <Location> locations = new ArrayList <Location>();
		//Cell[][] grid = myGrid.getGrid();        
		if(myGrid.getNorthernNeighbor(location) != null
				&& myGrid.getCell(myGrid.getNorthernNeighbor(location)).getState() == State.EMPTY) {
			locations.add(myGrid.getNorthernNeighbor(location));
		}
		if(myGrid.getSouthernNeighbor(location) != null
				&& myGrid.getCell(myGrid.getSouthernNeighbor(location)).getState() == State.EMPTY) {
			locations.add(myGrid.getSouthernNeighbor(location));
		}
		if(myGrid.getWesternNeighbor(location) != null
				&& myGrid.getCell(myGrid.getWesternNeighbor(location)).getState() == State.EMPTY) {
			locations.add(myGrid.getWesternNeighbor(location));
		}
		if(myGrid.getEasternNeighbor(location) != null
				&& myGrid.getCell(myGrid.getEasternNeighbor(location)).getState() == State.EMPTY) {
			locations.add(myGrid.getEasternNeighbor(location));
		}
		
		Collections.shuffle(locations);
		if(locations.size() > 0) {
			return locations.get(0);
		}
		return null;
	}
	
	/**
	 * @param source
	 * @param dest
	 */
	private void moveFish(Location source, Location dest) {
		myGrid.getCell(dest).setState(myGrid.getCell(source).getState());
		myGrid.getCell(dest).setBreedTime(myGrid.getCell(source).getBreedTime());
		killCell(source);
	}

	private void moveShark(Location source, Location dest) {
		moveShark(source, dest);
		myGrid.getCell(dest).setStarveTime(myGrid.getCell(source).getStarveTime());
	}

	private void breedFish(Location location) {
		fishCount++;
		myGrid.getCell(location).setState(State.FISH);
		myGrid.getCell(location).setBreedTime(fishBreedTime);
	}

	private void breedShark(Location location) {
		sharkCount++;
		myGrid.getCell(location).setState(State.SHARK);
		myGrid.getCell(location).setStarveTime(starveTime);
		myGrid.getCell(location).setBreedTime(sharkBreedTime);
	}

	private void killCell(Location location) {
		seaCount = (int) Math.pow(getGridLength(), 2) - sharkCount - fishCount;
		myGrid.getCell(location).setState(State.EMPTY);
		myGrid.getCell(location).setStarveTime(-1);
		myGrid.getCell(location).setBreedTime(-1);
	}

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
				new LineChart <Number,Number> (xAxis, yAxis);
		fishSeries = new XYChart.Series();
		fishSeries.setName("Fish");
		sharkSeries = new XYChart.Series();
		sharkSeries.setName("Shark");
		seaSeries = new XYChart.Series();
		seaSeries.setName("Sea");
		
		//populating the series with data
		lineChart.getData().add(fishSeries);
		lineChart.getData().add(sharkSeries);
		lineChart.getData().add(seaSeries);
		
		lineChart.setLayoutX(25);
		lineChart.setPrefSize(500, 100);
		lineChart.setLegendVisible(true);
		lineChart.setLegendSide(Side.RIGHT);
		getRootElement().getChildren().add(lineChart);
		
		Rectangle cellCounter = new Rectangle(
				SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER)
				+ 2 * MARGIN_BOX_TOP, (DIMENSIONS_OF_CELL_COUNTER / 5),
				DIMENSIONS_OF_CELL_COUNTER * 3 / 2, DIMENSIONS_OF_CELL_COUNTER);
		cellCounter.setFill(Color.WHITE);
		cellCounter.setStyle(
				"-fx-background-radius: 8,7,6;" + 
						"-fx-background-insets: 0,1,2;" +
						"-fx-text-fill: black;" +
						"-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
				);
		getRootElement().getChildren().add(cellCounter);
		numSea.setFill(Color.BLUE);
		numShark.setFill(Color.GOLD);
		numFish.setFill(Color.GREEN);
		updateText();
		getRootElement().getChildren().add(numSea);
		getRootElement().getChildren().add(numShark);
		getRootElement().getChildren().add(numFish);
	}
	
	/**
	 * 
	 */
	public void updateGraph() {
		fishSeries.getData().add(new XYChart.Data(stepCount, fishCount));
		sharkSeries.getData().add(new XYChart.Data(stepCount, sharkCount));
		seaSeries.getData().add(new XYChart.Data(stepCount, seaCount));
		updateText();
	}
	
	/**
	 * 
	 */
	private void updateText() {
		if(fishCount < 0) {
			fishCount = 0;
		}
		numSea.setText(sea + seaCount);
		numShark.setText(shark + sharkCount);
		numFish.setText(fish + fishCount);
	}
	
	@Override
	public void step () {
		updateState();
		updateGraph();
		stepCount++;
	}

	@Override
	public void createSeries(LineChart lineChart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createCellCounter() {
		// TODO Auto-generated method stub
		
	}
}