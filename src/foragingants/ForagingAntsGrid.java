package foragingants;

import base.CellShape;
import base.Grid;
import base.Location;
import base.Simulation;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by Soravit on 10/2/2016.
 */
public class ForagingAntsGrid extends Grid{

    private ForagingAntsSimulation sim;
    private Location nest;
    private Location foodSource;

    public ForagingAntsGrid(int rowLength, int sizeOfCell, Pane rootElement,
                          int initialX, int initialY, gridEdgeType edgeType, ForagingAntsSimulation sim,
                            Location nest, Location foodSource) {
        super(rowLength, sizeOfCell, rootElement, initialX, initialY, edgeType);
        this.sim = sim;
        this.nest = nest;
        this.foodSource = foodSource;
    }

    public ForagingAntsCell getCell(int row, int col){
        return (ForagingAntsCell) super.getCell(row,col);
    }

    @Override
    public void initializeGrid(Simulation.CellType type) {
        for(int i = 0; i < getColumnLength(); i++) {
            for(int j = 0; j < getRowLength(); j++) {
                int horizontalOffset = getInitialX();
                double horizontalShift = getSizeOfCell();
                double verticalShift = getSizeOfCell();
                if(type == Simulation.CellType.HEX){
                    horizontalShift = getSizeOfCell()* CellShape.horizontalOffsetHexagon;
                    verticalShift = CellShape.verticalOffsetHexagon * getSizeOfCell();
                    if(i%2 == 0){
                        horizontalOffset= getInitialX() + getSizeOfCell();

                    }
                }
                ForagingAntsCell gridCell = new ForagingAntsCell(getSizeOfCell(), getRootElement(),
                        verticalShift * (j) + horizontalOffset,
                        horizontalShift * (i) + getInitialY(), getRowLength(), type);

                gridCell.addToScene();
                setCell(i,j,gridCell);
            }
        }
        getCell(nest.getRow(), nest.getColumn()).setColor(Color.BROWN);
        getCell(foodSource.getRow(), foodSource.getColumn()).setColor(Color.YELLOW);
    }

    public void updateCell(int row, int col){
        ForagingAntsCell gridCell = getCell(row, col);
        if(gridCell.getAntCount() > 0){
            gridCell.setColor(Color.RED);
        }else if(gridCell != getCell(nest.getRow(), nest.getColumn()) && gridCell != getCell(foodSource.getRow(), foodSource.getColumn())){
            gridCell.setColor(Color.FORESTGREEN);
        }
    }

    public ArrayList<Location> getNorthernNeighbors(Location location){
        int row = location.getRow();
        int col = location.getColumn();
        ArrayList<Location> northernNeighbors = new ArrayList<Location>();
        if(getNorthernNeighbor(row,col) != null) {
            northernNeighbors.add(getNorthernNeighbor(location.getRow(), location.getColumn()));
        }
        if(getNorthernNeighbor(row,col) != null) {
            northernNeighbors.add(getNorthwesternNeighbor(location.getRow(), location.getColumn()));
        }
        if(getNorthernNeighbor(row,col) != null) {
            northernNeighbors.add(getNortheasternNeighbor(location.getRow(), location.getColumn()));
        }
        return northernNeighbors;
    }

    public ArrayList<Location> getSouthernNeighbors(Location location){
        int row = location.getRow();
        int col = location.getColumn();
        ArrayList<Location> southernNeighbors = new ArrayList<Location>();
        if(getSouthernNeighbor(row,col) != null) {
            southernNeighbors.add(getSouthernNeighbor(location.getRow(), location.getColumn()));
        }
        if(getSouthernNeighbor(row,col) != null) {
            southernNeighbors.add(getSouthwesternNeighbor(location.getRow(), location.getColumn()));
        }
        if(getSouthernNeighbor(row,col) != null) {
            southernNeighbors.add(getSoutheasternNeighbor(location.getRow(), location.getColumn()));
        }
        return southernNeighbors;
    }
}
