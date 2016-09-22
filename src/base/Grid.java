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
        Button startSim = new Button("Start Simulation");
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
        startSim.setTranslateX(Simulation.SIMULATION_WINDOW_WIDTH/3);
        startSim.setTranslateY(Simulation.SIMULATION_WINDOW_HEIGHT/3 * 2);
        rootElement.getChildren().add(startSim);

        // END SIMULATION BUTTON BELOW
        Button endSim = new Button("Stop Simulation");
        endSim.setStyle(
                        "-fx-background-color: linear-gradient(#ff5400, #be1d00);" + 
                                "-fx-background-radius: 20;" + 
                                "-fx-text-fill: white;"		
                );
        endSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sim.stopSimulation();
            }
        });
        endSim.setTranslateX(Simulation.SIMULATION_WINDOW_WIDTH/3 * 2);
        endSim.setTranslateY(Simulation.SIMULATION_WINDOW_HEIGHT/3 * 2);
        rootElement.getChildren().add(endSim);
    }
}
