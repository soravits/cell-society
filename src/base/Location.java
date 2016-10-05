package base;

/**
 * @author Soravit
 *
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
	 * @return
	 */
	public int getRow () {
		return row;
	}

	public void setRow(int row){
		this.row = row;
	}

	public void setCol(int col){
		this.col = col;
	}

	/**
	 * @return
	 */
	public int getColumn () {
		return col;
	}
}
