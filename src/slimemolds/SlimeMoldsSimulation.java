package slimemolds;

import java.util.Random;

import base.Grid;
import base.Simulation;
import base.Simulation.CellType;
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
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - 3 * marginBoxTop, mold);

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
	 * @param probCatch
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

	@Override
	public Scene init (Stage s,CellType type) {
		setStage(s);
		makeNewRootElement();
		int screenWidth = SIMULATION_WINDOW_WIDTH;
		if(type == CellType.HEX){
			screenWidth *= 1.75;
		}

		setMyScene(new Scene(getRootElement(), screenWidth, 
				SIMULATION_WINDOW_HEIGHT, Color.WHITE)); 
		setTopMargin(getTopMargin() + marginBoxTop * 4);
		this.myGrid = new SlimeMoldsGrid(getGridLength(), getCellSize(), getRootElement(), 
				getLeftMargin(), getTopMargin(), this);
		myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
		myGrid.initializeGrid(type);
		myGrid.setUpButtons();
		myGrid.setSimulationProfile(this);
		setInitialEnvironment();
		return getMyScene();
	}


	/**
	 * 
	 */
	public void checkUpdatedStatesAfterManualMod() {
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				if(manuallyModified(i, j)){
					MoldStatus cellState = myGrid.getCell(i, j).getState();
					noLongerModified(i, j);
					if(cellState == MoldStatus.MOLD) {
						numberMold++;
					}
				}
			}
		}
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean manuallyModified(int row,int col) {
		return (myGrid.getCell(row, col).isManuallyModified());
	}

	/**
	 * @param row
	 * @param col
	 */
	private void noLongerModified(int row, int col) {
		myGrid.getCell(row, col).noLongerManuallyModified();
	}


	/* (non-Javadoc)
	 * @see base.Simulation#setInitialEnvironment()
	 */
	public void setInitialEnvironment() {
		numberMold = 0;

		createGraph();
		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				int randomNum = random.nextInt(100);
				if(randomNum <= (probMold*100)) {
					myGrid.getCell(i, j).moldify();
					numberMold++;
				}
				myGrid.updateCell(i, j, myGrid.getCell(i, j).getState(), threshold);
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
				myGrid.getCell(i, j).dissipate(dissipateAmt);

				if(myGrid.getCell(i, j).getState() == MoldStatus.MOLD) {
					boolean nearbyCellPolluted = moveMoldCellToPollution(i, j);
					if(!nearbyCellPolluted) {
						int direction = random.nextInt(4);
						moveMoldCellRandomly(i, j, direction);
					}
					polluteCell(i, j);	
				}
			}
		}

		for(int i = 0; i < getGridLength(); i++) {
			for(int j = 0; j < getGridLength(); j++) {
				SlimeMoldsCell cell = myGrid.getCell(i, j);
				myGrid.updateCell(i, j, cell.getState(), threshold);	
			}
		}
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean moveMoldCellToPollution(int row, int col) {
		SlimeMoldsCell oldCell = myGrid.getCell(row, col);
		SlimeMoldsCell newCell;
		//Can't do && Here, we get Array Index Out of Bounds
		if((col - 1 > 0)) {
			newCell = myGrid.getCell(row, col - 1);
			if(newCell.isAttracting(threshold) 
					&& (newCell.getState() != MoldStatus.MOLD)) {
				moveMoldCellTo(row, col - 1, oldCell);	
				return true;
			}
		}
		else if((row - 1 > 0)) { 
			newCell = myGrid.getCell(row - 1, col);
			if(newCell.isAttracting(threshold) 
					&& (newCell.getState() != MoldStatus.MOLD)) {
				moveMoldCellTo(row - 1, col, oldCell);	
				return true;
			}
		}
		else if((col + 1 > getGridLength() - 1)) {
			newCell = myGrid.getCell(row, col + 1);
			if(newCell.isAttracting(threshold) 
					&& (newCell.getState() != MoldStatus.MOLD)) {
				moveMoldCellTo(row, col + 1, oldCell);	
				return true;
			}
		}
		else if((row + 1 > getGridLength() - 1)) {
			newCell = myGrid.getCell(row+1, col);
			if(newCell.isAttracting(threshold) 
					&& (newCell.getState() != MoldStatus.MOLD)) {
				moveMoldCellTo(row + 1, col, oldCell);	
				return true;
			}
		}

		return false;
	}

	/**
	 * @param row
	 * @param col
	 * @param oldCell
	 */
	private void moveMoldCellTo(int row, int col, SlimeMoldsCell oldCell) {
		oldCell.killMold();
		myGrid.getCell(row, col).moldify();	
		polluteCell(row, col);
	}

	/**
	 * @param row
	 * @param col
	 * @param direction
	 */
	private void moveMoldCellRandomly(int row, int col, int direction) {
		SlimeMoldsCell cell = myGrid.getCell(row, col);   	
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
				|| (myGrid.getCell(row, col).getState() == MoldStatus.MOLD)) {
			return;
		}

		moveMoldCellTo(row, col, cell);
	}

	/**
	 * @param row
	 * @param col
	 */
	private void polluteCell(int row, int col) {
		SlimeMoldsCell cell = myGrid.getCell(row, col);  
		cell.pollute(stepAmt);
		diffuseSpores(row + 1, col);
		diffuseSpores(row - 1, col);
		diffuseSpores(row, col + 1);
		diffuseSpores(row, col - 1);
	}

	/**
	 * @param row
	 * @param col
	 */
	private void diffuseSpores(int row, int col) {
		if((row<0) || (col<0) || (row > getGridLength()-1) || (col > getGridLength()-1)) {
			return;
		}
		myGrid.getCell(row, col).diffuse(diffusionAmt);
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
				SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + 2 * marginBoxTop, 
				(dimensionsOfCellCounterBox / 5), dimensionsOfCellCounterBox * 3 / 2,
				dimensionsOfCellCounterBox);
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
