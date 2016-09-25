package base;

import controller.MainMenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Brian, Soravit, Delia
 *
 */
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

    /**
     * @param gridLength
     */
    public Simulation(int gridLength){
        this.gridLength = gridLength;
        rootElement = new Pane();
        cellSize = GRID_DIMENSION / gridLength;
        lengthOfGridInPixels = gridLength * cellSize - 100;
        leftMargin = (SIMULATION_WINDOW_WIDTH - lengthOfGridInPixels)/2;
        topMargin = SIMULATION_WINDOW_HEIGHT/8;
    };

    /**
     * 
     */
    public void startSimulation(){
        KeyFrame frame = new KeyFrame(Duration.millis(MainMenu.MILLISECOND_DELAY * 100),
                                      e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();    
    };
    

    /**
     * 
     */
    public void stopSimulation () {
        animation.stop();
    }

    /**
     * 
     */
    public void resumeSimulation(){
        animation.play();
    }

    /**
     * @return
     */
    public int getGridLength () {
        return gridLength;
    }

    /**
     * @param gridLength
     */
    public void setGridLength (int gridLength) {
        this.gridLength = gridLength;
    }

    /**
     * @return
     */
    public int getLeftMargin () {
        return leftMargin;
    }

    /**
     * @param leftMargin
     */
    public void setLeftMargin (int leftMargin) {
        this.leftMargin = leftMargin;
    }

    /**
     * @return
     */
    public int getTopMargin () {
        return topMargin;
    }

    /**
     * @param topMargin
     */
    public void setTopMargin (int topMargin) {
        this.topMargin = topMargin;
    }

    /**
     * @return
     */
    public Scene getMyScene () {
        return myScene;
    }

    /**
     * @param myScene
     */
    public void setMyScene (Scene myScene) {
        this.myScene = myScene;
    }

    /**
     * @return
     */
    public Stage getStage () {
        return stage;
    }

    /**
     * @param stage
     */
    public void setStage (Stage stage) {
        this.stage = stage;
    }

    /**
     * @return
     */
    public Pane getRootElement () {
        return rootElement;
    }

    /**
     * @param rootElement
     */
    public void setRootElement (Pane rootElement) {
        this.rootElement = rootElement;
    }

    /**
     * @return
     */
    public int getCellSize () {
        return cellSize;
    }

    /**
     * @param cellSize
     */
    public void setCellSize (int cellSize) {
        this.cellSize = cellSize;
    }	

    /**
     * @param s
     * @return
     */
    public abstract Scene init(Stage s);
    
    /**
     * 
     */
    public abstract void setInitialEnvironment();
    
    /**
     * 
     */
    public abstract void step();
}
