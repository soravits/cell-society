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

	private Pane rootElement;
	private Simulation sim;
	private Cell[][] grid;
	private int rowLength;
	private int columnLength;
	private int initialX;
	private int initialY;
	private int sizeOfCell;

	private String buttonFill = "-fx-background-color: linear-gradient(#0079b3, #00110e);" + 
			"-fx-background-radius: 20;" + 
			"-fx-text-fill: white;";
	private String overButton = "-fx-background-color: linear-gradient(#00110e, #0079b3);" + 
			"-fx-background-radius: 20;" + 
			"-fx-text-fill: white;";

	public enum gridEdgeType{finite, toroidal};
	private gridEdgeType edgeType;

	/**
	 * @param gridLength;
	 * @param sizeOfCell
	 * @param rootElement
	 * @param initialX
	 * @param initialY
	 */
	public Grid(int gridLength, int sizeOfCell, Pane rootElement, int initialX, 
			int initialY, gridEdgeType edgeType) {
		this.rootElement = rootElement;
		this.rowLength = gridLength;        
		this.columnLength = gridLength;
		this.sizeOfCell = sizeOfCell;
		this.rootElement = rootElement;
		this.initialX = initialX;
		this.initialY = initialY;
		this.edgeType = edgeType;
		this.grid = new Cell[columnLength][rowLength];
	}

	/**
	 * @param type
	 */
	public abstract void initializeGrid(CellType type);


	/**
	 * @return
	 */
	public int getRowLength () {
		return rowLength;
	}

	/**
	 * @return
	 */
	public int getColumnLength () {
		return columnLength;
	}

	/**
	 * @return
	 */
	public gridEdgeType getEdgeType() {
		return edgeType;
	}

	/**
	 * @param edgeType
	 */
	public void setEdgeType(gridEdgeType edgeType) {
		this.edgeType = edgeType;
	}


	/**
	 * @return
	 */
	public Pane getRootElement() {
		return rootElement;
	}

	/**
	 * @param sim
	 */
	public void setSimulationProfile(Simulation sim) {
		this.sim = sim;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Cell getCell(int row, int col) {
		return grid[row][col];
	}

	/**
	 * @param row
	 * @param col
	 * @param c
	 */
	public void setCell(int row, int col, Cell c) {
		grid[row][col] = c;
	}

	//REFACTORING: Should we have a get general neighbor method in which
	//the parameter specifies the direction (nsew, or diagonal ones)

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getNorthernNeighbor(int row, int col) {
		if(row != 0) {
			return new Location(row - 1, col);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			return new Location(columnLength - 1, col);
		}
		return null;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getSouthernNeighbor(int row, int col) {
		if(row != columnLength - 1) {
			return new Location(row + 1, col);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			return new Location(0, col);
		}
		return null;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getEasternNeighbor(int row, int col) {
		if(col != rowLength - 1) {
			return new Location(row, col + 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			return new Location(row, 0);
		}
		return null;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getWesternNeighbor(int row, int col) {
		if(col != 0) {
			return new Location(row, col - 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			return new Location(row, rowLength - 1);
		}
		return null;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getNorthwesternNeighbor(int row, int col) {
		if(row != 0 && col != 0) {
			return new Location(row - 1, col - 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			if(row == 0 && col != 0) {
				return new Location(columnLength - 1, col - 1);
			}
			else if (row == 0 && col != 0) {
				return new Location(row - 1, rowLength - 1);
			}
			else {
				return new Location(columnLength - 1, rowLength - 1);
			}
		}
		return null;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getNortheasternNeighbor(int row, int col) {
		if(row != 0 && col != rowLength - 1) {
			return new Location(row - 1, col + 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			if(row == 0 && col != rowLength - 1) {
				return new Location(columnLength - 1, col + 1);
			}
			else if(row != 0 && col == rowLength - 1) {
				return new Location (row - 1, 0);
			}
			else {
				return new Location(columnLength - 1, 0);
			}
		}
		return null;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getSouthwesternNeighbor(int row, int col) {
		if(row != columnLength - 1 && col != 0) {
			return new Location(row + 1, col - 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			if(row == columnLength - 1 && col != 0) {
				return new Location(0, col - 1);
			}
			else if(row != columnLength - 1 && col == 0) {
				return new Location (row + 1, rowLength - 1);
			}
			else {
				return new Location(0, rowLength - 1);
			}
		}
		return null;
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getSoutheasternNeighbor(int row, int col) {
		if(row != columnLength - 1 && col != rowLength - 1) {
			return new Location(row + 1, col + 1);
		}
		else if(edgeType == gridEdgeType.toroidal) {
			if(row == columnLength - 1 && col != rowLength - 1) {
				return new Location(0, col + 1);
			}
			else if(row != columnLength - 1 && col == rowLength - 1) {
				return new Location (row + 1, 0);
			}
			else {
				return new Location(0, 0);
			}
		}
		return null;
	}

	/**
	 * @param location
	 * @return
	 */
	public ArrayList<Location> getAllNeighbors(Location location) {
		int row = location.getRow();
		int col = location.getColumn();
		ArrayList<Location> neighbors = new ArrayList<Location>();

		neighbors.add(getNorthernNeighbor(row, col));
		neighbors.add(getNortheasternNeighbor(row, col));
		neighbors.add(getEasternNeighbor(row, col));
		neighbors.add(getSoutheasternNeighbor(row, col));
		neighbors.add(getSouthernNeighbor(row, col));
		neighbors.add(getSouthwesternNeighbor(row, col));
		neighbors.add(getWesternNeighbor(row, col));
		neighbors.add(getNorthwesternNeighbor(row, col));

		return neighbors;
	}

	/**
	 * @return
	 */
	public int getInitialX () {
		return initialX;
	}

	/**
	 * @return
	 */
	public int getInitialY () {
		return initialY;
	}

	/**
	 * @return
	 */
	public int getSizeOfCell () {
		return sizeOfCell;
	}

	/**
	 * @param width
	 * @param height
	 */
	public void setBackground(int width, int height) {
		Image background = new Image(getClass().getClassLoader().
				getResourceAsStream("BackgroundCellSoc.jpg")); 
		ImageView backgroundImageMainScreen = new ImageView(background);
		backgroundImageMainScreen.setFitWidth(width + 50);
		backgroundImageMainScreen.setFitHeight(height);
		rootElement.getChildren().add(backgroundImageMainScreen);
	}

	/**
	 * @param text
	 * @param x
	 * @param y
	 * @return
	 */
	private Button createSimButton(String text, int x, int y) {
		Button startSim = new Button(text);
		startSim.setStyle(buttonFill);
		startSim.setOnMouseEntered(e -> mouseIn(startSim));
		startSim.setOnMouseExited(e -> mouseOut(startSim));
		startSim.setTranslateX(x);
		startSim.setTranslateY(y);

		return startSim;
	}

	/**
	 * @param b
	 */
	private void mouseIn(Button b) {
		b.setStyle(overButton);
	}

	/**
	 * @param b
	 */
	private void mouseOut(Button b) {
		b.setStyle(buttonFill);
	}

	/**
	 * 
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
		rootElement.getChildren().add(startSim);

		// STEP SIMULATION BUTTON BELOW
		Button stepSim = createSimButton("Step", 20, 250);
		stepSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sim.step();
			}
		});
		rootElement.getChildren().add(stepSim);

		// PAUSE SIMULATION BUTTON BELOW
		Button pauseSim = createSimButton("Pause", 20, 300);
		pauseSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sim.stopSimulation();
			}
		});
		rootElement.getChildren().add(pauseSim);

		// RESUME SIMULATION BUTTON BELOW
		Button resumeSim = createSimButton("Resume", 20, 350);
		resumeSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sim.resumeSimulation();
			}
		});
		rootElement.getChildren().add(resumeSim);

		Button makeEdgesFinite = createSimButton("Make edges \nfinite", 20, 400);
		makeEdgesFinite.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setEdgeType(gridEdgeType.finite);
			}
		});
		rootElement.getChildren().add(makeEdgesFinite);

		Button makeEdgesToroidal = createSimButton("Make edges \ntoroidal", 20, 470);
		makeEdgesToroidal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setEdgeType(gridEdgeType.toroidal);
			}
		});
		rootElement.getChildren().add(makeEdgesToroidal);
	}
}