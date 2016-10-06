package base;

import javafx.scene.shape.Polygon;

/**
 * @author Brian
 *
 */
public class CellShape {

	public static final double horizontalOffsetHexagon = 0.6;
	public static final double verticalOffsetHexagon = 1.925;
	
	private static final double HEX_WIDTH = 50;
	private static final double RADIUS_OF_HEX = 4;
	private static final double HEX_HEIGHT_MULTIPLIER = 1.8;

    private double verticalOffset = 0; // For aligning them together
    private double horizontalOffset = 0; // For subsequent rows getting aligned horizontally
    private int gridLength;
    private double sidePoint;
	private Polygon cellShape;

	/**
	 * @param gridLength
	 */
	public CellShape(int gridLength) {
		this.gridLength = gridLength;
	}

	/**
	 * @param xCoord
	 * @param yCoord
	 * 
	 * Set coordinates for cell on scene
	 */
	public void setCoords(double xCoord, double yCoord) {
		cellShape.setLayoutX(xCoord + horizontalOffset);
		cellShape.setLayoutY(yCoord - verticalOffset);
	}

	/**
	 * Makes cell hexagonal shape
	 */
	public void setHexagonalCell() {
		verticalOffset = 20;
		horizontalOffset = 10;
		cellShape = new Polygon();
		double pixelWidth = Simulation.GRID_DIMENSION / (1.5 * gridLength);

		cellShape.getPoints().addAll(new Double[] {
				HEX_WIDTH - pixelWidth / 2, RADIUS_OF_HEX * pixelWidth * HEX_HEIGHT_MULTIPLIER,
				HEX_WIDTH - pixelWidth, (RADIUS_OF_HEX + 0.5) * pixelWidth * HEX_HEIGHT_MULTIPLIER,
				HEX_WIDTH - pixelWidth / 2, (RADIUS_OF_HEX + 1) * pixelWidth * HEX_HEIGHT_MULTIPLIER,
				HEX_WIDTH + pixelWidth / 2, (RADIUS_OF_HEX + 1) * pixelWidth * HEX_HEIGHT_MULTIPLIER,
				HEX_WIDTH + pixelWidth, (RADIUS_OF_HEX + 0.5) * pixelWidth * HEX_HEIGHT_MULTIPLIER,
				HEX_WIDTH + pixelWidth / 2, RADIUS_OF_HEX * pixelWidth * HEX_HEIGHT_MULTIPLIER
		});
	}

	/**
	 * Makes cell square shape -> default
	 */
	public void setSquareCell() {
		cellShape = new Polygon();
		sidePoint = Simulation.GRID_DIMENSION / gridLength;
		cellShape.getPoints().addAll(new Double[] {
				0.0, 0.0, 0.0, 
				sidePoint,
				sidePoint, 
				sidePoint,
				sidePoint, 
				0.0
		});
	}

	/**
	 * Makes cell triangular
	 */
	public void setTriangularCell() {
		cellShape = new Polygon();
		sidePoint = Simulation.GRID_DIMENSION / gridLength;
		cellShape.getPoints().addAll(new Double[] {
				0.0, 0.0,
				sidePoint / 2,
				sidePoint,
				sidePoint,
				0.0 
		});
	}

	/**
	 * @return
	 */
	public Polygon returnShape() {
		return cellShape;
	}
}
