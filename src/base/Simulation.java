package base;

import java.util.Arrays;

import javafx.animation.KeyFrame;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class Simulation{
	public static final int SIMULATION_WINDOW_WIDTH = 800;
	public static final int SIMULATION_WINDOW_HEIGHT = 800;

	protected int gridLength;
	protected int totalCells = gridLength * gridLength;
	protected Stage stage;
	protected Scene myScene;
	protected Pane rootElement = new Pane();
		
	public Simulation(int gridLength){
		this.gridLength = gridLength;
	};
	public abstract void step();
	public abstract void startSimulation();	
	public abstract void stopSimulation();
	public abstract Scene init (Stage s);	
}
