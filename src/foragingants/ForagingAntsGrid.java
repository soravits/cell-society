package foragingants;

import base.CellShape;
import base.Grid;
import base.Location;
import base.Simulation;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @author Soravit
 */
public class ForagingAntsGrid extends Grid {

    private ForagingAntsSimulation sim;
    private Location nest;
    private Location foodSource;

    /**
     * @param gridLength The length of a side of the grid
     * @param sizeOfCell The size of each cell
     * @param rootElement The JavaFX pane
     * @param initialX The initial x position of the grid
     * @param initialY The initial y position of the grid
     * @param edgeType The grid edge type of the grid
     * @param sim The simulation that uses the grid
     * @param nest The location of the nest in the grid
     * @param foodSource The location of the food source in the grid
     */
    public ForagingAntsGrid(int gridLength, int sizeOfCell, Pane rootElement, int initialX, int initialY,
                            gridEdgeType edgeType, ForagingAntsSimulation sim,
                            Location nest, Location foodSource) {
        super(gridLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
        this.sim = sim;
        this.nest = nest;
        this.foodSource = foodSource;
    }

    /**
     *
     * @param location The location of the desired cell
     * @return The desired cell
     */
    public ForagingAntsCell getCell(Location location) {
        return (ForagingAntsCell) super.getCell(location);
    }

    /**
     *
     * @param type The shape of the cells in the grid
     * Creates the grid by initializing the cells
     */
    @Override
    public void initializeGrid(Simulation.CellType type) {
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                int horizontalOffset = getInitialX();
                double horizontalShift = getSizeOfCell();
                double verticalShift = getSizeOfCell();
                if(type == Simulation.CellType.HEX) {
                    horizontalShift = getSizeOfCell() * CellShape.horizontalOffsetHexagon;
                    verticalShift = CellShape.verticalOffsetHexagon * getSizeOfCell();
                    if(i % 2 == 0){
                        horizontalOffset= getInitialX() + getSizeOfCell();
                    }
                }
                ForagingAntsCell gridCell = new ForagingAntsCell(getSizeOfCell(), getRootElement(),
                        verticalShift * (j) + horizontalOffset,
                        horizontalShift * (i) + getInitialY(), getGridLength(), type);
                Location cellLocation = new Location(i,j);
                gridCell.addToScene();
                setCell(cellLocation, gridCell);
            }
        }
        getCell(nest).setColor(Color.BROWN);
        getCell(foodSource).setColor(Color.YELLOW);
    }

    /**
     * @param location The location in the grid to be updated
     * Sets the colors of the cells based on the state of th cell
     */
    public void updateCell(Location location) {
        ForagingAntsCell gridCell = getCell(location);
        if (gridCell != getCell(nest) && gridCell != getCell(foodSource)) {
            if (gridCell.getAntCount() > 0) {
                gridCell.setColor(Color.RED);
            }
            else if(gridCell.getFoodPheromoneCount() > 0 || gridCell.getHomePheromoneCount() > 0) {
                gridCell.setColor(Color.GREY);
            }
            else {
                gridCell.setColor(Color.FORESTGREEN);
            }
        }
    }
}