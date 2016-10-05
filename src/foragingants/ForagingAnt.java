package foragingants;

import base.Location;

/**
 * Created by Soravit on 10/2/2016.
 */
public class ForagingAnt {

    private Location location;
    private int orientation = 0;
    private ForagingAntsGrid myGrid;
    private boolean hasFood;

    /**
     * @param location
     * @param myGrid
     */
    public ForagingAnt(Location location, ForagingAntsGrid myGrid) {
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }


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
            myGrid.getCell(location).decrementAntCount();
            setLocation(location);
            myGrid.getCell(location).incrementAntCount();
    }
}
