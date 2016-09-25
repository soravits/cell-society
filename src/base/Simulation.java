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
    public static final int GRID_DIMENSION = 500;

    private int gridLength;
    private int cellSize;
    private int lengthOfGridInPixels; 
    private int leftMargin;
    private int topMargin; 
    private Stage stage;
    private Scene myScene;
    private Pane rootElement;
    private Timeline animation;

    public Simulation(int gridLength){
        this.gridLength = gridLength;
        rootElement = new Pane();
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

    public int getGridLength () {
        return gridLength;
    }

    public void setGridLength (int gridLength) {
        this.gridLength = gridLength;
    }

    public int getLeftMargin () {
        return leftMargin;
    }

    public void setLeftMargin (int leftMargin) {
        this.leftMargin = leftMargin;
    }

    public int getTopMargin () {
        return topMargin;
    }

    public void setTopMargin (int topMargin) {
        this.topMargin = topMargin;
    }

    public Scene getMyScene () {
        return myScene;
    }

    public void setMyScene (Scene myScene) {
        this.myScene = myScene;
    }

    public Stage getStage () {
        return stage;
    }

    public void setStage (Stage stage) {
        this.stage = stage;
    }

    public Pane getRootElement () {
        return rootElement;
    }

    public void setRootElement (Pane rootElement) {
        this.rootElement = rootElement;
    }

    public int getCellSize () {
        return cellSize;
    }

    public void setCellSize (int cellSize) {
        this.cellSize = cellSize;
    }	

    public abstract Scene init(Stage s);
    public abstract void setInitialEnvironment();
    public abstract void step();
}
