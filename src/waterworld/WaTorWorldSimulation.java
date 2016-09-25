package waterworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import base.Cell;
import base.Simulation;
import gameoflife.GameOfLifeCell.States;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import spreadingoffire.SpreadingOfFireGrid;
import waterworld.WaTorWorldCell.State;

public class WaTorWorldSimulation extends Simulation{

    private WaTorWorldGrid myGrid;
    private double fracFish;
    private double fracShark;
    private int fishBreedTime;
    private int sharkBreedTime;
    private int starveTime;
    
    private int fishCount;
    private int sharkCount;
    
    private XYChart.Series fishSeries;
    private XYChart.Series sharkSeries;
    private int stepCount = 0;

    public WaTorWorldSimulation(int gridLength, double fracFish, double fracShark, int fishBreedTime, int sharkBreedTime, int starveTime) {
        super(gridLength);
        this.gridLength = gridLength;
        this.fracFish = fracFish;
        this.fracShark = fracShark;
        this.fishBreedTime = fishBreedTime;
        this.sharkBreedTime = sharkBreedTime;
        this.starveTime = starveTime;

        // TODO Auto-generated constructor stub
    }

    @Override
    public Scene init (Stage s) {
        stage = s;
        myScene = new Scene(rootElement, SIMULATION_WINDOW_WIDTH, SIMULATION_WINDOW_HEIGHT, Color.WHITE);  
        int lengthOfGridInPixels = gridLength * Cell.cellSize - 100;
        int marginOnSidesOfGrid = 150;
        int marginTop = SIMULATION_WINDOW_HEIGHT/8 + 80;

        this.myGrid = new WaTorWorldGrid(gridLength,cellSize,rootElement,marginOnSidesOfGrid, marginTop);
        myGrid.initializeGrid();
        myGrid.setUpButtons();
        myGrid.setSimulationProfile(this);
        setInitialEnvironment();
        createGraph();

        return myScene;
    }

    @Override
    public void setInitialEnvironment(){
        for(int i = 0; i<gridLength; i++){
            for(int j = 0; j<gridLength; j++){
                double rand = Math.random();
                if(rand < fracFish){
                    breedFish(i,j);
                }else if(rand > fracFish && rand < fracFish + fracShark){
                    breedShark(i,j);
                }else{
                    killCell(i,j);
                }
            }
        }
    }

    public void updateState(){
        sharkCount = 0;
        fishCount = 0;
        Cell[][] grid = myGrid.getGrid();
        for(int i = 0; i<gridLength; i++){
            for(int j = 0; j<gridLength; j++){
                if(((WaTorWorldCell) grid[i][j]).getState() == State.SHARK){
                    updateShark(i,j);
                    sharkCount++;
                } else if (((WaTorWorldCell) grid[i][j]).getState() == State.FISH){
                    updateFish(i,j);
                    fishCount++;
                }
            }
        }
        System.out.println("Number of fish: " + fishCount);
        System.out.println("Number of sharks: " + sharkCount);
    }

    public void updateShark(int x, int y){
        Location currLocation = new Location(x,y);
        Cell[][] grid = myGrid.getGrid();
        ArrayList<Location> fish = new ArrayList<Location>();
        ((WaTorWorldCell) grid[x][y]).decrementBreedTime();
        ((WaTorWorldCell) grid[x][y]).decrementStarveTime();
        if(x != 0 && ((WaTorWorldCell) grid[x-1][y]).getState() == State.FISH){
            fish.add(new Location(x-1,y));
        }
        if(x != gridLength-1 && ((WaTorWorldCell) grid[x+1][y]).getState() == State.FISH){
            fish.add(new Location(x+1,y));
        }
        if(y != gridLength - 1 && ((WaTorWorldCell) grid[x][y+1]).getState() == State.FISH){
            fish.add(new Location(x,y+1));
        }
        if(y != 0 && ((WaTorWorldCell) grid[x][y-1]).getState() == State.FISH){
            fish.add(new Location(x,y-1));
        }
        if(fish.size() == 0){
            Location loc = getRandomEmptyNeighbor(x,y);
            if(loc != null){
                moveFish(currLocation,loc); 
                currLocation.setX(loc.getX());
                currLocation.setY(loc.getY());
            }
        }else if(fish.size() == 1){
            killCell(fish.get(0).getX(),fish.get(0).getY());
            ((WaTorWorldCell) grid[x][y]).setStarveTime(starveTime);
        } else {
            Collections.shuffle(fish);
            killCell(fish.get(0).getX(),fish.get(0).getY());
            ((WaTorWorldCell) grid[x][y]).setStarveTime(starveTime);
        }
        if(((WaTorWorldCell) grid[currLocation.getX()][currLocation.getY()]).getStarveTime() == 0){
            killCell(currLocation.getX(), currLocation.getY());
        } else if(((WaTorWorldCell) grid[currLocation.getX()][currLocation.getY()]).getBreedTime() == 0){
            Location loc = getRandomEmptyNeighbor(currLocation.getX(),currLocation.getY());
            if(loc != null){
                breedShark(loc.getX(),loc.getY());              
            }
            ((WaTorWorldCell) grid[currLocation.getX()][currLocation.getY()]).setBreedTime(sharkBreedTime);
        }
    }

    public void updateFish(int x, int y){
        Cell[][] grid = myGrid.getGrid();        
        ((WaTorWorldCell) grid[x][y]).setBreedTime(((WaTorWorldCell) grid[x][y]).getBreedTime() - 1);
        Location loc = getRandomEmptyNeighbor(x,y);
        if(loc != null){
            moveFish(new Location(x,y),loc);
            x = loc.getX();
            y = loc.getY();
        }
        if(((WaTorWorldCell) grid[x][y]).getBreedTime() == 0){
            Location l = getRandomEmptyNeighbor(x,y);
            if(l != null){
                breedFish(l.getX(),l.getY());              
            }
            ((WaTorWorldCell) grid[x][y]).setBreedTime(fishBreedTime);
        }

    }

    public Location getRandomEmptyNeighbor(int x, int y){
        ArrayList<Location> locations = new ArrayList<Location>();
        Cell[][] grid = myGrid.getGrid();        
        if(x != 0 && ((WaTorWorldCell) grid[x-1][y]).getState() == State.EMPTY){
            locations.add(new Location(x-1,y));
        }
        if(x != gridLength-1 && ((WaTorWorldCell) grid[x+1][y]).getState() == State.EMPTY){
            locations.add(new Location(x+1,y));
        }
        if(y != gridLength-1 &&((WaTorWorldCell) grid[x][y+1]).getState() == State.EMPTY){
            locations.add(new Location(x,y+1));
        }
        if(y != 0 && ((WaTorWorldCell) grid[x][y-1]).getState() == State.EMPTY){
            locations.add(new Location(x,y-1));
        }
        Collections.shuffle(locations);
        if(locations.size() > 0){
            return locations.get(0);
        }
        return null;
    }

    public void moveFish(Location source, Location dest){
        myGrid.getCell(dest.getX(), dest.getY()).setState(myGrid.getCell(source.getX(), source.getY()).getState());
        myGrid.getCell(dest.getX(), dest.getY()).setBreedTime(myGrid.getCell(source.getX(), source.getY()).getBreedTime());
        killCell(source.getX(),source.getY());
    }
    
    public void moveShark(Location source, Location dest){
        moveShark(source,dest);
        myGrid.getCell(dest.getX(), dest.getY()).setStarveTime(myGrid.getCell(source.getX(), source.getY()).getStarveTime());
    }

    public void breedFish(int x, int y){
        myGrid.getCell(x, y).setState(State.FISH);
        myGrid.getCell(x, y).setBreedTime(fishBreedTime);
    }

    public void breedShark(int x, int y){
        myGrid.getCell(x, y).setState(State.SHARK);
        myGrid.getCell(x, y).setStarveTime(starveTime);
        myGrid.getCell(x, y).setBreedTime(sharkBreedTime);
    }

    public void killCell(int x, int y){
        myGrid.getCell(x, y).setState(State.EMPTY);
        myGrid.getCell(x, y).setStarveTime(-1);
        myGrid.getCell(x, y).setBreedTime(-1);
    }
    
    public void createGraph(){
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setMinorTickVisible(false);
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
        fishSeries = new XYChart.Series();
        sharkSeries = new XYChart.Series();
        //populating the series with data
        //series.getData().add(new XYChart.Data(1, 23));
        lineChart.getData().add(fishSeries);
        lineChart.getData().add(sharkSeries);
        lineChart.setLayoutX(100);
        lineChart.setPrefSize(500, 100);
        lineChart.setLegendVisible(false);
        rootElement.getChildren().add(lineChart);

    }
    
    public void updateGraph(){
        fishSeries.getData().add(new XYChart.Data(stepCount, fishCount));
        sharkSeries.getData().add(new XYChart.Data(stepCount, sharkCount));
    }


    @Override
    public void step () {
        updateState();
        updateGraph();
        stepCount++;

    }

}
