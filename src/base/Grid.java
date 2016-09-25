package base;

import gameoflife.GameOfLifeCell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Grid {
    protected Cell[][] grid;
    protected int rowLength;
    protected Pane rootElement;
    protected Simulation sim;
    protected int initialX;

    public void setGrid (Cell[][] grid) {
        this.grid = grid;
    }

    protected int initialY;
    protected int sizeOfCell;

    public Grid(int rowLength,int sizeOfCell,Pane rootElement,int initialX,int initialY){
        this.grid = new Cell[rowLength][rowLength];
        this.rootElement = rootElement;
        this.rowLength = rowLength;		
        this.sizeOfCell = sizeOfCell;
        this.initialX = initialX;
        this.initialY = initialY;
    }

    public abstract void initializeGrid();

    public void setSimulationProfile(Simulation sim){
        this.sim = sim;;
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