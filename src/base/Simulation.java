package base;

import controller.MainMenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class Simulation{
    public static final int SIMULATION_WINDOW_WIDTH = 700;
    public static final int SIMULATION_WINDOW_HEIGHT = 700;
    protected static final int GRID_DIMENSION = 500;

    protected int gridLength;
    protected int totalCells = gridLength * gridLength;
    protected int cellSize;
    protected int lengthOfGridInPixels; 
    protected int leftMargin;
    protected int topMargin; 
    protected Stage stage;
    protected Scene myScene;
    protected Pane rootElement = new Pane();
    private Timeline animation;

    public Simulation(int gridLength){
        this.gridLength = gridLength;
        cellSize = GRID_DIMENSION / gridLength;
        lengthOfGridInPixels = gridLength * cellSize - 100;
        leftMargin = (SIMULATION_WINDOW_WIDTH - lengthOfGridInPixels)/2;
        topMargin = SIMULATION_WINDOW_HEIGHT/8;

    };

    public void startSimulation(){
        KeyFrame frame = new KeyFrame(Duration.millis(MainMenu.MILLISECOND_DELAY * 100),
                                      e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();    
    };

    public void stopSimulation () {
        animation.stop();
    }

    public void resumeSimulation(){
        animation.play();
    }

    public abstract void setInitialEnvironment();
    public abstract void step();
    public abstract Scene init(Stage s);	
}
