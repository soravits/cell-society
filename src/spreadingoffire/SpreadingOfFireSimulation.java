package spreadingoffire;
import base.Grid;
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
import spreadingoffire.SpreadingOfFireCell.States;
/**
 * @author Soravit
 *
 */
public class SpreadingOfFireSimulation extends Simulation {

	private static final String fire = "Fire: ";
	private static final String dead = "Dead: ";
	private static final String alive = "Alive: ";

	private int numberAlive;
	private int numberDead;
	private int numberFire;

	private XYChart.Series fireLine;
	private XYChart.Series yellowLine;
	private XYChart.Series aliveLine;
	private int stepCount = 0;

	private static final Text numFireText = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - 3 * marginBoxTop, fire);
	private static final Text numDeadText = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - 2 * marginBoxTop, dead);
	private static final Text numAliveText = new Text(
			SIMULATION_WINDOW_WIDTH - (2 * dimensionsOfCellCounterBox) + marginBoxTop * 3, 
			0 + (7 / 5 * dimensionsOfCellCounterBox) - marginBoxTop, alive);
	private double probCatch;
	private SpreadingOfFireGrid myGrid;
	private CellType type;

	/**
	 * @param gridLength
	 * @param probCatch
	 */
	public SpreadingOfFireSimulation(int gridLength, double probCatch,CellType type) {
		super(gridLength,type);
		this.probCatch = probCatch;
		this.type = type;
	}

	@Override
	public Scene init (Stage s,CellType type) {
		setStage(s);
		makeNewRootElement();
		int screenWidth = SIMULATION_WINDOW_WIDTH;
		if(type == CellType.HEX) {
			screenWidth *= 1.75;
		}

		setMyScene(new Scene(getRootElement(), screenWidth,
				SIMULATION_WINDOW_HEIGHT, Color.WHITE));
		setTopMargin(getTopMargin() + marginBoxTop * 4);
		this.myGrid = new SpreadingOfFireGrid(getGridLength(), getCellSize(), getRootElement(),
				getLeftMargin(), getTopMargin(), Grid.gridEdgeType.finite, this);
		myGrid.setBackground(screenWidth, SIMULATION_WINDOW_HEIGHT);
		myGrid.initializeGrid(type);
		myGrid.setUpButtons();
		myGrid.setSimulationProfile(this);
		setInitialEnvironment();
		return getMyScene();
	}

	/**
	 * @param row
	 * @param col
	 */
	 public void spawnTree(int row, int col) {
		 myGrid.updateCell(row,col,States.ALIVE);
		 numberAlive++;
		 myGrid.getCell(row, col).spawn();
	 }

	 /**
	  * 
	  */
	 public void checkUpdatedStatesAfterManualMod() {
		 for(int i = 0; i < getGridLength(); i++) {
			 for(int j = 0; j < getGridLength(); j++) {
				 if(manuallyModified(i, j)){
					 States cellState = myGrid.getCell(i, j).getState();
					 noLongerModified(i, j);
					 if(cellState == States.BURNING) {
						 numberFire++;
						 numberAlive--;
					 }
					 else if (cellState == States.DEAD) {
						 numberDead++;
						 numberFire--;
					 }
					 else if(cellState == States.ALIVE){
						 numberAlive++;
						 numberDead--;
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
	 /**
	  * @param x
	  * @param y
	  * @param forceBurn
	  */
	 public void burnTree(int x, int y, boolean forceBurn) {
		 double rand = Math.random();
		 if(rand < probCatch || forceBurn) {
			 myGrid.getCell(x, y).catchfire();
			 myGrid.updateCell(x, y, States.CAUGHTFIRE);
			 numberFire++;
			 numberAlive--;
		 }
	 }

	 /**
	  * @param x
	  * @param y
	  */
	 public void clearCell(int x, int y) {
		 myGrid.updateCell(x, y, States.DEAD);
		 myGrid.getCell(x, y).burnout();
		 numberDead++;
		 if(numberFire > 0){
			 numberFire--;
		 }
	 }

	 /* (non-Javadoc)
	  * @see base.Simulation#setInitialEnvironment()
	  */
	 public void setInitialEnvironment() {
		 numberAlive = 0;
		 numberDead = 0;
		 createGraph();
		 int fireBurnedInitially = 0;
		 for(int i = 0; i < getGridLength(); i++) {
			 for(int j = 0; j < getGridLength(); j++) {
				 if(type != CellType.HEX){
					 if(i == 0 || i == getGridLength() - 1
							 || j == 0 || j == getGridLength()-1) {
						 clearCell(i, j);
					 }
					 else if(i == getGridLength() / 2 
							 && j == getGridLength() / 2) {
						 burnTree(i, j, true);
						 fireBurnedInitially++;
					 }
					 else {
						 spawnTree(i, j);
					 }
				 }
				 else{
					 if(((j == 0) && (i%2 == 1)) || (i == getGridLength() - 2) || i == 1 
							 || (j == getGridLength() - 1 && i%2 == 0)  || i == 0 
							 || i == getGridLength()-1) {
						 clearCell(i, j);
					 }
					 else if(i == getGridLength() / 2 
							 && j == getGridLength() / 2) {
						 burnTree(i, j, true);
						 fireBurnedInitially++;
					 }
					 else {
						 spawnTree(i, j);
					 }
				 }
				 myGrid.updateCell(i, j, myGrid.getCell(i,j).getState());
			 }
		 }

		 numberFire = fireBurnedInitially;
	 }

	 /**
	  * 
	  */
	 public void updateState() {
		 for(int i = 0; i < getGridLength(); i++) {
			 for(int j = 0; j < getGridLength(); j++) {
				 if(myGrid.getCell(i,j).getState() == States.ALIVE) {
					 if(myGrid.getNorthernNeighbor(i, j) != null 
							 && myGrid.getCell(myGrid.getNorthernNeighbor(i,j).getRow(), 
									 myGrid.getNorthernNeighbor(i,j).getColumn()).
									 getState() == States.BURNING) {
						 burnTree(i, j, false);
					 }
					 if(myGrid.getSouthernNeighbor(i, j) != null 
							 && myGrid.getCell(myGrid.getSouthernNeighbor(i,j).getRow(), 
									 myGrid.getSouthernNeighbor(i,j).getColumn()).
									 getState() == States.BURNING) {
						 burnTree(i, j, false);
					 }
					 if(myGrid.getEasternNeighbor(i, j) != null 
							 && myGrid.getCell(myGrid.getEasternNeighbor(i,j).getRow(), 
									 myGrid.getEasternNeighbor(i,j).getColumn()).
									 getState() == States.BURNING) {
						 burnTree(i, j, false);
					 }
					 if(myGrid.getWesternNeighbor(i, j) != null 
							 && myGrid.getCell(myGrid.getWesternNeighbor(i,j).getRow(),
									 myGrid.getWesternNeighbor(i,j).getColumn()).
									 getState() == States.BURNING) {
						 burnTree(i, j, false);
					 }
				 } 
			 }
		 }
		 for(int i = 0; i < getGridLength(); i++) {
			 for(int j = 0; j < getGridLength(); j++) {
				 if(myGrid.getCell(i,j).getState() == States.BURNING) {
					 clearCell(i, j);
				 } 
				 else if(myGrid.getCell(i,j).getState() == States.CAUGHTFIRE) {
					 myGrid.getCell(i,j).burn();
					 myGrid.updateCell(i,j,States.BURNING);
				 }
			 }
		 }
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
		 fireLine = new XYChart.Series();
		 fireLine.setName("Fire");
		 yellowLine = new XYChart.Series();
		 yellowLine.setName("Dead");
		 aliveLine = new XYChart.Series();
		 aliveLine.setName("Alive");


		 //populating the series with data
		 //series.getData().add(new XYChart.Data(1, 23));
		 lineChart.getData().add(fireLine);
		 lineChart.getData().add(yellowLine);
		 lineChart.getData().add(aliveLine);

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
		 numFireText.setFill(Color.RED);
		 numDeadText.setFill(Color.ORANGE);
		 numAliveText.setFill(Color.GREEN);
		 updateText();
		 getRootElement().getChildren().add(numFireText);
		 getRootElement().getChildren().add(numDeadText);
		 getRootElement().getChildren().add(numAliveText);


	 }

	 /**
	  * 
	  */
	 private void updateText() {
		 numFireText.setText(fire + numberFire);
		 numDeadText.setText(dead + numberDead);
		 numAliveText.setText(alive + numberAlive);
	 }

	 /**
	  * 
	  */
	 public void updateGraph() {
		 fireLine.getData().add(new XYChart.Data(stepCount, numberFire));
		 yellowLine.getData().add(new XYChart.Data(stepCount, numberDead));
		 aliveLine.getData().add(new XYChart.Data(stepCount, numberAlive));
		 updateText();
	 }

	 @Override
	 public void step () {
		 updateState();
		 updateGraph();
		 stepCount++;
	 }
}