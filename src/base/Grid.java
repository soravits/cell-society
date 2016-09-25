package base;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public abstract class Grid {
    private Cell[][] grid;
    private int rowLength;
    private Pane rootElement;
    private Simulation sim;
    private int initialX;
    private int initialY;
    private int sizeOfCell;
    
    public Grid(int rowLength,int sizeOfCell,Pane rootElement,int initialX,int initialY){
        this.grid = new Cell[rowLength][rowLength];
        this.rootElement = rootElement;
        this.rowLength = rowLength;           
        this.sizeOfCell = sizeOfCell;
        this.rootElement = rootElement;
        this.initialX = initialX;
        this.initialY = initialY;
    }
    
    public Cell[][] getGrid(){
        return grid;
    }

    public void setGrid (Cell[][] grid) {
        this.grid = grid;
    }

    public Pane getRootElement () {
        return rootElement;
    }

    public void setRootElement (Pane rootElement) {
        this.rootElement = rootElement;
    }

    public abstract void initializeGrid();

    public void setSimulationProfile(Simulation sim){
        this.sim = sim;;
    }

    public int getInitialX () {
        return initialX;
    }

    public void setInitialX (int initialX) {
        this.initialX = initialX;
    }

    public int getRowLength () {
        return rowLength;
    }

    public void setRowLength (int rowLength) {
        this.rowLength = rowLength;
    }

    public int getInitialY () {
        return initialY;
    }

    public void setInitialY (int initialY) {
        this.initialY = initialY;
    }

    public int getSizeOfCell () {
        return sizeOfCell;
    }

    public void setSizeOfCell (int sizeOfCell) {
        this.sizeOfCell = sizeOfCell;
    }
    
    public void setUpButtons(){
        // START SIMULATION BUTTON BELOW
        Button startSim = new Button("Start");
        startSim.setStyle(
                          "-fx-background-color: linear-gradient(#ff5400, #be1d00);" + 
                                  "-fx-background-radius: 20;" + 
                                  "-fx-text-fill: white;"               
                );
        startSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.startSimulation();
            }
        });
        startSim.setTranslateX(20);
        startSim.setTranslateY(200);
        rootElement.getChildren().add(startSim);

        // STEP SIMULATION BUTTON BELOW
        Button stepSim = new Button("Step");
        stepSim.setStyle(
                         "-fx-background-color: linear-gradient(#ff5400, #be1d00);" + 
                                 "-fx-background-radius: 20;" + 
                                 "-fx-text-fill: white;"               
                );
        stepSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.step();
            }
        });
        stepSim.setTranslateX(20);
        stepSim.setTranslateY(250);
        rootElement.getChildren().add(stepSim);

        // PAUSE SIMULATION BUTTON BELOW
        Button pauseSim = new Button("Pause");
        pauseSim.setStyle(
                          "-fx-background-color: linear-gradient(#ff5400, #be1d00);" + 
                                  "-fx-background-radius: 20;" + 
                                  "-fx-text-fill: white;"               
                );
        pauseSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.stopSimulation();
            }
        });
        pauseSim.setTranslateX(20);
        pauseSim.setTranslateY(300);
        rootElement.getChildren().add(pauseSim);

        // RESUME SIMULATION BUTTON BELOW
        Button resumeSim = new Button("Resume");
        resumeSim.setStyle(
                           "-fx-background-color: linear-gradient(#ff5400, #be1d00);" + 
                                   "-fx-background-radius: 20;" + 
                                   "-fx-text-fill: white;"         
                );
        resumeSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.resumeSimulation();
            }
        });
        resumeSim.setTranslateX(20);
        resumeSim.setTranslateY(350);
        rootElement.getChildren().add(resumeSim);
    }
}