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
 * when refactoring, pay attention to hardcoded values in this class
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

	private ForagingAntsGrid myGrid;
	private ArrayList<ForagingAnt> ants = new ArrayList<ForagingAnt>();

	/**
	 * @param gridLength
	 * @param type
	 */
	public ForagingAntsSimulation(int gridLength, CellType type, int simDuration, 
			int nestLocationRow, int nestLocationColumn,
			int foodSourceLocationRow, int foodSourceLocationColumn, int maxAntsPerSim,
			int maxAntsPerLocation, int antLifetime, int numInitialAnts, int antsBornPerStep,
			double minPheromone, double maxPheromone, double evapRatio, double diffusionRatio) {
		
		super(gridLength, type);
		this.simDuration = simDuration;
		nestLocation = new Location(nestLocationRow, nestLocationColumn);
        foodSourceLocation = new Location(foodSourceLocationRow, foodSourceLocationColumn);
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

	/* (non-Javadoc)
	 * @see base.Simulation#init(javafx.stage.Stage, base.Simulation.CellType)
	 */
	@Override
	public Scene init(Stage s, CellType type) {
		setStage(s);
		setNewRootElement();

		int screenWidth = SIMULATION_WINDOW_WIDTH;
		if(type == CellType.HEX){
			screenWidth *= 1.75;
		}

		setMyScene(new Scene(getRootElement(), screenWidth, SIMULATION_WINDOW_HEIGHT, Color.WHITE));
		setTopMargin(getTopMargin() + marginBoxTop * 4);
		this.myGrid = new ForagingAntsGrid(getGridLength(), getCellSize(), getRootElement(),
				getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this, 
				nestLocation, foodSourceLocation);
		myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
		myGrid.initializeGrid(type);
		myGrid.setUpButtons();
		myGrid.setSimulationProfile(this);
		setInitialEnvironment();
		return getMyScene();
	}

	/* (non-Javadoc)
	 * @see base.Simulation#setInitialEnvironment()
	 */
	@Override
	public void setInitialEnvironment() {
		birthAnts(numInitialAnts);
		for(int i = 0; i < getGridLength(); i++){
			for(int j = 0; j < getGridLength(); j++){
				myGrid.updateCell(new Location(i, j));
			}
		}
	}

	/* (non-Javadoc)
	 * @see base.Simulation#step()
	 */
	@Override
	public void step() {
		if(ants.size() <= maxAntsPerSim - 2) {
			birthAnts(2);
		}
		for(int i = 0; i < ants.size(); i++){
			forage(ants.get(i));
		}
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				myGrid.updateCell(new Location(i, j));
			}
		}
	}

	/**
	 * @param ant
	 */
	public void forage(ForagingAnt ant) {
		if(ant.hasFood()) {
			returnToNest(ant);
		}
		else {
			findFoodSource(ant);
		}
	}

	/**
	 * @param ant
	 * @param orientation
	 * @return
	 */
	public Location findLocationWithMostFoodPheromones(ForagingAnt ant, int orientation) {
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

	//might be duplicated code in this method
	/**
	 * @param ant
	 * @param orientation
	 * @return
	 */
	public Location findLocationWithMostHomePheromones(ForagingAnt ant, int orientation) {
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

	/**
	 * @param ant
	 */
	public void returnToNest(ForagingAnt ant) {
		Location location = findLocationWithMostHomePheromones(ant, ant.getOrientation());
		if (location != null) {
			dropFoodPheromones(ant);
			ant.move(location);
			if (ant.getLocation() == nestLocation) {
				ant.setHasFood(false);
			}
		}
	}

	/**
	 * @param ant
	 */
	public void findFoodSource(ForagingAnt ant) {
		Location location = findLocationWithMostFoodPheromones(ant, ant.getOrientation());
		if(location != null) {
			dropHomePheromones(ant);
			ant.move(location);
			if(ant.getLocation() == foodSourceLocation) {
				ant.setHasFood(true);
			}
		}
	}

	/**
	 * @param ant
	 */
	public void dropHomePheromones(ForagingAnt ant) {
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

	/**
	 * @param ant
	 */
	public void dropFoodPheromones(ForagingAnt ant) {
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

	/**
	 * @param location
	 * @param diffRate
	 */
	public void diffuseHomePheromones(Location location, double diffRate) {
		ForagingAntsCell gridCell = myGrid.getCell(location);
		double initialCount = gridCell.getHomePheromoneCount() * diffRate;
		ArrayList<Location> neighbors = myGrid.getAllNeighbors(location);
		Collections.shuffle(neighbors);
		
		for(int i = 0; i < neighbors.size(); i++) {
			ForagingAntsCell neighbor = myGrid.getCell(neighbors.get(i));
			if(neighbor.getHomePheromoneCount() < gridCell.getHomePheromoneCount()) {
				gridCell.setHomePheromoneCount(initialCount);
				neighbor.setHomePheromoneCount(neighbor.getHomePheromoneCount() + initialCount);
			}
		}
	}

	/**
	 * @param location
	 * @param diffRate
	 */
	public void diffuseFoodPheromones(Location location, double diffRate) {
		ForagingAntsCell gridCell = myGrid.getCell(location);
		double initialCount = gridCell.getFoodPheromoneCount() * diffRate;
		ArrayList<Location> neighbors = myGrid.getAllNeighbors(location);
		Collections.shuffle(neighbors);
		
		for(int i = 0; i < neighbors.size(); i++) {
			ForagingAntsCell neighbor = myGrid.getCell(neighbors.get(i));
			if(neighbor.getFoodPheromoneCount() < gridCell.getFoodPheromoneCount()){
				gridCell.setFoodPheromoneCount(initialCount);
				neighbor.setFoodPheromoneCount(neighbor.getFoodPheromoneCount() + initialCount);
			}
		}
	}

	/**
	 * @param count
	 */
	public void birthAnts(int count) {
		for(int i = 0; i < count; i++) {
			ForagingAnt ant = new ForagingAnt(nestLocation, myGrid);
			myGrid.getCell(nestLocation).incrementAntCount();
			ants.add(ant);
		}
	}
}
