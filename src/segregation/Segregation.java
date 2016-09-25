package segregation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import controller.MainMenu;
import base.Cell;
import base.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Segregation extends Simulation{
	private SegregationGrid myGrid;
    private Timeline animation;
    private int[][] cellSatisfied;
    private double satisfyThresh, percA, percB, percEmpty;
    private static final int EMPTY = 0;
    private static final int SATISFIED = 1;
    private static final int UNSATISFIED = 2;
    private int numberOfUnsatisfied;
    private int totalSteps = 0;
    private Random random = new Random();
	
	public Segregation(int gridLength, double threshold, double percentA, 
			double percentB, double percentEmpty) {
		super(gridLength);
		this.satisfyThresh = threshold;
		this.percA = percentA * (1 - percentEmpty);
		this.percB = percentB;
		this.percEmpty = percentEmpty;
	}
	
	@Override
	public void startSimulation() {
        KeyFrame frame = new KeyFrame(Duration.millis(MainMenu.MILLISECOND_DELAY * 100),
                e -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}


	@Override
	public void stopSimulation() {
		animation.stop();
	}

	@Override
	public Scene init(Stage s) {
        stage = s;
        myScene = new Scene(rootElement, SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE); 
        this.myGrid = new SegregationGrid(gridLength, cellSize, rootElement, marginOnSidesOfGrid, marginTop);
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        cellSatisfied = new int[gridLength][gridLength];
        setInitialEnvironment();
        setSatisfiedGrid();

        return myScene;
	}
	
	public void setInitialEnvironment(){
		int cellType;
		for(int i = 0; i < gridLength; i++){
			for(int j = 0; j < gridLength; j++){
				int cellLottery = random.nextInt(100);
				if(cellLottery <= (percEmpty * 100)){
					cellType = 0;
				}
				else if (cellLottery <= ((percEmpty + percA) * 100)){
					cellType = 1;
				}
				else{
					cellType = 2;
				}
				myGrid.updateCell(i, j, cellType);
			}
		}
	}
	
	public void setSatisfiedGrid(){
		for(int i = 0; i < gridLength; i++){
			for(int j = 0; j < gridLength; j++){
				cellSatisfied[i][j] = setSatisfiedState(i, j);
			}
		}
	}
	
	
	/**
	 * MUST CHANGE THIS
	 * WORST METHOD EVER BUT IT WORKS
	 * @param i
	 * @param j
	 * @return
	 */
	public int setSatisfiedState(int i, int j){
		Cell current = myGrid.getCell(i, j);
		Paint color = current.getColor();
		int sameColor = 0;
		int totalNeighbors = 0;
		
		//if the cell is uninhabited, can't be satisfied or unsatisfied
		if(color.equals(Color.WHITE)){
			return EMPTY;
		}
		
		//checks north
		if(i > 0 && myGrid.getCell(i - 1, j) != null && !myGrid.getCell(i - 1, j).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i - 1, j).getColor().equals(color))
				sameColor++;
		}
		//checks south
		if(i < gridLength - 1 && myGrid.getCell(i + 1, j) != null 
				&& !myGrid.getCell(i + 1, j).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i + 1, j).getColor().equals(color))
				sameColor++;
		}
		//checks west
		if(j > 0 && myGrid.getCell(i, j - 1) != null 
				&& !myGrid.getCell(i, j - 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i, j - 1).getColor().equals(color))
				sameColor++;
		}
		//checks east
		if(j < gridLength - 1 && myGrid.getCell(i, j + 1) != null 
				&& !myGrid.getCell(i, j + 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i, j + 1).getColor().equals(color))
				sameColor++;
		}
		//checks northwest
		if(i > 0 && j > 0 && myGrid.getCell(i - 1, j - 1) != null 
				&& !myGrid.getCell(i - 1, j - 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i - 1, j - 1).getColor().equals(color))
				sameColor++;
		}
		//checks southwest
		if(i < gridLength - 1 && j > 0 && myGrid.getCell(i + 1, j - 1) != null 
				&& !myGrid.getCell(i + 1, j - 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i + 1, j - 1).getColor().equals(color))
				sameColor++;
		}
		//checks northeast
		if(i > 0 && j < gridLength - 1 && myGrid.getCell(i - 1, j + 1) != null 
				&& !myGrid.getCell(i - 1, j + 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i - 1, j + 1).getColor().equals(color))
				sameColor++;
		}
		//checks southeast
		if(i < gridLength - 1 && j < gridLength - 1 && myGrid.getCell(i + 1, j + 1) != null 
				&& !myGrid.getCell(i + 1, j + 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i + 1, j + 1).getColor().equals(color))
				sameColor++;
		}
		
		if((double) sameColor / (double) totalNeighbors >= satisfyThresh) 
			return SATISFIED;
		
		return UNSATISFIED;
	}

	@Override
	public void step() {
		totalSteps++;
		setSatisfiedGrid();
		updateState();
		if(numberOfUnsatisfied == 0) animation.stop();
	}
	
	public void updateState(){
		//make a list of empty spots
		ArrayList<Point> emptySpots = new ArrayList<>();
		ArrayList<Point> unhappySpots = new ArrayList<>();
		for(int i = 0; i < gridLength; i++){
			for(int j = 0; j < gridLength; j++){
				//make a list of dissatisfied cells
				
				if (cellSatisfied[i][j] == EMPTY || cellSatisfied[i][j] == UNSATISFIED){
					emptySpots.add(new Point(i, j));
				}
				if(cellSatisfied[i][j] == UNSATISFIED){
					unhappySpots.add(new Point(i, j));
				}
			}
		}
		
		//randomly place dissatisfied cells into empty spots
		for(int i = 0; i < unhappySpots.size(); i++){
			int destinationIndex = random.nextInt(emptySpots.size());
			myGrid.switchCells(unhappySpots.get(i), emptySpots.get(destinationIndex));
			emptySpots.remove(destinationIndex);
		}
		
		numberOfUnsatisfied = unhappySpots.size();
		myGrid.updateStats(totalSteps, numberOfUnsatisfied);
	}

}
