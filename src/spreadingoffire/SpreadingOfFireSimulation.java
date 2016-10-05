package spreadingoffire;
import base.Grid;
import base.Location;
import base.Simulation;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import spreadingoffire.SpreadingOfFireCell.States;
/**
 * @author Soravit
 *
 */
public class SpreadingOfFireSimulation extends Simulation {

    private static final String fire = "Fire: ";
    private static final String dead = "Dead: ";
    private static final String alive = "Alive: ";

    private int numberAlive;
    private int numberDead;
    private int numberFire;

    private XYChart.Series fireLine;
    private XYChart.Series yellowLine;
    private XYChart.Series aliveLine;
    private int stepCount = 0;

    private static final Text numFireText = new Text(
            SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + MARGIN_BOX_TOP * 3,
            0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - 3 * MARGIN_BOX_TOP, fire);
    private static final Text numDeadText = new Text(
            SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + MARGIN_BOX_TOP * 3,
            0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - 2 * MARGIN_BOX_TOP, dead);
    private static final Text numAliveText = new Text(
            SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + MARGIN_BOX_TOP * 3,
            0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - MARGIN_BOX_TOP, alive);
    private double probCatch;
    private SpreadingOfFireGrid myGrid;
    private CellType type;

    /**
     * @param gridLength
     * @param probCatch
     */
    public SpreadingOfFireSimulation(int gridLength, double probCatch,CellType type) {
        super(gridLength,type);
        this.probCatch = probCatch;
        this.type = type;
    }

    /**
     *
     * @param s The stage to be set
     * @param type The shape of the cell
     * @return
     */
    public Scene init (Stage s,CellType type) {
        super.init(s, type);
        return getMyScene();
    }

    /**
     *
     * @return The instantiated SpreadingOfFire grid
     */
    @Override
    public Grid instantiateGrid(){
        this.myGrid = new SpreadingOfFireGrid(getGridLength(), getCellSize(), getRootElement(),
                getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
        return myGrid;
    }

    /**
     * @param location
     */
    public void spawnTree(Location location) {
        myGrid.updateCell(location, States.ALIVE);
        numberAlive++;
        myGrid.getCell(location).spawn();
    }

    /**
     * Updates cell after it's clicked
     */
    public void checkUpdatedStatesAfterManualMod() {
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
                if(manuallyModified(location)){
                    States cellState = myGrid.getCell(new Location(i, j)).getState();
                    noLongerModified(location);
                    if(cellState == States.BURNING) {
                        numberFire++;
                        numberAlive--;
                    }
                    else if (cellState == States.DEAD) {
                        numberDead++;
                        numberFire--;
                    }
                    else if(cellState == States.ALIVE){
                        numberAlive++;
                        numberDead--;
                    }
                }
            }
        }
    }

    private boolean manuallyModified(Location location) {
        return (myGrid.getCell(location).isManuallyModifiedByUser());
    }

    private void noLongerModified(Location location) {
        myGrid.getCell(location).noLongerManuallyModified();
    }

    private void burnTree(Location location, boolean forceBurn) {
        double rand = Math.random();
        if(rand < probCatch || forceBurn) {
            myGrid.getCell(location).catchFire();
            myGrid.updateCell(location, States.CAUGHTFIRE);
            numberFire++;
            numberAlive--;
        }
    }

    private void clearCell(Location location) {
        myGrid.updateCell(location, States.DEAD);
        myGrid.getCell(location).burnout();
        numberDead++;
        if(numberFire > 0){
            numberFire--;
        }
    }

    /**
     * Sets up the initial map of trees
     */
    public void setInitialEnvironment() {
        numberAlive = 0;
        numberDead = 0;
        createGraph();
        int fireBurnedInitially = 0;
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
                if(type != CellType.HEX){
                    if(i == 0 || i == getGridLength() - 1
                            || j == 0 || j == getGridLength()-1) {
                        clearCell(location);
                    }
                    else if(i == getGridLength() / 2
                            && j == getGridLength() / 2) {
                        burnTree(location, true);
                        fireBurnedInitially++;
                    }
                    else {
                        spawnTree(location);
                    }
                }
                else{
                    if(((j == 0) && (i%2 == 1)) || (i == getGridLength() - 2) || i == 1
                            || (j == getGridLength() - 1 && i%2 == 0)  || i == 0
                            || i == getGridLength()-1) {
                        clearCell(location);
                    }
                    else if(i == getGridLength() / 2
                            && j == getGridLength() / 2) {
                        burnTree(location, true);
                        fireBurnedInitially++;
                    }
                    else {
                        spawnTree(location);
                    }
                }
                myGrid.updateCell(location, myGrid.getCell(location).getState());
            }
        }

        numberFire = fireBurnedInitially;
    }

    private void updateState() {
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
                if(myGrid.getCell(location).getState() == States.ALIVE) {
                    if(myGrid.getNorthernNeighbor(location) != null
                            && myGrid.getCell(myGrid.getNorthernNeighbor(location)).
                            getState() == States.BURNING) {
                        burnTree(location, false);
                    }
                    if(myGrid.getSouthernNeighbor(location) != null
                            && myGrid.getCell(myGrid.getSouthernNeighbor(location)).
                            getState() == States.BURNING) {
                        burnTree(location, false);
                    }
                    if(myGrid.getEasternNeighbor(location) != null
                            && myGrid.getCell(myGrid.getEasternNeighbor(location)).
                            getState() == States.BURNING) {
                        burnTree(location, false);
                    }
                    if(myGrid.getSouthernNeighbor(location) != null
                            && myGrid.getCell(myGrid.getSouthernNeighbor(location)).
                            getState() == States.BURNING) {
                        burnTree(location, false);
                    }
                }
            }
        }
        for(int i = 0; i < getGridLength(); i++) {
            for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
                if(myGrid.getCell(location).getState() == States.BURNING) {
                    clearCell(location);
                }
                else if(myGrid.getCell(location).getState() == States.CAUGHTFIRE) {
                    myGrid.getCell(location).burn();
                    myGrid.updateCell(location, States.BURNING);
                }
            }
        }
    }
    
	@Override
	public void createSeries(LineChart lineChart) {
        fireLine = new XYChart.Series();
        fireLine.setName("Fire");
        yellowLine = new XYChart.Series();
        yellowLine.setName("Dead");
        aliveLine = new XYChart.Series();
        aliveLine.setName("Alive");


        //populating the series with data
        //series.getData().add(new XYChart.Data(1, 23));
        lineChart.getData().add(fireLine);
        lineChart.getData().add(yellowLine);
        lineChart.getData().add(aliveLine);
	}

	@Override
	public void createCellCounter() {
        Rectangle cellCounter = new Rectangle(
                SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + 2 * MARGIN_BOX_TOP,
                (DIMENSIONS_OF_CELL_COUNTER / 5), DIMENSIONS_OF_CELL_COUNTER * 3 / 2,
                DIMENSIONS_OF_CELL_COUNTER);
        cellCounter.setFill(Color.WHITE);
        cellCounter.setStyle(getCellCounterStyle());
        getRootElement().getChildren().add(cellCounter);
        numFireText.setFill(Color.RED);
        numDeadText.setFill(Color.ORANGE);
        numAliveText.setFill(Color.GREEN);
        updateText();
        getRootElement().getChildren().add(numFireText);
        getRootElement().getChildren().add(numDeadText);
        getRootElement().getChildren().add(numAliveText);

	}

    /**
     *
     */
    private void updateText() {
        numFireText.setText(fire + numberFire);
        numDeadText.setText(dead + numberDead);
        numAliveText.setText(alive + numberAlive);
    }

    /**
     * Updates the graph
     */
    public void updateGraph() {
        fireLine.getData().add(new XYChart.Data(stepCount, numberFire));
        yellowLine.getData().add(new XYChart.Data(stepCount, numberDead));
        aliveLine.getData().add(new XYChart.Data(stepCount, numberAlive));
        updateText();
    }

    /**
     * Updates the grid on each stepf
     */
    @Override
    public void step () {
        updateState();
        updateGraph();
        stepCount++;
    }
}