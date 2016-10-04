package base;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class CellShape {
	public static final double horizontalOffsetHexagon = 0.6;
	public static final double verticalOffsetHexagon = 1.925;

	private Polygon cellShape;
	private double verticleOffset = 0; // For aligning them together
	private double horizontalOffset = 0; // For subsequent rows getting aligned horizontally
	private int gridLength;
	//private boolean needsManipulation;

	public CellShape(int gridLength){
		this.gridLength = gridLength;
		//this.needsManipulation = needsManipulation;
	}

	public void setCoords(double xCoord, double yCoord){
		cellShape.setLayoutX(xCoord + horizontalOffset);
		cellShape.setLayoutY(yCoord - verticleOffset);
	}

	public void setHexagonalCell(){
		verticleOffset = 20;
		horizontalOffset = 10;
		cellShape = new Polygon();
		double currIterWidth = 50;
		double pixelWidth = Simulation.GRID_DIMENSION/(1.5*gridLength);
		double r = 4;
		double HEX_HEIGHT_MULTIPLIER = 1.8;
		double offset = 0;

		cellShape.getPoints().addAll(new Double[]{
				currIterWidth-pixelWidth/2,r*pixelWidth*HEX_HEIGHT_MULTIPLIER-offset,
				currIterWidth-pixelWidth,(r+0.5)*pixelWidth*HEX_HEIGHT_MULTIPLIER-offset,
				currIterWidth-pixelWidth/2,(r+1)*pixelWidth*HEX_HEIGHT_MULTIPLIER-offset,
				currIterWidth+pixelWidth/2,(r+1)*pixelWidth*HEX_HEIGHT_MULTIPLIER-offset,
				currIterWidth+pixelWidth,(r+0.5)*pixelWidth*HEX_HEIGHT_MULTIPLIER-offset,
				currIterWidth+pixelWidth/2,r*pixelWidth*HEX_HEIGHT_MULTIPLIER-offset,
		});
	}

	public void setSquareCell(){
		cellShape = new Polygon();
		cellShape.getPoints().addAll(new Double[]{
				0.0, 0.0,
				0.0, Simulation.GRID_DIMENSION/gridLength*1.0,
				Simulation.GRID_DIMENSION/gridLength*1.0, Simulation.GRID_DIMENSION/gridLength*1.0,
				Simulation.GRID_DIMENSION/gridLength*1.0, 0.0});
	}

	public void setTriangularCell(){
		cellShape = new Polygon();
		cellShape.getPoints().addAll(new Double[]{
				0.0, 0.0,
				Simulation.GRID_DIMENSION/gridLength*1.0/2, Simulation.GRID_DIMENSION/gridLength*1.0,
				Simulation.GRID_DIMENSION/gridLength*1.0, 0.0 });
	}

	public Polygon returnShape(){
		return cellShape;
	}
}
