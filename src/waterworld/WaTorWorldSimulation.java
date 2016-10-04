package waterworld;
import java.util.ArrayList;
import java.util.Collections;
import base.Cell;
import base.Grid;
import base.Location;
import base.Simulation;
import base.Simulation.CellType;
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
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - 3 * marginBoxTop, sea);
	private static final Text numShark = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - 2 * marginBoxTop, shark);
	private static final Text numFish = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - marginBoxTop, fish);
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

	@Override
	public Scene init (Stage s,CellType type) {
		setStage(s);
		makeNewRootElement();

		int screenWidth = SIMULATION_WINDOW_WIDTH;
		if(type == CellType.HEX){
			screenWidth *= 1.75;
		}

		setMyScene(new Scene(getRootElement(), screenWidth,
				SIMULATION_WINDOW_HEIGHT, Color.WHITE));
		setTopMargin(getTopMargin() + marginBoxTop * 4);
		this.myGrid = new WaTorWorldGrid(getGridLength(), getCellSize(), getRootElement(),
				getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
		myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
		myGrid.initializeGrid(type);
		myGrid.setUpButtons();
		myGrid.setSimulationProfile(this);
		setInitialEnvironment();
		createGraph();
		return getMyScene();
	}

	@Override
	public void setInitialEnvironment() {
		sharkCount = 0;
		fishCount = 0;
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				double rand = Math.random();
				if(rand < fracFish) {
					breedFish(i, j);
				}
				else if(rand > fracFish 
						&& rand < fracFish + fracShark) {
					breedShark(i, j);
				}
				else {
					killCell(i, j);
				}
			}
		}
		seaCount = (int) Math.pow(getGridLength(), 2) - sharkCount - fishCount;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean manuallyModified(int row, int col) {
		return (myGrid.getCell(row, col).isManuallyModified());
	}

	/**
	 * @param row
	 * @param col
	 */
	private void noLongerModified(int row, int col) {
		myGrid.getCell(row, col).noLongerManuallyModified();
	}

	/**
	 * 
	 */
	public void manuallyModifyStateOfGrid() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				if(manuallyModified(i, j)) {
					System.out.println(i + " " + j);
					noLongerModified(i, j);
					if((myGrid.getCell(i, j)).getState() == State.SHARK) {
						sharkCount++;
						fishCount--;
					} 
					else if (myGrid.getCell(i, j).getState() == State.FISH) {
						fishCount++;
						seaCount--;
					}
					else {
						sharkCount--;
						killCell(i, j);
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
				if(myGrid.getCell(i, j).getState() == State.SHARK) {
					updateShark(i, j);
				} 
				else if (myGrid.getCell(i, j).getState() == State.FISH) {
					updateFish(i, j);
				}
			}
		}
	}

	/**
	 * @param row
	 * @param col
	 */
	public void updateShark(int row, int col) {
		Location currLocation = new Location(row, col);
		//Cell[][] grid = myGrid.getGrid();
		ArrayList <Location> fish = new ArrayList <Location>();
		myGrid.getCell(row, col).decrementBreedTime();
		myGrid.getCell(row, col).decrementStarveTime();
		if(myGrid.getNorthernNeighbor(row, col) != null 
				&& myGrid.getCell(myGrid.getNorthernNeighbor(row, col).getRow(),
				myGrid.getNorthernNeighbor(row, col).getColumn()).getState() == State.FISH) {
			fish.add(myGrid.getNorthernNeighbor(row, col));
		}
		if(myGrid.getSouthernNeighbor(row, col) != null 
				&& myGrid.getCell(myGrid.getSouthernNeighbor(row, col).getRow(),
				myGrid.getSouthernNeighbor(row, col).getColumn()).getState() == State.FISH) {
			fish.add(myGrid.getSouthernNeighbor(row, col));
		}
		if(myGrid.getEasternNeighbor(row, col) != null 
				&& myGrid.getCell(myGrid.getEasternNeighbor(row, col).getRow(),
				myGrid.getEasternNeighbor(row, col).getColumn()).getState() == State.FISH) {
			fish.add(myGrid.getEasternNeighbor(row, col));
		}
		if(myGrid.getWesternNeighbor(row, col) != null 
				&& myGrid.getCell(myGrid.getWesternNeighbor(row, col).getRow(),
				myGrid.getWesternNeighbor(row, col).getColumn()).getState() == State.FISH) {
			fish.add(myGrid.getWesternNeighbor(row, col));
		}
		if(fish.size() == 0) {
			Location loc = getRandomEmptyNeighbor(row, col);
			if(loc != null) {
				moveFish(currLocation, loc); 
				currLocation.setRow(loc.getRow());
				currLocation.setColumn(loc.getColumn());
			}
		}
		else if(fish.size() == 1) {
			killCell(fish.get(0).getRow(), fish.get(0).getColumn());
			fishCount--;
			myGrid.getCell(row,col).setStarveTime(starveTime);
		} 
		else {
			Collections.shuffle(fish);
			killCell(fish.get(0).getRow(), fish.get(0).getColumn());
			fishCount--;
			myGrid.getCell(row, col).setStarveTime(starveTime);
		}
		if(myGrid.getCell(currLocation.getRow(), currLocation.getColumn()).getStarveTime() == 0) {
			killCell(currLocation.getRow(), currLocation.getColumn());
		} 
		else if(myGrid.getCell(currLocation.getRow(), currLocation.getColumn()).getBreedTime() == 0) {
			Location loc = getRandomEmptyNeighbor(currLocation.getRow(), currLocation.getColumn());
			if(loc != null){
				breedShark(loc.getRow(), loc.getColumn());
				seaCount--;
			}
			myGrid.getCell(currLocation.getRow(), currLocation.getColumn()).setBreedTime(sharkBreedTime);
		}
	}
	
	/**
	 * @param row
	 * @param col
	 */
	public void updateFish(int row, int col) {
		//Cell[][] grid = myGrid.getGrid();        
		myGrid.getCell(row, col).setBreedTime(myGrid.getCell(row, col).getBreedTime() - 1);
		Location loc = getRandomEmptyNeighbor(row, col);
		if(loc != null) {
			moveFish(new Location(row, col), loc);
			row = loc.getRow();
			col = loc.getColumn();
		}
		if(myGrid.getCell(row, col).getBreedTime() == 0) {
			Location l = getRandomEmptyNeighbor(row, col);
			if(l != null) {
				breedFish(l.getRow(), l.getColumn());
				seaCount--;
			}
			myGrid.getCell(row,col).setBreedTime(fishBreedTime);
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getRandomEmptyNeighbor(int row, int col) {
		ArrayList <Location> locations = new ArrayList <Location>();
		//Cell[][] grid = myGrid.getGrid();        
		if(myGrid.getNorthernNeighbor(row, col) != null 
				&& myGrid.getCell(myGrid.getNorthernNeighbor(row, col).getRow(),
				myGrid.getNorthernNeighbor(row, col).getColumn()).getState() == State.EMPTY) {
			locations.add(myGrid.getNorthernNeighbor(row, col));
		}
		if(myGrid.getSouthernNeighbor(row, col) != null 
				&& myGrid.getCell(myGrid.getSouthernNeighbor(row, col).getRow(),
				myGrid.getSouthernNeighbor(row, col).getColumn()).getState() == State.EMPTY) {
			locations.add(myGrid.getSouthernNeighbor(row, col));
		}
		if(myGrid.getEasternNeighbor(row, col) != null 
				&& myGrid.getCell(myGrid.getEasternNeighbor(row, col).getRow(),
				myGrid.getEasternNeighbor(row, col).getColumn()).getState() == State.EMPTY) {
			locations.add(myGrid.getEasternNeighbor(row, col));
		}
		if(myGrid.getWesternNeighbor(row, col) != null 
				&& myGrid.getCell(myGrid.getWesternNeighbor(row, col).getRow(),
				myGrid.getWesternNeighbor(row, col).getColumn()).getState() == State.EMPTY) {
			locations.add(myGrid.getWesternNeighbor(row, col));
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
	public void moveFish(Location source, Location dest) {
		myGrid.getCell(dest.getRow(), dest.getColumn()).setState(myGrid.getCell(source.getRow(),
				source.getColumn()).getState());
		myGrid.getCell(dest.getRow(), dest.getColumn()).setBreedTime(myGrid.getCell(source.getRow(),
				source.getColumn()).getBreedTime());
		killCell(source.getRow(),source.getColumn());
	}
	
	/**
	 * @param source
	 * @param dest
	 */
	public void moveShark(Location source, Location dest) {
		moveShark(source, dest);
		myGrid.getCell(dest.getRow(), dest.getColumn()).setStarveTime(myGrid.getCell(source.getRow(),
				source.getColumn()).getStarveTime());
	}
	
	/**
	 * @param row
	 * @param col
	 */
	public void breedFish(int row, int col) {
		fishCount++;
		myGrid.getCell(row, col).setState(State.FISH);
		myGrid.getCell(row, col).setBreedTime(fishBreedTime);
	}
	
	/**
	 * @param row
	 * @param col
	 */
	public void breedShark(int row, int col) {
		sharkCount++;
		myGrid.getCell(row, col).setState(State.SHARK);
		myGrid.getCell(row, col).setStarveTime(starveTime);
		myGrid.getCell(row, col).setBreedTime(sharkBreedTime);
	}
	
	/**
	 * @param row
	 * @param col
	 */
	public void killCell(int row, int col) {
		seaCount = (int) Math.pow(getGridLength(), 2) - sharkCount - fishCount;
		myGrid.getCell(row, col).setState(State.EMPTY);
		myGrid.getCell(row, col).setStarveTime(-1);
		myGrid.getCell(row, col).setBreedTime(-1);
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
				new LineChart <Number,Number> (xAxis, yAxis);
		fishSeries = new XYChart.Series();
		fishSeries.setName("Fish");
		sharkSeries = new XYChart.Series();
		sharkSeries.setName("Shark");
		seaSeries = new XYChart.Series();
		seaSeries.setName("Sea");
		//populating the series with data
		//series.getData().add(new XYChart.Data(1, 23));
		lineChart.getData().add(fishSeries);
		lineChart.getData().add(sharkSeries);
		lineChart.getData().add(seaSeries);
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
}