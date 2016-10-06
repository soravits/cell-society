package foragingants;

import base.Location;

/**
 * This represents an ant in the simulation.
 * @author Soravit
 */
public class ForagingAnt {

    private Location location;
    private int orientation = 0;
    private ForagingAntsGrid myGrid;
    private boolean hasFood;
    private int life;

    /**
     * @param location
     * @param myGrid
     */
    public ForagingAnt(Location location, int life, ForagingAntsGrid myGrid) {
        this.location = location;
        this.life = life;
        this.myGrid = myGrid;
    }

    /**
     *
     * @return The current life of the ant
     */
    public int getLife(){
        return life;
    }

    /**
     * Decrements the life on the ant
     */
    public void decrementLife(){
        life --;
    }

    /**
     * @return The direction that the the ant is facing (0 to 7)
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * @param orientation The desired orientation of the ant
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /**
     * @return The location of the ant
     */

    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location The desired location of the ant
     */
    public void setLocation(Location location){
        this.location = location;
    }

    /**
     *
     * @return Whether or not the ant is carrying food
     */
    public boolean hasFood() {
        return hasFood;
    }

    /**
     * @param hasFood The desired state of whether the ant is carrying food
     */
    public void setHasFood(boolean hasFood) {
        this.hasFood = hasFood;
    }

    /**
     * @param location the desired destination
     * Moves the ant from the current location to the specified location and adjusts ant counts for these cells
     */
    public void move(Location location) {
        myGrid.getCell(this.location).decrementAntCount();
        setLocation(location);
        myGrid.getCell(location).incrementAntCount();
    }
}