package foragingants;

import base.Grid;
import base.Location;
import base.Simulation;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author Soravit
 */
public class ForagingAntsSimulation extends Simulation {
    //get rid of unused vars
    private int simDuration;
    private Location nestLocation;
    private Location foodSourceLocation;
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

    private int antPopulation = 0;
    private int totalSteps = 0;
    private static final String pop = "Ant population: ";
    private XYChart.Series populationLine;
    private static final Text numAntsText = new Text(
            SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + MARGIN_BOX_TOP * 3,
            0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - 3 * MARGIN_BOX_TOP, pop);

    private ForagingAntsGrid myGrid;
    private ArrayList<ForagingAnt> ants = new ArrayList<ForagingAnt>();

    /**
     * @param gridLength The length of a side of the grid
     * @param type The shape of a cell
     * @param simDuration The duration of the simulation in steps
     * @param nestLocationRow The row that the nest is in
     * @param nestLocationColumn The column that the nest is in
     * @param foodSourceLocationRow The row that the food source is in
     * @param foodSourceLocationColumn The column that the food source is in
     * @param maxAntsPerSim The max number of ants in the simulation
     * @param maxAntsPerLocation The max number of ants per location
     * @param antLifetime The lifetime of an ant in steps
     * @param numInitialAnts The number of initial ants
     * @param antsBornPerStep The number of ants born per step
     * @param minPheromone The minimum amount of pheromone per cell
     * @param maxPheromone The max amount of pheromone per cell
     * @param evapRatio The rate at which pheromone evaporates
     * @param diffusionRatio The rate at which pheromone diffuses
     */
    public ForagingAntsSimulation(int gridLength, CellType type, int simDuration,
                                  int nestLocationRow, int nestLocationColumn,
                                  int foodSourceLocationRow, int foodSourceLocationColumn, int maxAntsPerSim,
                                  int maxAntsPerLocation, int antLifetime, int numInitialAnts, int antsBornPerStep,
                                  double minPheromone, double maxPheromone, double evapRatio, double diffusionRatio) {

        super(gridLength, type);
        this.simDuration = simDuration;
        this.nestLocation = new Location(nestLocationRow, nestLocationColumn);
        this.foodSourceLocation = new Location(foodSourceLocationRow, foodSourceLocationColumn);
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

    /**
     *
     * @param s The stage to be set
     * @param type The shape of the cell
     * @return The current scene
     */
    @Override
    public Scene init(Stage s, CellType type) {
        super.init(s, type);
        return getMyScene();
    }

    /**
     * @return An instantiated grid of type ForagingAntsGrid
     */
    @Override
    public Grid instantiateGrid(){
        this.myGrid = new ForagingAntsGrid(getGridLength(), getCellSize(), getRootElement(),
                getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this,
                nestLocation, foodSourceLocation);
        return myGrid;
    }

    /**
     * Spawns initial ants and updates every cell
     */
    @Override
    public void setInitialEnvironment() {
    	createGraph();
        birthAnts(numInitialAnts);
        for(int i = 0; i < getGridLength(); i++){
            for(int j = 0; j < getGridLength(); j++){
                Location location = new Location(i, j);
                myGrid.updateCell(location);
                updatePheromones(location);
            }
        }
    }

    private void updateAnts(){
    	antPopulation = ants.size();
        for(int i = 0; i < ants.size(); i++){
            ForagingAnt ant = ants.get(i);
            if(ant.getLife() == 0){
                ants.remove(i);
                myGrid.getCell(ant.getLocation()).decrementAntCount();
            }else{
                forage(ant);
                ant.decrementLife();
            }
        }
    }

    private void updateCells(){
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                Location cellLocation = new Location(i, j);
                myGrid.updateCell(cellLocation);
            }
        }
    }

    /**
     * Updates every ant and cell in the grid
     */
    @Override
    public void step() {
    	totalSteps++;
        if(ants.size() <= maxAntsPerSim - antsBornPerStep) {
            birthAnts(antsBornPerStep);
        }
        updateAnts();
        updateCells();
        updateGraph();
    }

    private void forage(ForagingAnt ant) {
        if(ant.hasFood()) {
            returnToNest(ant);
        }
        else {
            findFoodSource(ant);
        }
    }

    private Location findLocationWithMostFoodPheromones(ForagingAnt ant, int orientation) {
        double maxPheromones = -1;
        Location maxLocation = null;
        ArrayList<Location> neighbors = myGrid.getAllNeighbors(ant.getLocation());
        ArrayList<Location> options = new ArrayList<Location>();
        for(int i = orientation - 1; i < orientation + 2; i++) {
            int index = i;
            ForagingAntsCell neighbor;
            if(i == -1) {
                index = 7;
            }
            else if(i == 8) {
                index = 0;
            }
            if(neighbors.get(index) != null) {
                neighbor = myGrid.getCell(neighbors.get(index));
                if (neighbor.getFoodPheromoneCount() >= maxPheromones) {
                    maxPheromones = neighbor.getFoodPheromoneCount();
                    options.add(neighbors.get(index));
                }
            }
        }
        if(options.size() > 0) {
            int rand = new Random().nextInt(options.size());
            ant.setOrientation(neighbors.indexOf(options.get(rand)));
            return options.get(new Random().nextInt(options.size()));
        }
        else {
            options.clear();
            maxPheromones = -1;
            for(int i = 0; i < neighbors.size(); i++) {
                ForagingAntsCell neighbor;
                if(neighbors.get(i) != null) {
                    neighbor = myGrid.getCell(neighbors.get(i));
                    if (neighbor.getFoodPheromoneCount() >= maxPheromones) {
                        maxPheromones = neighbor.getFoodPheromoneCount();
                        options.add(neighbors.get(i));
                    }
                }
            }
        }
        if(options.size() > 0) {
            int rand = new Random().nextInt(options.size());
            ant.setOrientation(neighbors.indexOf(options.get(rand)));
            return options.get(new Random().nextInt(options.size()));
        }
        return maxLocation;
    }

    private Location findLocationWithMostHomePheromones(ForagingAnt ant, int orientation) {
        double maxPheromones = -1;
        Location maxLocation = null;
        ArrayList<Location> neighbors = myGrid.getAllNeighbors(ant.getLocation());
        ArrayList<Location> options = new ArrayList<Location>();
        for(int i = orientation - 1; i < orientation + 2; i++) {
            int index = i;
            ForagingAntsCell neighbor;
            if(i == -1) {
                index = 7;
            }
            else if(i == 8) {
                index = 0;
            }
            if(neighbors.get(index) != null) {
                neighbor = myGrid.getCell(neighbors.get(index));
                if (neighbor.getHomePheromoneCount() >= maxPheromones) {
                    maxPheromones = neighbor.getFoodPheromoneCount();
                    options.add(neighbors.get(index));
                }
            }
        }
        if(options.size() > 0) {
            int rand = new Random().nextInt(options.size());
            ant.setOrientation(neighbors.indexOf(options.get(rand)));
            return options.get(new Random().nextInt(options.size()));
        }
        else {
            options.clear();
            maxPheromones = -1;
            for(int i = 0; i < neighbors.size(); i++) {
                ForagingAntsCell neighbor;
                if(neighbors.get(i) != null) {
                    neighbor = myGrid.getCell(neighbors.get(i));
                    if (neighbor.getHomePheromoneCount() >= maxPheromones) {
                        maxPheromones = neighbor.getFoodPheromoneCount();
                        options.add(neighbors.get(i));
                    }
                }
            }
        }
        if(options.size() > 0) {
            int rand = new Random().nextInt(options.size());
            ant.setOrientation(neighbors.indexOf(options.get(rand)));
            return options.get(new Random().nextInt(options.size()));
        }
        return maxLocation;
    }

    private void returnToNest(ForagingAnt ant) {
        Location location = findLocationWithMostHomePheromones(ant, ant.getOrientation());
        if (location != null) {
            dropFoodPheromones(ant);
            ant.move(location);
            if (ant.getLocation() == nestLocation) {
                ant.setHasFood(false);
            }
        }
    }

    private void findFoodSource(ForagingAnt ant) {
        Location location = findLocationWithMostFoodPheromones(ant, ant.getOrientation());
        if(location != null) {
            dropHomePheromones(ant);
            ant.move(location);
            if(ant.getLocation() == foodSourceLocation) {
                ant.setHasFood(true);
            }
        }
    }

    private void dropHomePheromones(ForagingAnt ant) {
        Location location = ant.getLocation();
        if (ant.getLocation() == nestLocation) {
            myGrid.getCell(nestLocation).setHomePheromoneCount(maxPheromone);
        }
        else {
            Location maxPheromones = findLocationWithMostHomePheromones(ant, ant.getOrientation());
            if(maxPheromones != null) {
                double DES = myGrid.getCell(maxPheromones).getHomePheromoneCount() - 2;
                double D = DES - myGrid.getCell(location).getHomePheromoneCount();
                if(D > 0) {
                    myGrid.getCell(location).setHomePheromoneCount(D);
                }
            }
        }
    }

    private void dropFoodPheromones(ForagingAnt ant) {
        Location location = ant.getLocation();
        if (ant.getLocation() == foodSourceLocation) {
            myGrid.getCell(foodSourceLocation).setFoodPheromoneCount(maxPheromone);
        }
        else {
            Location maxPheromones = findLocationWithMostFoodPheromones(ant, ant.getOrientation());
            if(maxPheromones != null) {
                double DES = myGrid.getCell(maxPheromones).getFoodPheromoneCount() - 2;
                double D = DES - myGrid.getCell(location).getFoodPheromoneCount();
                if(D > 0) {
                    myGrid.getCell(location).setFoodPheromoneCount(D);
                }
            }
        }
    }

    private void diffuseHomePheromones(Location location, double diffRate) {
        ForagingAntsCell gridCell = myGrid.getCell(location);
        double initialCount = gridCell.getHomePheromoneCount() * diffRate;
        ArrayList<Location> neighbors = myGrid.getAllNeighbors(location);
        Collections.shuffle(neighbors);
        for(int i = 0; i < neighbors.size(); i++) {
            if(neighbors.get(i) != null){
            ForagingAntsCell neighbor = myGrid.getCell(neighbors.get(i));
            if(neighbor.getHomePheromoneCount() < gridCell.getHomePheromoneCount()) {
                gridCell.setHomePheromoneCount(initialCount);
                neighbor.setHomePheromoneCount(neighbor.getHomePheromoneCount() + initialCount);
                break;
                }
            }
        }
    }

    private void diffuseFoodPheromones(Location location, double diffRate) {
        ForagingAntsCell gridCell = myGrid.getCell(location);
        double initialCount = gridCell.getFoodPheromoneCount() * diffRate;
        ArrayList<Location> neighbors = myGrid.getAllNeighbors(location);
        Collections.shuffle(neighbors);

        for(int i = 0; i < neighbors.size(); i++) {
            if(neighbors.get(i) != null) {
                ForagingAntsCell neighbor = myGrid.getCell(neighbors.get(i));
                if (neighbor.getFoodPheromoneCount() < gridCell.getFoodPheromoneCount()) {
                    gridCell.setFoodPheromoneCount(initialCount);
                    neighbor.setFoodPheromoneCount(neighbor.getFoodPheromoneCount() + initialCount);
                    break;
                }
            }
        }
    }

    private void evaporateHomePheromones(Location location, double evapRate) {
        ForagingAntsCell gridCell = myGrid.getCell(location);
        if(gridCell.getHomePheromoneCount() >= minPheromone) {
            gridCell.setHomePheromoneCount(gridCell.getHomePheromoneCount() * evapRate);
        }
    }

    /**
     * @param location
     * @param evapRate Reduces the food pheromone count by the evaporation rate
     */
    private void evaporateFoodPheromones(Location location, double evapRate) {
        ForagingAntsCell gridCell = myGrid.getCell(location);
        if(gridCell.getFoodPheromoneCount() >= minPheromone) {
            gridCell.setFoodPheromoneCount(gridCell.getFoodPheromoneCount() * evapRate);
        }
    }

    private void updatePheromones(Location location){
        diffuseFoodPheromones(location, diffusionRatio);
        diffuseHomePheromones(location, diffusionRatio);
        evaporateFoodPheromones(location, evapRatio);
        evaporateHomePheromones(location, evapRatio);
    }

    private void birthAnts(int count) {
        for(int i = 0; i < count; i++) {
            ForagingAnt ant = new ForagingAnt(nestLocation, antLifetime, myGrid);
            myGrid.getCell(nestLocation).incrementAntCount();
            ants.add(ant);
        }
    }

    @Override
    public void createSeries(LineChart lineChart){
    	populationLine = new XYChart.Series();
        populationLine.setName("Ants");

        //populating the series with data
        lineChart.getData().add(populationLine);
    }


    /**
     *
     */
    @Override
    public void createCellCounter() {

        //createCellCounter();
        Rectangle cellCounter = new Rectangle(
                SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER)
                        + 2 * MARGIN_BOX_TOP, (DIMENSIONS_OF_CELL_COUNTER / 5),
                DIMENSIONS_OF_CELL_COUNTER * 3 / 2, DIMENSIONS_OF_CELL_COUNTER);
        cellCounter.setFill(Color.WHITE);
        cellCounter.setStyle(getCellCounterStyle());
        getRootElement().getChildren().add(cellCounter);
        
        numAntsText.setFill(Color.GRAY);
        updateText();
        getRootElement().getChildren().add(numAntsText);


    }

    /**
     *
     */
    private void updateText() {
        numAntsText.setText(pop + antPopulation);
    }

    /**
     *
     */
    public void updateGraph() {
        populationLine.getData().add(new XYChart.Data(totalSteps, antPopulation));
        updateText();
    }
}
