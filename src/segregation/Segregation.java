package segregation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import spreadingoffire.SpreadingOfFireGrid;
import controller.MainMenu;
import base.Cell;
import base.Simulation;
import gameoflife.GameOfLifeCell;
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
		// TODO Auto-generated method stub
		animation.stop();
	}

	@Override
	public Scene init(Stage s) {
        stage = s;
        myScene = new Scene(rootElement, SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE);  
        int lengthOfGridInPixels = gridLength * Cell.cellSize - 100;
        int marginOnSidesOfGrid = (SIMULATION_WINDOW_WIDTH - lengthOfGridInPixels)/2;
        int marginTop = SIMULATION_WINDOW_HEIGHT/8;

        this.myGrid = new SegregationGrid(gridLength,Cell.cellSize,rootElement,marginOnSidesOfGrid,marginTop);
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
		int blue = 0;
		int red = 0; 
		int empty = 0;
		for(int i = 0; i < gridLength; i++){
			for(int j = 0; j < gridLength; j++){
				int cellLottery = random.nextInt(100);
				//40
				if(cellLottery <= (percEmpty * 100)){ //if the cell is white
					cellType = 0;
					empty++;
				}
				//
				else if (cellLottery <= ((percEmpty + percA) * 100)){ //if the cell is blue
					cellType = 1;
					blue++;
				}
				else{
					cellType = 2;
					red++;
				}
				myGrid.updateCell(i, j, cellType);
			}
		}
		System.out.println("Number empty = " + empty);
		System.out.println("Number blue = " + blue);
		System.out.println("Number red = " + red);
	}
	
	public void setSatisfiedGrid(){
		for(int i = 0; i < gridLength; i++){
			for(int j = 0; j < gridLength; j++){
				cellSatisfied[i][j] = setSatisfiedState(i, j);
//				System.out.println(setSatisfiedState(i, j));
			}
		}
	}
	
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
		if(i < gridLength - 1 && myGrid.getCell(i + 1, j) != null && !myGrid.getCell(i + 1, j).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i + 1, j).getColor().equals(color))
				sameColor++;
		}
		//checks west
		if(j > 0 && myGrid.getCell(i, j - 1) != null && !myGrid.getCell(i, j - 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i, j - 1).getColor().equals(color))
				sameColor++;
		}
		//checks east
		if(j < gridLength - 1 && myGrid.getCell(i, j + 1) != null && !myGrid.getCell(i, j + 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i, j + 1).getColor().equals(color))
				sameColor++;
		}
		//checks northwest
		if(i > 0 && j > 0 && myGrid.getCell(i - 1, j - 1) != null && !myGrid.getCell(i - 1, j - 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i - 1, j - 1).getColor().equals(color))
				sameColor++;
		}
		//checks southwest
		if(i < gridLength - 1 && j > 0 && myGrid.getCell(i + 1, j - 1) != null && !myGrid.getCell(i + 1, j - 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i + 1, j - 1).getColor().equals(color))
				sameColor++;
		}
		//checks northeast
		if(i > 0 && j < gridLength - 1 && myGrid.getCell(i - 1, j + 1) != null && !myGrid.getCell(i - 1, j + 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i - 1, j + 1).getColor().equals(color))
				sameColor++;
		}
		//checks southeast
		if(i < gridLength - 1 && j < gridLength - 1 && myGrid.getCell(i + 1, j + 1) != null && !myGrid.getCell(i + 1, j + 1).getColor().equals(Color.WHITE)){
			totalNeighbors++;
			if(myGrid.getCell(i + 1, j + 1).getColor().equals(color))
				sameColor++;
		}
		
		if((double) sameColor / (double) totalNeighbors >= satisfyThresh) 
			return 1;
		
		return 2;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		setSatisfiedGrid();
		updateState();
		if(numberOfUnsatisfied == 0) animation.stop();
	}
	
	//don't know if we should implement it so that a dissatisfied cell can leave its position and immediately have
	//that spot filled by another previously dissatisfied cell. Or can we make it so that dissatisfied cells can only
	//go to spots that are currently empty and not inhabited by another dissatisfied cell?
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
//					System.out.println("add unsatisfied");
				}
			}
		}
//		System.out.println("Original emptyspots = " + emptySpots.size());
//		System.out.println("Original unhappy spots = " + unhappySpots.size());
		
		//randomly place dissatisfied cells into empty spots
		for(int i = 0; i < unhappySpots.size(); i++){
			int destinationIndex = random.nextInt(emptySpots.size());
			myGrid.switchCells(unhappySpots.get(i), emptySpots.get(destinationIndex));
			emptySpots.remove(destinationIndex);
		}
		
		numberOfUnsatisfied = unhappySpots.size();
	}

}
