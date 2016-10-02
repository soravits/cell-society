package base;

import base.Simulation.CellType;
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
    private int columnLength;
    private int initialX;
    private int initialY;
    private int sizeOfCell;
    private String buttonFill = "-fx-background-color: linear-gradient(#0079b3, #00110e);" + 
            "-fx-background-radius: 20;" + 
            "-fx-text-fill: white;";
    private String overButton = "-fx-background-color: linear-gradient(#00110e, #0079b3);" + 
            "-fx-background-radius: 20;" + 
            "-fx-text-fill: white;";

    /**
     * @param rowLength
     * @param sizeOfCell
     * @param rootElement
     * @param initialX
     * @param initialY
     */
    public Grid(int gridLength,int sizeOfCell,Pane rootElement,int initialX,int initialY) {
        this.rootElement = rootElement;
        this.rowLength = gridLength;        
        this.columnLength = gridLength;
        this.sizeOfCell = sizeOfCell;
        this.rootElement = rootElement;
        this.initialX = initialX;
        this.initialY = initialY;
        this.grid = new Cell[columnLength][rowLength];
    }

    public int getRowLength () {
        return rowLength;
    }

    public int getColumnLength () {
        return columnLength;
    }


    /**
     * @return
     */
    public Pane getRootElement () {
        return rootElement;
    }

    /**
     * 
     */
    public abstract void initializeGrid(CellType type);

    /**
     * @param sim
     */
    public void setSimulationProfile(Simulation sim) {
        this.sim = sim;
    }

    public Cell getCell(int row, int col){
        return grid[row][col];
    }

    public void setCell(int row, int col, Cell c){
        grid[row][col] = c;
    }

    /**
     * @return
     */
    public int getInitialX () {
        return initialX;
    }

    /**
     * @return
     */
    public int getInitialY () {
        return initialY;
    }

    /**
     * @return
     */
    public int getSizeOfCell () {
        return sizeOfCell;
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
        startSim.setOnMouseEntered(e -> mouseIn(startSim));
        startSim.setOnMouseExited(e -> mouseOut(startSim));
        startSim.setTranslateX(x);
        startSim.setTranslateY(y);

        return startSim;
    }
    
    private void mouseIn(Button b){
    	b.setStyle(overButton);
    }
    
    private void mouseOut(Button b){
    	b.setStyle(buttonFill);
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