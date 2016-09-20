package cellsociety_team04;

import javafx.animation.KeyFrame;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Simulation{
	public static final int SIMULATION_WINDOW_WIDTH = 800;
	public static final int SIMULATION_WINDOW_HEIGHT = 800;
	public static final int cellSize = 30;
	
	protected int gridLength;
	protected Stage stage;
	protected Scene myScene;
	protected Pane rootElement = new Pane();
	
	public Simulation(int gridLength){
		this.gridLength = gridLength;
	};
	
	public void checkAndModifySurroundingCells(){
		
	}
	
	public Scene init (Stage s) {
    	stage = s;
        myScene = new Scene(rootElement, SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE);  
        int lengthOfGridInPixels = gridLength * cellSize - cellSize;
        int marginOnSidesOfGrid = (SIMULATION_WINDOW_WIDTH - lengthOfGridInPixels)/2;
        int marginTop = SIMULATION_WINDOW_HEIGHT/8;
        
        Grid myGrid = new Grid(gridLength,cellSize,rootElement,marginOnSidesOfGrid,marginTop);
        

        return myScene;
    }
	
}
