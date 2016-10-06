package base;

import controller.MainMenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Brian, Soravit, Delia
 */
public abstract class Simulation {
	public static final int SIMULATION_WINDOW_WIDTH = 700;
	public static final int SIMULATION_WINDOW_HEIGHT = 700;
	public static final int GRID_DIMENSION = 500;
	public static final int DIMENSIONS_OF_CELL_COUNTER = 100;
	public static final int MARGIN_BOX_TOP = 20;
	public static final int LENGTH_OF_GRID_ADJUSTMENT = 100;
	public static final int STEP_DELAY_ADJUSTMENT = 100;

	public static final double LEFT_MARGIN_SIZE = 0.4;
	public static final double TOP_MARGIN_SIZE = 10;
	public static final double TOP_MARGIN_ADJUSTMENT = 4;
	public static final double HEX_SCREEN_WIDTH_ADJUSTMENT = 1.75;
	
	public static final double textPositionHorizontal = SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER)+ MARGIN_BOX_TOP * 3;
	public static final double textPositionVertical = 0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - 2 * MARGIN_BOX_TOP;


	public enum CellType {HEX, TRIANGLE, SQUARE};
	private CellType cellType;

	private int gridLength;
	private int cellSize;
	private int lengthOfGridInPixels;
	private int leftMargin;
	private int topMargin;
	private String cellCounterStyle = "-fx-background-radius: 8,7,6;" +
                        "-fx-background-insets: 0,1,2;" +
                        "-fx-text-fill: black;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";

	private Stage stage;
	private Scene myScene;
	private Pane rootElement;
	protected Timeline animation;

	/**
	 * @param myGridLength The length of the sides of the grid in cells
	 */
	public Simulation(int myGridLength, CellType type) {
		gridLength = myGridLength;
		cellType = type;
		rootElement = new Pane();
		cellSize = GRID_DIMENSION / gridLength;
		lengthOfGridInPixels = gridLength * cellSize - LENGTH_OF_GRID_ADJUSTMENT;
		leftMargin = (int) ((SIMULATION_WINDOW_WIDTH - lengthOfGridInPixels) * LEFT_MARGIN_SIZE);
		topMargin = (int) (SIMULATION_WINDOW_HEIGHT / TOP_MARGIN_SIZE + MARGIN_BOX_TOP * TOP_MARGIN_ADJUSTMENT);
	};
	

	/**
	 * @return The grid with which the simulation works
	 */
	public abstract Grid instantiateGrid();

	/**
	 * Sets up the initial objects/behavior of the simulation
	 */
	public abstract void setInitialEnvironment();
	
	/**
	 * The function that is constantly called throughout the simulation
	 */
	public abstract void step();

	/**
	 * @param lineChart
	 */
	public abstract void createSeries(LineChart lineChart);

	/**
	 *  Sets up new line in plot
	 */
	public void createCellCounter(){};
	
	/**
	 * Updates graph with new data
	 */
	public void updateGraph(){};

	/**
	 * Starts the animation object that keeps the simulation running
	 */
	public void startSimulation() {
		KeyFrame frame = new KeyFrame(Duration.millis(MainMenu.MILLISECOND_DELAY * STEP_DELAY_ADJUSTMENT),
				e -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	};

	/**
	 * Pauses the simulation
	 */
	public void stopSimulation () {
    	try {
    		animation.stop();
    	}
    	catch(NullPointerException e) {

    	}
	}

	/**
	 * Resumes the simulation
	 */
	public void resumeSimulation() {
		animation.play();
	}

	/**
	 * @param s The stage to be set
	 * @param type The shape of the cell
	 * @return The newly created scene
	 * 
	 * Initializes the scene with basic configurations depending on input
	 */
	public Scene init(Stage s, CellType type){
		setStage(s);
		setNewRootElement();
		int screenWidth = (int) ((type == CellType.HEX) ? (SIMULATION_WINDOW_WIDTH * HEX_SCREEN_WIDTH_ADJUSTMENT) : SIMULATION_WINDOW_WIDTH);
		setMyScene(new Scene(getRootElement(), screenWidth, SIMULATION_WINDOW_HEIGHT, Color.WHITE));
		Grid myGrid = instantiateGrid();
		myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
		myGrid.initializeGrid(type);
		myGrid.setUpButtons();
		myGrid.setSimulationProfile(this);
		setInitialEnvironment();
		return getMyScene();
	}
	
	/**
	 * Creates graph and puts it on scene
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
		final LineChart <Number, Number> lineChart = 
				new LineChart <Number,Number> (xAxis, yAxis);
		createSeries(lineChart);
        lineChart.setLayoutX(25);
        lineChart.setPrefSize(500, 100);
        lineChart.setLegendVisible(true);
        lineChart.setLegendSide(Side.RIGHT);
        getRootElement().getChildren().add(lineChart);

        createCellCounter();
	}
	
	/**
	 * @return The length of a side of the grid in cells
	 */
	public int getGridLength () {
		return gridLength;
	}

	/**
	 * @return The margin between the left side of the green and the left side of the grid
	 */
	public int getLeftMargin () {
		return leftMargin;
	}

	/**
	 * @param leftMargin The specified margin
	 */
	public void setLeftMargin (int leftMargin) {
		this.leftMargin = leftMargin;
	}

	/**
	 * @return The margin between the top side of the green and the top side of the grid
	 */
	public int getTopMargin () {
		return topMargin;
	}

	/**
	 * @param topMargin The specified margin
	 */
	public void setTopMargin (int topMargin) {
		this.topMargin = topMargin;
	}
	
	public String getCellCounterStyle(){
		return cellCounterStyle;
	}

	/**
	 * @return The scene of the simulation
	 */
	public Scene getMyScene () {
		return myScene;
	}

	/**
	 * @param myScene The JavaFX scene to be set
	 */
	public void setMyScene (Scene myScene) {
		this.myScene = myScene;
	}

	/**
	 * @return The JavaFX stage
	 */
	public Stage getStage () {
		return stage;
	}

	/**
	 * @param stage The specified JavaFX stage
	 */
	public void setStage (Stage stage) {
		this.stage = stage;
	}

	/**
	 * @return The JavaFX pane
	 */
	public Pane getRootElement () {
		return rootElement;
	}

	/**
	 * Creates a new JavaFX pane
	 */
	public void setNewRootElement() {
		this.rootElement = new Pane();
	}

	/**
	 * @return The size of a cell
	 */
	public int getCellSize () {
		return cellSize;
	}

	/**
	 * @param cellSize The desired size of a cell
	 */
	public void setCellSize (int cellSize) {
		this.cellSize = cellSize;
	}
}