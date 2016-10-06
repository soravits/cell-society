package base;

/**
 * This class provides an abstract representation of location in the grid, parameterized by row and column.
 * @author Soravit
 */
public class Location {

	private int row;
	private int col;

	/**
	 * @param row
	 * @param col
	 */
	public Location (int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * @return Row value for this location
	 */
	public int getRow () {
		return row;
	}

	/**
	 * Sets row value for this location
	 */
	public void setRow(int row){
		this.row = row;
	}


	/**
	 * Sets row value for this location
	 */
	public void setCol(int col){
		this.col = col;
	}

	/**
	 * @return Column value for this location
	 */
	public int getColumn () {
		return col;
	}
}