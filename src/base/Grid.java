package base;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @author Soravit, Brian, Delia
 *define this as a superclass, and then make squarem, triangular,m and hesagonal subclasses that inherit
 *avoid intialiting a 2d grid
 *
 */
public abstract class Grid {
    private Pane rootElement;
    private Simulation sim;
    private Cell[][] grid;
    private int rowLength;
    private int initialX;
    private int initialY;
    private int sizeOfCell;
    private String buttonFill = "-fx-background-color: linear-gradient(#0079b3, #00110e);" + 
            "-fx-background-radius: 20;" + 
            "-fx-text-fill: white;";
    
    /**
     * @param rowLength
     * @param sizeOfCell
     * @param rootElement
     * @param initialX
     * @param initialY
     */
    public Grid(int rowLength,int sizeOfCell,Pane rootElement,int initialX,int initialY) {
        this.grid = new Cell[rowLength][rowLength];
        this.rootElement = rootElement;
        this.rowLength = rowLength;           
        this.sizeOfCell = sizeOfCell;
        this.rootElement = rootElement;
        this.initialX = initialX;
        this.initialY = initialY;
    }
    
    /**Improve this? How should we design this better??
     * @return
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * @param grid
     */
    public void setGrid (Cell[][] grid) {
        this.grid = grid;
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
     * 
     */
    public abstract void initializeGrid();

    /**
     * @param sim
     */
    public void setSimulationProfile(Simulation sim) {
        this.sim = sim;;
    }

    /**
     * @return
     */
    public int getInitialX () {
        return initialX;
    }

    /**
     * @param initialX
     */
    public void setInitialX (int initialX) {
        this.initialX = initialX;
    }

    /**
     * @return
     */
    public int getRowLength () {
        return rowLength;
    }

    /**
     * @param rowLength
     */
    public void setRowLength (int rowLength) {
        this.rowLength = rowLength;
    }

    /**
     * @return
     */
    public int getInitialY () {
        return initialY;
    }

    /**
     * @param initialY
     */
    public void setInitialY (int initialY) {
        this.initialY = initialY;
    }

    /**
     * @return
     */
    public int getSizeOfCell () {
        return sizeOfCell;
    }

    /**
     * @param sizeOfCell
     */
    public void setSizeOfCell (int sizeOfCell) {
        this.sizeOfCell = sizeOfCell;
    }
    
    public void setBackground(int width, int height) {
    	Image background = new Image(getClass().getClassLoader().
    			getResourceAsStream("BackgroundCellSoc.jpg")); 
		ImageView backgroundImageMainScreen = new ImageView(background);
		backgroundImageMainScreen.setFitWidth(width + 50);
		backgroundImageMainScreen.setFitHeight(height);
		rootElement.getChildren().add(backgroundImageMainScreen);
    }
    
    private Button createSimButton(String text, int x, int y) {
    	Button startSim = new Button(text);
        startSim.setStyle(buttonFill);
        startSim.setTranslateX(x);
        startSim.setTranslateY(y);
        
        return startSim;
    }
    
    /**
     * 
     */
    public void setUpButtons() {
        // START SIMULATION BUTTON BELOW
    	
        Button startSim = createSimButton("Start", 20, 200);
        startSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.startSimulation();
            }
        });
        rootElement.getChildren().add(startSim);

        // STEP SIMULATION BUTTON BELOW
        Button stepSim = createSimButton("Step", 20, 250);
        stepSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.step();
            }
        });
        rootElement.getChildren().add(stepSim);

        // PAUSE SIMULATION BUTTON BELOW
        Button pauseSim = createSimButton("Pause", 20, 300);
        pauseSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.stopSimulation();
            }
        });
        rootElement.getChildren().add(pauseSim);

        // RESUME SIMULATION BUTTON BELOW
        Button resumeSim = createSimButton("Resume", 20, 350);
        resumeSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.resumeSimulation();
            }
        });
        rootElement.getChildren().add(resumeSim);
    }
}