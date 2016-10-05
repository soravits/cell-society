package slimemolds;

import java.util.Random;

import base.Grid;
import base.Location;
import base.Simulation;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import slimemolds.SlimeMoldsCell.MoldStatus;

public class SlimeMoldsSimulation extends Simulation {

	private static final String mold = "Mold: ";
	private static final Text numMoldText = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + MARGIN_BOX_TOP * 3,
			0 + (7 / 5 * DIMENSIONS_OF_CELL_COUNTER) - 3 * MARGIN_BOX_TOP, mold);

	private Random random = new Random();

	//UI Stuff
	private int numberMold; 
	private XYChart.Series moldLine;
	private int stepCount = 0;

	private SlimeMoldsGrid myGrid;
	private CellType type;

	private double diffusionAmt;
	private double stepAmt;
	private double threshold;
	private double probMold;
	private double dissipateAmt;

	/**
	 * @param gridLength
	 */
	public SlimeMoldsSimulation(int gridLength, double diffusionAmt, double stepAmt, double threshold, 
			double dissipateAmt, double probMold, CellType type) {
		super(gridLength,type);
		this.type = type;
		this.diffusionAmt = diffusionAmt;
		this.stepAmt = stepAmt;
		this.threshold = threshold;
		this.dissipateAmt = dissipateAmt;
		this.probMold = probMold;
	}

	public Scene init (Stage s,CellType type) {
		super.init(s, type);
		return getMyScene();
	}

	public Grid instantiateGrid(){
		this.myGrid = new SlimeMoldsGrid(getGridLength(), getCellSize(), getRootElement(),
				getLeftMargin(), getTopMargin(), this);
		return myGrid;
	}


	/**
	 * 
	 */
	public void checkUpdatedStatesAfterManualMod() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
				if(manuallyModified(location)){
					MoldStatus cellState = myGrid.getCell(new Location(i, j)).getState();
					noLongerModified(location);
					if(cellState == MoldStatus.MOLD) {
						numberMold++;
					}
				}
			}
		}
	}

	/**
	 * @param location
	 * @return
	 */
	private boolean manuallyModified(Location location) {
		return (myGrid.getCell(location).isManuallyModifiedByUser());
	}

	/**
	 * @param location
	 */
	private void noLongerModified(Location location) {
		myGrid.getCell(location).noLongerManuallyModified();
	}


	/* (non-Javadoc)
	 * @see base.Simulation#setInitialEnvironment()
	 */
	public void setInitialEnvironment() {
		numberMold = 0;

		createGraph();
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
                Location cellLocation = new Location(i, j);
				int randomNum = random.nextInt(100);
				if(randomNum <= (probMold*100)) {
					myGrid.getCell(cellLocation).moldify();
					numberMold++;
				}
				myGrid.updateCell(cellLocation, myGrid.getCell(cellLocation).getState(), threshold);
			}
		}
		updateGraph();
	}

	/**
	 * 
	 */
	public void updateState() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
				myGrid.getCell(location).dissipate(dissipateAmt);

				if(myGrid.getCell(location).getState() == MoldStatus.MOLD) {
					boolean nearbyCellPolluted = moveMoldCellToPollution(location);
					if(!nearbyCellPolluted) {
						int direction = random.nextInt(4);
						moveMoldCellRandomly(location, direction);
					}
					polluteCell(location);
				}
			}
		}

		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
                Location location = new Location(i, j);
				SlimeMoldsCell cell = myGrid.getCell(location);
				myGrid.updateCell(location, cell.getState(), threshold);
			}
		}
	}

	/**
	 * @param location
	 * @return
	 */
	private boolean moveMoldCellToPollution(Location location) {
		SlimeMoldsCell oldCell = myGrid.getCell(location);
		SlimeMoldsCell newCell;
		//Can't do && Here, we get Array Index Out of Bounds
		if((location.getColumn() - 1 > 0)) {
			newCell = myGrid.getCell(myGrid.getWesternNeighbor(location));
			if(newCell.isAttracting(threshold) 
					&& (newCell.getState() != MoldStatus.MOLD)) {
				moveMoldCellTo(myGrid.getWesternNeighbor(location), oldCell);
				return true;
			}
		}
		else if((location.getRow() - 1 > 0)) {
			newCell = myGrid.getCell(myGrid.getNorthernNeighbor(location));
			if(newCell.isAttracting(threshold) 
					&& (newCell.getState() != MoldStatus.MOLD)) {
				moveMoldCellTo(myGrid.getNorthernNeighbor(location), oldCell);
				return true;
			}
		}
		else if((location.getColumn() + 1 > getGridLength() - 1)) {
			newCell = myGrid.getCell(myGrid.getEasternNeighbor(location));
			if(newCell.isAttracting(threshold) 
					&& (newCell.getState() != MoldStatus.MOLD)) {
				moveMoldCellTo(myGrid.getEasternNeighbor(location), oldCell);
				return true;
			}
		}
		else if((location.getRow() + 1 > getGridLength() - 1)) {
			newCell = myGrid.getCell(myGrid.getSouthernNeighbor(location));
			if(newCell.isAttracting(threshold) 
					&& (newCell.getState() != MoldStatus.MOLD)) {
				moveMoldCellTo(myGrid.getSouthernNeighbor(location), oldCell);
				return true;
			}
		}

		return false;
	}

	/**
	 * @param location
	 * @param oldCell
	 */
	private void moveMoldCellTo(Location location, SlimeMoldsCell oldCell) {
		oldCell.killMold();
		myGrid.getCell(location).moldify();
		polluteCell(location);
	}

	/**
	 * @param location
	 * @param direction
	 */
	private void moveMoldCellRandomly(Location location, int direction) {
		SlimeMoldsCell cell = myGrid.getCell(location);
        int row = location.getRow();
        int col = location.getColumn();
		switch (direction) {
		case 0:  col -= 1;
		break;
		case 1:  col += 1;
		break;
		case 2:  row -= 1;
		break;
		case 3:  row += 1;
		break; 
		}  	 

		if((row < 0) || (col < 0) || (row > getGridLength() - 1) || (col > getGridLength() - 1) 
				|| (myGrid.getCell(location).getState() == MoldStatus.MOLD)) {
			return;
		}

		moveMoldCellTo(new Location(row, col), cell);
	}

	/**
	 * @param location
	 */
	private void polluteCell(Location location) {
        int row = location.getRow();
        int col = location.getColumn();
		SlimeMoldsCell cell = myGrid.getCell(location);
		cell.pollute(stepAmt);
		diffuseSpores(myGrid.getSouthernNeighbor(location));
		diffuseSpores(myGrid.getNorthernNeighbor(location));
		diffuseSpores(myGrid.getEasternNeighbor(location));
		diffuseSpores(myGrid.getWesternNeighbor(location));
	}

	/**
	 * @param location
	 */
	private void diffuseSpores(Location location) {
        int row = location.getRow();
        int col = location.getColumn();
		if((row<0) || (col<0) || (row > getGridLength()-1) || (col > getGridLength()-1)) {
			return;
		}
		myGrid.getCell(location).diffuse(diffusionAmt);
	}

	/**
	 * 
	 */
	public void createGraph() {
		//defining the axes
		final NumberAxis xAxis = new NumberAxis();
		xAxis.setTickLabelsVisible(false);
		xAxis.setTickMarkVisible(false);
		xAxis.setMinorTickVisible(false);
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setMinorTickVisible(false);

		//creating the chart
		final LineChart <Number, Number> lineChart = 
				new LineChart <Number, Number> (xAxis, yAxis);
		moldLine = new XYChart.Series();


		lineChart.getData().add(moldLine);


		lineChart.setLayoutX(25);
		lineChart.setPrefSize(500, 100);
		lineChart.setLegendVisible(true);
		lineChart.setLegendSide(Side.RIGHT);
		getRootElement().getChildren().add(lineChart);


		Rectangle cellCounter = new Rectangle(
				SIMULATION_WINDOW_WIDTH - (2 * DIMENSIONS_OF_CELL_COUNTER) + 2 * MARGIN_BOX_TOP,
				(DIMENSIONS_OF_CELL_COUNTER / 5), DIMENSIONS_OF_CELL_COUNTER * 3 / 2,
				DIMENSIONS_OF_CELL_COUNTER);
		cellCounter.setFill(Color.WHITE);
		cellCounter.setStyle(
				"-fx-background-radius: 8,7,6;" + 
						"-fx-background-insets: 0,1,2;" +
						"-fx-text-fill: black;" +
						"-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
				);
		getRootElement().getChildren().add(cellCounter);
		numMoldText.setFill(Color.RED);

		updateText();
		getRootElement().getChildren().add(numMoldText);


	}

	/**
	 * 
	 */
	private void updateText() {
		numMoldText.setText(mold + numberMold);
	}

	/**
	 * 
	 */
	public void updateGraph() {
		moldLine.getData().add(new XYChart.Data(stepCount, numberMold));
		updateText();
	}
	@Override
	public void step () {
		updateState();
		updateGraph();
		stepCount++;
	}

}
