package foragingants;

import base.Grid;
import base.Location;
import base.Simulation;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Soravit on 10/2/2016.
 */
public class ForagingAntsSimulation extends Simulation{

    private int simDuration;
    private int nestLocationRow;
    private int nestLocationColumn;
    private int foodSourceLocationRow;
    private int foodSourceLocationColumn;
    private int maxAntsPerSim;
    private int maxAntsPerLocation;
    private int antLifetime;
    private int numInitialAnts;
    private int antsBornPerStep;
    private double minPheromone;
    private double maxPheromone;
    private double evapRatio;
    private double diffusionRatio;
    private double K;
    private double N;

    private ForagingAntsGrid myGrid;
    private ArrayList<ForagingAnt> ants = new ArrayList<ForagingAnt>();

    /**
     * @param gridLength
     * @param type
     */
    public ForagingAntsSimulation(int gridLength, CellType type, int simDuration, int nestLocationRow, int nestLocationColumn,
                                int foodSourceLocationRow, int foodSourceLocationColumn, int maxAntsPerSim,
                                  int maxAntsPerLocation, int antLifetime, int numInitialAnts, int antsBornPerStep,
                                  double minPheromone, double maxPheromone, double evapRatio, double diffusionRatio) {
        super(gridLength, type);
        this.simDuration = simDuration;
        this.nestLocationRow = nestLocationRow;
        this.nestLocationColumn = nestLocationColumn;
        this.foodSourceLocationRow = foodSourceLocationRow;
        this.foodSourceLocationColumn = foodSourceLocationColumn;
        this.maxAntsPerSim = maxAntsPerSim;
        this.maxAntsPerLocation = maxAntsPerLocation;
        this.antLifetime = antLifetime;
        this.numInitialAnts = numInitialAnts;
        this.antsBornPerStep = antsBornPerStep;
        this.minPheromone = minPheromone;
        this.maxPheromone = maxPheromone;
        this.evapRatio = evapRatio;
        this.diffusionRatio = diffusionRatio;
    }

    @Override
    public Scene init(Stage s, CellType type) {
        setStage(s);
        makeNewRootElement();

        int screenWidth = SIMULATION_WINDOW_WIDTH;
        if(type == CellType.HEX){
            screenWidth *= 1.75;
        }

        setMyScene(new Scene(getRootElement(), screenWidth,
                SIMULATION_WINDOW_HEIGHT, Color.WHITE));
        setTopMargin(getTopMargin() + marginBoxTop * 4);
        this.myGrid = new ForagingAntsGrid(getGridLength(), getCellSize(), getRootElement(),
                getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this, new Location(nestLocationRow, nestLocationColumn),
                new Location(foodSourceLocationRow, foodSourceLocationColumn));
        myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
        myGrid.initializeGrid(type);
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        setInitialEnvironment();
        return getMyScene();
    }

    @Override
    public void setInitialEnvironment() {
        birthAnts(numInitialAnts);
        for(int i = 0; i < getGridLength(); i++){
            for(int j = 0; j < getGridLength(); j++){;
                myGrid.updateCell(i, j);
            }
        }
    }

    @Override
    public void step() {
        if(ants.size() <= maxAntsPerSim - 2) {
            birthAnts(2);
        }
        for(int i = 0; i < ants.size(); i++){
            forage(ants.get(i));
        }
        for(int i = 0; i < getGridLength(); i++){
            for(int j = 0; j < getGridLength(); j++){;
                myGrid.updateCell(i, j);
            }
        }
    }

    public void forage(ForagingAnt ant){
        if(ant.hasFood()){
            returnToNest(ant);
        }else{
            findFoodSource(ant);
        }
    }

    public Location findLocationWithMostFoodPheromones(ForagingAnt ant, int antDirection, int directionBeingChecked) {
        int row = ant.getRow();
        int col = ant.getColumn();
        double max = 0;
        ArrayList<Location> locations;
        if(directionBeingChecked == 1) {
            locations = myGrid.getNorthernNeighbors(ant.getLocation());
        }else{
            locations = myGrid.getSouthernNeighbors(ant.getLocation());
        }
        if(antDirection != directionBeingChecked){
            locations.add(myGrid.getWesternNeighbor(row, col));
            locations.add(myGrid.getEasternNeighbor(row, col));
        }
        for(int i = 0; i < locations.size(); i++) {
            if (locations.get(i)!= null) {
                ForagingAntsCell neighbor = myGrid.getCell(locations.get(i).getRow(), locations.get(i).getColumn());
                if (neighbor.getFoodPheromoneCount() >= max && neighbor.getAntCount() < maxAntsPerLocation) {
                    max = neighbor.getFoodPheromoneCount();
                }else{
                    locations.remove(i);
                }
            }else{
                locations.remove(i);
            }
        }

        if(locations.size() > 0) {
            Location randomLocation = locations.get(new Random().nextInt(locations.size()));
            ant.setOrientation(directionBeingChecked);
            return randomLocation;
        }else if(antDirection == directionBeingChecked){
            return findLocationWithMostFoodPheromones(ant, antDirection, 1 - antDirection);
        }
        return null;
    }

    public Location findLocationWithMostHomePheromones(ForagingAnt ant, int antDirection, int directionBeingChecked) {
        int row = ant.getRow();
        int col = ant.getColumn();
        double max = 0;
        ArrayList<Location> locations;
        if(directionBeingChecked == 1) {
            locations = myGrid.getNorthernNeighbors(ant.getLocation());
        }else{
            locations = myGrid.getSouthernNeighbors(ant.getLocation());
        }
        if(antDirection != directionBeingChecked){
            locations.add(myGrid.getWesternNeighbor(row, col));
            locations.add(myGrid.getEasternNeighbor(row, col));
        }
        for(int i = 0; i < locations.size(); i++) {
            if (locations.get(i)!= null) {
                ForagingAntsCell neighbor = myGrid.getCell(locations.get(i).getRow(), locations.get(i).getColumn());
                if (neighbor.getHomePheromoneCount() >= max && neighbor.getAntCount() < maxAntsPerLocation) {
                    max = neighbor.getHomePheromoneCount();
                }else{
                    locations.remove(i);
                }
            }else{
                locations.remove(i);
            }
        }

        if(locations.size() > 0) {
            Location randomLocation = locations.get(new Random().nextInt(locations.size()));
            ant.setOrientation(directionBeingChecked);
            return randomLocation;
        }else if(antDirection == directionBeingChecked){
            return findLocationWithMostHomePheromones(ant, antDirection, 1 - antDirection);
        }
        return null;
    }

    public void returnToNest(ForagingAnt ant){
            Location location = findLocationWithMostHomePheromones(ant, ant.getOrientation(), ant.getOrientation());
            if (location != null) {
                dropFoodPheromones(ant);
                ant.move(location);
                if (ant.getRow() == nestLocationRow && ant.getColumn() == nestLocationColumn) {
                    ant.setHasFood(false);
                }
            }
    }

    public void findFoodSource(ForagingAnt ant){
        Location location = findLocationWithMostFoodPheromones(ant, ant.getOrientation(), ant.getOrientation());
            if(location != null){
                dropHomePheromones(ant);
                ant.move(location);
                if(ant.getRow() == foodSourceLocationRow && ant.getColumn() == foodSourceLocationColumn){
                    ant.setHasFood(true);
                }
            }
    }

    public void dropHomePheromones(ForagingAnt ant){
        Location location = ant.getLocation();
        if (ant.getRow() == nestLocationRow && ant.getColumn() == nestLocationColumn) {
            myGrid.getCell(nestLocationRow, nestLocationColumn).setHomePheromoneCount(maxPheromone);
        }else{
            Location maxPheromones = findLocationWithMostHomePheromones(ant, ant.getOrientation(), ant.getOrientation());
            if(maxPheromones != null){
                double DES = myGrid.getCell(maxPheromones.getRow(), maxPheromones.getColumn()).getHomePheromoneCount() - 2;
                double D = DES - myGrid.getCell(location.getRow(), location.getColumn()).getHomePheromoneCount();
                if(D > 0){
                    myGrid.getCell(location.getRow(), location.getColumn()).setHomePheromoneCount(D);
                }
            }
        }
    }

    public void dropFoodPheromones(ForagingAnt ant){
        Location location = ant.getLocation();
        if (ant.getRow() == foodSourceLocationRow && ant.getColumn() == foodSourceLocationColumn) {
            myGrid.getCell(foodSourceLocationRow, foodSourceLocationColumn).setFoodPheromoneCount(maxPheromone);
        }else{
            Location maxPheromones = findLocationWithMostFoodPheromones(ant, ant.getOrientation(), ant.getOrientation());
            if(maxPheromones != null){
                double DES = myGrid.getCell(maxPheromones.getRow(), maxPheromones.getColumn()).getFoodPheromoneCount() - 2;
                double D = DES - myGrid.getCell(location.getRow(), location.getColumn()).getFoodPheromoneCount();
                if(D > 0){
                    myGrid.getCell(location.getRow(), location.getColumn()).setFoodPheromoneCount(D);
                }
            }
        }
    }

    public void diffuseHomePheromones(Location location, double diffRate){
        ForagingAntsCell gridCell = myGrid.getCell(location.getRow(), location.getColumn());
        double initialCount = gridCell.getHomePheromoneCount() * diffRate;
        ArrayList<Location> neighbors = myGrid.getAllNeighbors(location);
        Collections.shuffle(neighbors);
        for(int i = 0; i < neighbors.size(); i++){
            ForagingAntsCell neighbor = myGrid.getCell(neighbors.get(i).getRow(), neighbors.get(i).getColumn());
            if(neighbor.getHomePheromoneCount() <
                    gridCell.getHomePheromoneCount()){
                gridCell.setHomePheromoneCount(initialCount);
                neighbor.setHomePheromoneCount(neighbor.getHomePheromoneCount() + initialCount);
            }
        }
    }

    public void diffuseFoodPheromones(Location location, double diffRate){
        ForagingAntsCell gridCell = myGrid.getCell(location.getRow(), location.getColumn());
        double initialCount = gridCell.getFoodPheromoneCount() * diffRate;
        ArrayList<Location> neighbors = myGrid.getAllNeighbors(location);
        Collections.shuffle(neighbors);
        for(int i = 0; i < neighbors.size(); i++){
            ForagingAntsCell neighbor = myGrid.getCell(neighbors.get(i).getRow(), neighbors.get(i).getColumn());
            if(neighbor.getFoodPheromoneCount() <
                    gridCell.getFoodPheromoneCount()){
                gridCell.setFoodPheromoneCount(initialCount);
                neighbor.setFoodPheromoneCount(neighbor.getFoodPheromoneCount() + initialCount);
            }
        }
    }

    public void birthAnts(int count){
        for(int i = 0; i < count; i++){
            ForagingAnt ant = new ForagingAnt(nestLocationRow, nestLocationColumn, myGrid);
            myGrid.getCell(nestLocationRow, nestLocationColumn).incrementAntCount();
            ants.add(ant);
        }
    }
}
