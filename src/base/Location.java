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
		this.setRow(row);
		this.setColumn(col);
	}

	/**
	 * @return
	 */
	public int getRow () {
		return row;
	}

	/**
	 * @param row
	 */
	public void setRow (int row) {
		this.row = row;
	}

	/**
	 * @return
	 */
	public int getColumn () {
		return col;
	}

	/**
	 * @param col
	 */
	public void setColumn (int col) {
		this.col = col;
	}
}
