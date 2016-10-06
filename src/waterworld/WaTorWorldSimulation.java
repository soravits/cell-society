// This entire file is part of my masterpiece.
// Soravit Sophastienphong
/*
This is the code for the Predator Prey Simulation, which instantiates a WaTorWorldGrid and implements it to
define the behavior of the simulation from start to finish. I think that the code is well designed because
it well encapsulates private variables and methods, implements and redefines methods from its superclass, updates
the states of cells but not their graphical representations, and has good readability with regard to naming, comments,
layout, and code style.
 */

package waterworld;
import java.util.ArrayList;
import java.util.Collections;

import base.Grid;
import base.Location;
import base.Simulation;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import waterworld.WaTorWorldCell.State;
/**
 * @author Soravit
 */
public class WaTorWorldSimulation extends Simulation {
    private WaTorWorldGrid myGrid;
    private double fracFish;
    private double fracShark;
    private int fishBreedTime;
    private int sharkBreedTime;
    private int starveTime;
    private XYChart.Series fishSeries;
    private XYChart.Series sharkSeries;
    private int stepCount;
    private int waterCount;
    private int fishCount;
    private int sharkCount;

    /**
     * @param gridLength The length of a side of the grid in cells
     * @param fracFish The fraction of the grid that filled with fish
     * @param fracShark The fraction of the grid that filled with sharks
     * @param fishBreedTime The number of steps it takes for a fish to breed
     * @param sharkBreedTime The number of steps it takes for a shark to breed
     * @param starveTime The number of steps it takes for a shark to starve
     * @param type The shape of a cell in the grid
     */
    public WaTorWorldSimulation(int gridLength, double fracFish, double fracShark,
                                int fishBreedTime, int sharkBreedTime, int starveTime, CellType type) {
        super(gridLength,type);
        this.fracFish = fracFish;
        this.fracShark = fracShark;
        this.fishBreedTime = fishBreedTime;
        this.sharkBreedTime = sharkBreedTime;
        this.starveTime = starveTime;
    }

    /**
     * @param s The stage on which the scene will be set
     * @param type The shape of a cell
     * @return The initialized scene
     */
    @Override
    public Scene init (Stage s, CellType type) {
        super.init(s, type);
        return getMyScene();
    }

    /**
     * Instantiates a new WaTorWorld grid
     * @return The instantiated grid
     */
    @Override
    public Grid instantiateGrid(){
        this.myGrid = new WaTorWorldGrid(getGridLength(), getCellSize(), getRootElement(),
                getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
        return myGrid;
    }

    /**
     * Initializes the grid with fish, shark, and water
     */
    @Override
    public void setInitialEnvironment() {
        createGraph();
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
                double rand = Math.random();
                if(rand < fracFish) {
                    breedFish(location);
                }
                else if(rand > fracFish && rand < fracFish + fracShark) {
                    breedShark(location);
                }
                else {
                    setAsEmptyCell(location);
                }
            }
        }
    }

    /**
     * Creates the series to display on the graph
     * @param lineChart The chart on which to put the series
     */
    @Override
    public void createSeries(LineChart lineChart) {
        fishSeries = new XYChart.Series();
        fishSeries.setName("Fish");
        sharkSeries = new XYChart.Series();
        sharkSeries.setName("Shark");

        //populating the series with data
        lineChart.getData().add(fishSeries);
        lineChart.getData().add(sharkSeries);
    }

    /**
     *
     */
    @Override
    public void updateGraph() {
        fishSeries.getData().add(new XYChart.Data(stepCount, fishCount));
        sharkSeries.getData().add(new XYChart.Data(stepCount, sharkCount));
    }

    /**
     * Continually updates the grid and the graph
     */
    @Override
    public void step () {
        updateState();
        updateGraph();
        stepCount++;
    }

    private void updateState() {
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
                if(myGrid.getCell(location).getState() == State.SHARK) {
                    updateShark(location);
                }
                else if (myGrid.getCell(location).getState() == State.FISH) {
                    updateFish(location);
                }
            }
        }
    }

    private void updateFish(Location location) {
        //Decrement the time until the next breed
        myGrid.getCell(location).setBreedTime(myGrid.getCell(location).getBreedTime() - 1);
        Location emptyNeighbor = getRandomEmptyNeighbor(location);
        if(emptyNeighbor != null) {
            //Move fish to random location
            moveFish(location, emptyNeighbor);
            location = emptyNeighbor;
        }
        if(myGrid.getCell(location).getBreedTime() == 0) {
            emptyNeighbor = getRandomEmptyNeighbor(location);
            if(emptyNeighbor != null) {
                breedFish(emptyNeighbor);
                myGrid.getCell(location).setBreedTime(fishBreedTime);
            }
        }
    }

    private void updateShark(Location location) {
        ArrayList <Location> neighbors = myGrid.getAllNeighbors(location);
        ArrayList <Location> edibleFish = new ArrayList <Location>();
        myGrid.getCell(location).decrementBreedTime();
        myGrid.getCell(location).decrementStarveTime();
        for(int i = 0; i < neighbors.size(); i++){
            if(neighbors.get(i) != null && myGrid.getCell(neighbors.get(i)).getState() == State.FISH){
                //Keep track of surrounding fish
                edibleFish.add(neighbors.get(i));
            }
        }
        if(edibleFish.size() == 0) {
            Location emptyNeighbor = getRandomEmptyNeighbor(location);
            if(emptyNeighbor != null) {
                //Move to a random spot because there's no fish to eat
                moveShark(location, emptyNeighbor);
                location = emptyNeighbor;
            }
        } else {
            //Eat a random fish
            Collections.shuffle(edibleFish);
            setAsEmptyCell(edibleFish.get(0));
            fishCount--;
            myGrid.getCell(location).setStarveTime(starveTime);
        }

        //Kill the shark if it starves
        if(myGrid.getCell(location).getStarveTime() == 0) {
            setAsEmptyCell(location);
            sharkCount--;
        }
        else if(myGrid.getCell(location).getBreedTime() == 0) {
            Location emptyNeighbor = getRandomEmptyNeighbor(location);
            if(emptyNeighbor != null){
                breedShark(emptyNeighbor);
            }
            myGrid.getCell(location).setBreedTime(sharkBreedTime);
        }
    }

    private Location getRandomEmptyNeighbor(Location location) {
        ArrayList <Location> locations = myGrid.getAllCardinalNeighbors(location);
        for(int i = 0; i < locations.size(); i++){
            if(locations.get(i) != null && !myGrid.getCell(locations.get(i)).getState().equals(State.EMPTY)){
                locations.remove(i);
            }
        }
        Collections.shuffle(locations);
        return (locations.size() > 0) ? locations.get(0) : null;
    }

    private void moveFish(Location source, Location dest) {
        myGrid.getCell(dest).setState(myGrid.getCell(source).getState());
        myGrid.getCell(dest).setBreedTime(myGrid.getCell(source).getBreedTime());
        setAsEmptyCell(source);
    }

    private void moveShark(Location source, Location dest) {
        moveFish(source, dest);
        myGrid.getCell(dest).setStarveTime(myGrid.getCell(source).getStarveTime());
    }

    private void breedFish(Location location) {
        myGrid.getCell(location).setState(State.FISH);
        myGrid.getCell(location).setBreedTime(fishBreedTime);
        fishCount++;
    }

    private void breedShark(Location location) {
        myGrid.getCell(location).setState(State.SHARK);
        myGrid.getCell(location).setStarveTime(starveTime);
        myGrid.getCell(location).setBreedTime(sharkBreedTime);
        sharkCount++;
    }

    private void setAsEmptyCell(Location location) {
        WaTorWorldCell cell = myGrid.getCell(location);
        myGrid.getCell(location).setState(State.EMPTY);
        myGrid.getCell(location).setStarveTime(-1);
        myGrid.getCell(location).setBreedTime(-1);
    }
}