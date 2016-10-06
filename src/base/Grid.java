package base;

import java.util.ArrayList;

import base.Simulation.CellType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


/**
 * @author Soravit, Brian, Delia
 *
 */
public abstract class Grid {

    public static final String BACKGROUND_IMAGE = "BackgroundCellSoc.jpg";
    public enum gridEdgeType{finite, toroidal};
    
    private gridEdgeType edgeType;
    private int gridLength;
	private int initialX;
	private int initialY;
	private int sizeOfCell;
    private Pane rootElement;
	private String buttonFill = "-fx-background-color: linear-gradient(#0079b3, #00110e);" + 
			"-fx-background-radius: 20;" + 
			"-fx-text-fill: white;";
	private String overButton = "-fx-background-color: linear-gradient(#00110e, #0079b3);" + 
			"-fx-background-radius: 20;" + 
			"-fx-text-fill: white;";
    private Cell[][] grid;
    private Simulation sim;

	/**
	 * @param gridLength The length of a side of the grid in terms of cells
	 * @param sizeOfCell The size of the cells in the grid
	 * @param rootElement The JavaFX pane
	 * @param initialX The initial x location of the grid
	 * @param initialY The initial Y location of the grid
     * @param edgeType Whether the grid has finite or toroidal edges
	 */
	public Grid(int gridLength, int sizeOfCell, Pane rootElement, int initialX,
			int initialY, gridEdgeType edgeType) {
		this.rootElement = rootElement;
		this.gridLength = gridLength;
		this.sizeOfCell = sizeOfCell;
		this.rootElement = rootElement;
		this.initialX = initialX;
		this.initialY = initialY;
		this.edgeType = edgeType;
		this.grid = new Cell[gridLength][gridLength];
	}

	/**
	 * @param type The shape of the cells in the grid
	 */
	public abstract void initializeGrid(CellType type);

    /**
     * @return The length of a side of the grid
     */
    public int getGridLength() {
        return gridLength;
    }


    /**
	 * @return Whether grid edges are finite or toroidal
	 */
	public gridEdgeType getEdgeType() {
		return edgeType;
	}

	/**
	 * @param edgeType The desired edge type the grid uses
	 */
	public void setEdgeType(gridEdgeType edgeType) {
		this.edgeType = edgeType;
	}


	/**
	 * @return The JavaFX pane
	 */
	public Pane getRootElement() {
		return rootElement;
	}

	/**
	 * @param sim The simulation to be referenced by the grid
	 */
	public void setSimulationProfile(Simulation sim) {
		this.sim = sim;
	}

	/**
	 * @param location The location of the desired cell
	 * @return The cell at said location
	 */
	public Cell getCell(Location location) {
		return grid[location.getRow()][location.getColumn()];
	}

	/**
	 * @param location The location to be set in the grid
	 * @param cell The cell to be put in said location
	 */
	public void setCell(Location location, Cell cell) {
		grid[location.getRow()][location.getColumn()] = cell;
	}

	/**
	 * @param location A specified location in the grid
	 * @return The location to the north of the given location
	 */
	public Location getNorthernNeighbor(Location location) {
		if(location.getRow() != 0) {
			return new Location(location.getRow() - 1, location.getColumn());
		}
		else if(edgeType == gridEdgeType.toroidal) {
			return new Location(gridLength - 1, location.getColumn());
		}
		return null;
	}

    /**
     * @param location A specified location in the grid
     * @return The location to the south of the given location
     */
	public Location getSouthernNeighbor(Location location) {
		if(location.getRow() != gridLength - 1) {
			return new Location(location.getRow() + 1, location.getColumn());
		}
		else if(edgeType == gridEdgeType.toroidal) {
			return new Location(0, location.getColumn());
		}
		return null;
	}

    /**
     * @param location A specified location in the grid
     * @return The location to the east of the given location
     */
	public Location getEasternNeighbor(Location location) {
		if(location.getColumn() != gridLength - 1) {
			return new Location(location.getRow(), location.getColumn() + 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			return new Location(location.getRow(), 0);
		}
		return null;
	}

    /**
     * @param location A specified location in the grid
     * @return The location to the west of the given location
     */
	public Location getWesternNeighbor(Location location) {
		if(location.getColumn() != 0) {
			return new Location(location.getRow(), location.getColumn() - 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			return new Location(location.getRow(), gridLength - 1);
		}
		return null;
	}

    /**
     * @param location A specified location in the grid
     * @return The location to the northwest of the given location
     */
	public Location getNorthwesternNeighbor(Location location) {
        int row = location.getRow();
        int col = location.getColumn();
		if(row != 0 && col != 0) {
			return new Location(row - 1, col - 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			if(row == 0 && col != 0) {
				return new Location(gridLength - 1, col - 1);
			}
			else if (row == 0 && col != 0) {
				return new Location(row - 1, gridLength - 1);
			}
			else {
				return new Location(gridLength - 1, gridLength - 1);
			}
		}
		return null;
	}

    /**
     * @param location A specified location in the grid
     * @return The location to the northeast of the given location
     */
	public Location getNortheasternNeighbor(Location location) {
        int row = location.getRow();
        int col = location.getColumn();
		if(row != 0 && col != gridLength - 1) {
			return new Location(row - 1, col + 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			if(row == 0 && col != gridLength - 1) {
				return new Location(gridLength - 1, col + 1);
			}
			else if(row != 0 && col == gridLength - 1) {
				return new Location (row - 1, 0);
			}
			else {
				return new Location(gridLength - 1, 0);
			}
		}
		return null;
	}

    /**
     * @param location A specified location in the grid
     * @return The location to the southwest of the given location
     */
	public Location getSouthwesternNeighbor(Location location) {
        int row = location.getRow();
        int col = location.getColumn();

		if(row != gridLength - 1 && col != 0) {
			return new Location(row + 1, col - 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			if(row == gridLength - 1 && col != 0) {
				return new Location(0, col - 1);
			}
			else if(row != gridLength - 1 && col == 0) {
				return new Location (row + 1, gridLength - 1);
			}
			else {
				return new Location(0, gridLength - 1);
			}
		}
		return null;
	}

    /**
     * @param location A specified location in the grid
     * @return The location to the southeast of the given location
     */
	public Location getSoutheasternNeighbor(Location location) {
        int row = location.getRow();
        int col = location.getColumn();
		if(row != gridLength - 1 && col != gridLength - 1) {
			return new Location(row + 1, col + 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			if(row == gridLength - 1 && col != gridLength - 1) {
				return new Location(0, col + 1);
			}
			else if(row != gridLength - 1 && col == gridLength - 1) {
				return new Location (row + 1, 0);
			}
			else {
				return new Location(0, 0);
			}
		}
		return null;
	}

	/**
	 * @param location A specified location in the grid
	 * @return A list of all neighbors
	 */
	public ArrayList<Location> getAllNeighbors(Location location) {
		ArrayList<Location> neighbors = new ArrayList<Location>();

		neighbors.add(getNorthernNeighbor(location));
		neighbors.add(getNortheasternNeighbor(location));
		neighbors.add(getEasternNeighbor(location));
		neighbors.add(getSoutheasternNeighbor(location));
		neighbors.add(getSouthernNeighbor(location));
		neighbors.add(getSouthwesternNeighbor(location));
		neighbors.add(getWesternNeighbor(location));
		neighbors.add(getNorthwesternNeighbor(location));

		return neighbors;
	}

	public ArrayList<Location> getAllCardinalNeighbors(Location location) {
		ArrayList<Location> neighbors = new ArrayList<Location>();

		neighbors.add(getNorthernNeighbor(location));
		neighbors.add(getEasternNeighbor(location));
		neighbors.add(getSouthernNeighbor(location));
		neighbors.add(getWesternNeighbor(location));

		return neighbors;
	}

	/**
	 * @return The initial x position of the grid
	 */
	public int getInitialX () {
		return initialX;
	}

	/**
	 * @return The initial y position of the grid
	 */
	public int getInitialY () {
		return initialY;
	}

	/**
	 * @return The size of the cells in the grid
	 */
	public int getSizeOfCell () {
		return sizeOfCell;
	}

	/**
	 * @param width The desired width of the background
	 * @param height The desired height of the background
     * Sets the background of the grid
	 */
	public void setBackground(int width, int height) {
		Image background = new Image(getClass().getClassLoader().
				getResourceAsStream(BACKGROUND_IMAGE));
		ImageView backgroundImageMainScreen = new ImageView(background);
		backgroundImageMainScreen.setFitWidth(width + 50);
		backgroundImageMainScreen.setFitHeight(height);
		rootElement.getChildren().add(backgroundImageMainScreen);
	}
	

	/**
	 * Sets up the buttons in every simulation
	 */
	public void setUpButtons() {
		// START SIMULATION BUTTON BELOW
		Button startSim = createSimButton("Start", 20, 200);
		startSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sim.startSimulation();
			}
		});

		// STEP SIMULATION BUTTON BELOW
		Button stepSim = createSimButton("Step", 20, 250);
		stepSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sim.step();
			}
		});

		// PAUSE SIMULATION BUTTON BELOW
		Button pauseSim = createSimButton("Pause", 20, 300);
		pauseSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sim.stopSimulation();
			}
		});

		// RESUME SIMULATION BUTTON BELOW
		Button resumeSim = createSimButton("Resume", 20, 350);
		resumeSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sim.resumeSimulation();
			}
		});
		
		//MAKE EDGES FINITE -> DEFAULT
		Button makeEdgesFinite = createSimButton("Make edges \nfinite", 20, 400);
		makeEdgesFinite.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setEdgeType(gridEdgeType.finite);
			}
		});
		
		//MAKES EDGES TOROIDAL SO THEY WRAP
		Button makeEdgesToroidal = createSimButton("Make edges \ntoroidal", 20, 470);
		makeEdgesToroidal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setEdgeType(gridEdgeType.toroidal);
			}
		});
	}

	private Button createSimButton(String text, int x, int y) {
		Button newButton = new Button(text);
		newButton.setStyle(buttonFill);
		newButton.setOnMouseEntered(e -> mouseIn(newButton));
		newButton.setOnMouseExited(e -> mouseOut(newButton));
		newButton.setTranslateX(x);
		newButton.setTranslateY(y);
		
		rootElement.getChildren().add(newButton);
		return newButton;
	}

	private void mouseIn(Button button) {
		button.setStyle(overButton);
	}

	private void mouseOut(Button button) {
		button.setStyle(buttonFill);
	}

}