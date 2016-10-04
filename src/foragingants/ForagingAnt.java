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

    public ForagingAnt(int row, int col, ForagingAntsGrid myGrid){
        this.row = row;
        this.col = col;
        this.myGrid = myGrid;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row){
        this.row = row;
    }

    public int getColumn() {
        return col;
    }

    public Location getLocation(){
        return new Location(row, col);
    }

    public void setCol(int col){
        this.col = col;
    }

    public boolean hasFood() {
        return hasFood;
    }

    public void setHasFood(boolean hasFood){
        this.hasFood = hasFood;
    }

    public void move(Location location){
            myGrid.getCell(row, col).decrementAntCount();
            setRow(location.getRow());
            setCol(location.getColumn());
            myGrid.getCell(location.getRow(), location.getColumn()).incrementAntCount();
    }
}
