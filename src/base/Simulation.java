package base;

import controller.MainMenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
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
	public static final int dimensionsOfCellCounterBox = 100;
	public static final int marginBoxTop = 20;

	public enum CellType {HEX, TRIANGLE, SQUARE};
	private CellType cellType;

	private int gridLength;
	private int cellSize;
	private int lengthOfGridInPixels; 
	private int leftMargin;
	private int topMargin;

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
		 lengthOfGridInPixels = gridLength * cellSize - 100;
		 leftMargin = (SIMULATION_WINDOW_WIDTH - lengthOfGridInPixels) / 5 * 2;
		 topMargin = SIMULATION_WINDOW_HEIGHT / 10;
	 };

	 /**
	  * Starts the animation object that keeps the simulation running
	  */
	 public void startSimulation() {
		 KeyFrame frame = new KeyFrame(Duration.millis(MainMenu.MILLISECOND_DELAY * 100),
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
		 animation.stop();
	 }

	 /**
	  * Resumes the simulation
	  */
	 public void resumeSimulation() {
		 animation.play();
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

	 /**
	  * @param s The stage to be set
      * @param type The shape of the cell
	  * @return The newly created scene
	  */
	 public Scene init(Stage s, CellType type){
		 setStage(s);
		 setNewRootElement();
		 int screenWidth = SIMULATION_WINDOW_WIDTH;
		 if(type == CellType.HEX){
			 screenWidth *= 1.75;
		 }
		 setMyScene(new Scene(getRootElement(), screenWidth, SIMULATION_WINDOW_HEIGHT, Color.WHITE));
		 setTopMargin(getTopMargin() + marginBoxTop * 4);
		 Grid myGrid = instantiateGrid();
         myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
         myGrid.initializeGrid(type);
         myGrid.setUpButtons();
         myGrid.setSimulationProfile(this);
		 setInitialEnvironment();
		 return getMyScene();
	 }

	 public abstract Grid instantiateGrid();

	 /**
	  * Sets up the initial objects/behavior of the simulation
	  */
	 public abstract void setInitialEnvironment();

	 /**
	  * The function that is constantly called throughout the simulation
	  */
	 public abstract void step();
}