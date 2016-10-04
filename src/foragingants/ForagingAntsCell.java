package foragingants;

import base.Cell;
import base.Location;
import base.Simulation;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Created by Soravit on 10/2/2016.
 */
public class ForagingAntsCell extends Cell{

    private double homePheromoneCount;
    private double foodPheromoneCount;
    private int antCount;
    /**
     * @param sizeOfCell
     * @param rootElement
     * @param xCoord
     * @param yCoord
     * @param gridLength
     * @param type
     */
    public ForagingAntsCell(int sizeOfCell, Pane rootElement, double xCoord, double yCoord, int gridLength, Simulation.CellType type) {
        super(sizeOfCell, rootElement, xCoord, yCoord, gridLength, type);
    }

    public double getFoodPheromoneCount() {
        return foodPheromoneCount;
    }

    public double getHomePheromoneCount() {
        return homePheromoneCount;
    }

    public double getAntCount() {
        return antCount;
    }

    public void incrementAntCount() {
        antCount++;
    }

    public void decrementAntCount() {
        antCount--;
    }

    public void setHomePheromoneCount(double homePheromoneCount){
        this.homePheromoneCount = homePheromoneCount;
    }

    public void setFoodPheromoneCount(double foodPheromoneCount){
        this.foodPheromoneCount = foodPheromoneCount;
    }

    public void evaporateHomePheromones(double evapRate){
        setFoodPheromoneCount(homePheromoneCount * evapRate);
    }

    public void evaporateFoodPheromones(double evapRate){
        setFoodPheromoneCount(foodPheromoneCount * evapRate);
    }
}
