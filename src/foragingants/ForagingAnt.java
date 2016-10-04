package foragingants;

import base.Location;

/**
 * Created by Soravit on 10/2/2016.
 */
public class ForagingAnt {

    private int row;
    private int col;
    private int orientation = 0;
    private ForagingAntsGrid myGrid;
    private boolean hasFood;

    /**
     * @param row
     * @param col
     * @param myGrid
     */
    public ForagingAnt(int row, int col, ForagingAntsGrid myGrid) {
        this.row = row;
        this.col = col;
        this.myGrid = myGrid;
    }

    /**
     * @return
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * @param orientation
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /**
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return
     */
    public int getColumn() {
        return col;
    }

    /**
     * @return
     */
    public Location getLocation() {
        return new Location(row, col);
    }

    /**
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @return
     */
    public boolean hasFood() {
        return hasFood;
    }

    /**
     * @param hasFood
     */
    public void setHasFood(boolean hasFood) {
        this.hasFood = hasFood;
    }

    /**
     * @param location
     */
    public void move(Location location) {
            myGrid.getCell(row, col).decrementAntCount();
            setRow(location.getRow());
            setCol(location.getColumn());
            myGrid.getCell(location.getRow(), location.getColumn()).incrementAntCount();
    }
}
