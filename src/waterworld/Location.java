package waterworld;

/**
 * @author Soravit
 *
 */
public class Location {

    private int x;
    private int y;
    
    /**
     * @param x
     * @param y
     */
    public Location (int x, int y) {
      this.setX(x);
      this.setY(y);
    }

    /**
     * @return
     */
    public int getX () {
        return x;
    }

    /**
     * @param x
     */
    public void setX (int x) {
        this.x = x;
    }

    /**
     * @return
     */
    public int getY () {
        return y;
    }

    /**
     * @param y
     */
    public void setY (int y) {
        this.y = y;
    }
}
