package foragingants;

import base.Cell;
import base.Location;
import base.Simulation;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * @author Soravit
 */
public class ForagingAntsCell extends Cell {

	private double homePheromoneCount;
	private double foodPheromoneCount;
	private int antCount;

	/**
	 * @param sizeOfCell The size of the cell
	 * @param rootElement The JavaFX pane
	 * @param xCoord The x location of the cell
	 * @param yCoord The y location of the cell
	 * @param gridLength The length of the sides of the entire grid
	 * @param type The shape of the cell
	 */
	public ForagingAntsCell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord, 
			int gridLength, Simulation.CellType type) {
		super(sizeOfCell, rootElement, xCoord, yCoord, gridLength, type);
	}

	/**
	 * @return The amount of food pheromones in the current cell
	 */
	public double getFoodPheromoneCount() {
		return foodPheromoneCount;
	}

	/**
	 * @return The amount of home pheromones in the current cell
	 */
	public double getHomePheromoneCount() {
		return homePheromoneCount;
	}

	/**
	 * @return The number of ants in the cell
	 */
	public double getAntCount() {
		return antCount;
	}

	/**
	 * Increments the current number of ants
	 */
	public void incrementAntCount() {
		antCount++;
	}

	/**
	 * Decrements the current number of ants
	 */
	public void decrementAntCount() {
		antCount--;
	}

	/**
	 * @param homePheromoneCount The desired home pheromone count
	 */
	public void setHomePheromoneCount(double homePheromoneCount) {
		this.homePheromoneCount = homePheromoneCount;
	}

	/**
	 * @param foodPheromoneCount The desired food pheromone count
	 */
	public void setFoodPheromoneCount(double foodPheromoneCount) {
		this.foodPheromoneCount = foodPheromoneCount;
	}

}
