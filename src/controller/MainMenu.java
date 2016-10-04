
package controller;

import foragingants.ForagingAntsInput;
import foragingants.ForagingAntsSimulation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import spreadingoffire.*;
import sugarscape.*;
import waterworld.*;
import segregation.*;
import slimemolds.SlimeMoldsInput;
import slimemolds.SlimeMoldsSimulation;
import foragingants.*;
import gameoflife.*;
import xml.*;
import base.Simulation;
import base.Simulation.CellType;
import controller.MainMenu.MenuItem;
import base.UserInput;

/**
 * @author Brian, Delia, Soravit
 */
public class MainMenu {
	
	public static final int MAIN_MENU_WIDTH = 700;
	public static final int MAIN_MENU_HEIGHT = 600;	
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final String xmlFileRoot = "data/xml/rules.xml";
	public static final String badxmlFileRoot = "data/xml/brokenRules.xml";
	public static LinearGradient gradient = new LinearGradient(0d, 1d, 1d, 0d, true, 
			CycleMethod.NO_CYCLE, 
			new Stop[]{
			new Stop(0, Color.WHITE),
			new Stop(0.15, Color.TURQUOISE),
			new Stop(0.3, Color.LIGHTGREEN),
			new Stop(0.45, Color.GREEN),
			new Stop(0.6, Color.LIGHTGREEN),
			new Stop(0.75, Color.TURQUOISE),
			new Stop(0.9, Color.WHITE),
			new Stop(1, Color.WHITE)
	});

	public static int DimensionsOfGrid = 10;
	public static Stage stage, stageNew;

	/**
	 * @param s
	 */
	public MainMenu(Stage s) {
		stage = s;		
	}

	/**
	 * Sets scene to the new main menu scene
	 */
	public void display() {
		stage.setTitle("Cell Society");
		stage.setScene(new Scene(setUpWindow()));
	}

	/**
	 * @author Brian
	 *
	 */
	public class MenuItem extends StackPane {
		/**
		 * @param Name
		 */
		public MenuItem(String Name) {
			Rectangle bg = new Rectangle(300, 50);
			bg.setOpacity(0.4);

			Text optionText = new Text(Name);
			optionText.setFill(Color.web("rgba(204,204,204,1.0)"));
			optionText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 22));

			setAlignment(Pos.CENTER);
			getChildren().addAll(bg,optionText);

			//On Mouse Over	For Option/Mode Hovering
			setOnMouseEntered(event -> {
				bg.setFill(gradient);
				optionText.setFill(Color.WHITE);
			});

			//On Mouse Exit (Return buttons to default state)	
			setOnMouseExited(event -> {
				bg.setFill(Color.BLACK);
				optionText.setFill(Color.web("rgba(204,204,204,1.0)"));
			});

			//Change button appearance when they are pressed
			setOnMousePressed(event -> {
				bg.setFill(Color.DARKVIOLET);
			});

			setOnMouseReleased(event -> {
				bg.setFill(gradient);
				XMLParser parser = new XMLParser();
				UserInput input;
				
				if(Name == null) {
					System.out.println("null");
				}
				String stageTitle = "";

				switch(Name) {
				case "FOREST BURNING":
					stageTitle = Name;
					stageNew = new Stage();
					stageNew.setTitle(stageTitle);
					FireXMLFactory factory = new FireXMLFactory(
							parser.getRootElement(xmlFileRoot));
					SpreadingOfFireSimulation myFire = new SpreadingOfFireSimulation(
							factory.getGridSize(), factory.getProbCatch(), CellType.SQUARE); 
					input = new SpreadingOfFireInput(stageNew, factory, myFire);
					break;
				case "GAME OF LIFE":
					stageTitle = Name;
					stageNew = new Stage();
					stageNew.setTitle(stageTitle);
					GameOfLifeXMLFactory GoLFactory = new GameOfLifeXMLFactory(
							parser.getRootElement(xmlFileRoot));
					GameOfLifeSimulation myGoL = new GameOfLifeSimulation(
							GoLFactory.getGridSize(), GoLFactory.getPercentageAlive(), CellType.SQUARE); 
					input = new GameOfLifeInput(stageNew, GoLFactory, myGoL);
					break;
				case "SEGREGATION":
					stageTitle = Name;
					stageNew = new Stage();
					stageNew.setTitle(stageTitle);
					SegregationXMLFactory segfactory = new SegregationXMLFactory(
							parser.getRootElement(xmlFileRoot));
					SegregationSimulation mySeg = new SegregationSimulation(segfactory.getGridSize(), 
							segfactory.getSatisfyThreshold(), segfactory.getPercA(), 
							segfactory.getPercB(), segfactory.getPercEmpty(),CellType.SQUARE);

					input = new SegregationInput(stageNew, segfactory, mySeg);
					break;
				case "SUGARSCAPE":
					stageTitle = Name;
					stageNew = new Stage();
					stageNew.setTitle(stageTitle);
					SugarScapeXMLFactory sugarfactory = new SugarScapeXMLFactory(
							parser.getRootElement(xmlFileRoot));
					SugarScapeSimulation mySugar = new SugarScapeSimulation(
							sugarfactory.getSugarGridSize(), 
							sugarfactory.getMaxSugarPerPatch(), sugarfactory.getTotalAgents(),
							sugarfactory.getGrowBackRate(), sugarfactory.getAgentMaxCarbs(), 
							sugarfactory.getAgentMinCarbs(), sugarfactory.getAgentMetabRate(), 
							sugarfactory.getAgentVision(), sugarfactory.getPreset(), CellType.SQUARE);
					input = new SugarScapeInput(stageNew, sugarfactory, mySugar);
					break;

				case "PREDATOR PREY":
					stageTitle = Name;
					stageNew = new Stage();
					stageNew.setTitle(stageTitle);
					WaTorWorldXMLFactory WWXMLFactory = new WaTorWorldXMLFactory(
							parser.getRootElement(xmlFileRoot));
					WaTorWorldSimulation myWater = new WaTorWorldSimulation(
							WWXMLFactory.getGridSize(), WWXMLFactory.getFracFish(), 
							WWXMLFactory.getFracShark(), WWXMLFactory.getFishBreedTime(), 
							WWXMLFactory.getSharkBreedTime(), WWXMLFactory.getStarveTime(),
							CellType.SQUARE);
					input = new WaTorWorldInput(stageNew, WWXMLFactory, myWater);
					break;

				case "FORAGING ANTS":
					stageTitle = Name;
					stageNew = new Stage();
					stageNew.setTitle(stageTitle);
					ForagingAntsXMLFactory FAXMLFactory = new ForagingAntsXMLFactory(
							parser.getRootElement(xmlFileRoot));
					ForagingAntsSimulation myForagingAntsSimulation = new ForagingAntsSimulation(
							FAXMLFactory.getGridSize(), CellType.SQUARE, FAXMLFactory.getDuration(),
							FAXMLFactory.getNestLocationRow(), FAXMLFactory.getNestLocationColumn(),
							FAXMLFactory.getFoodSourceLocationRow(), FAXMLFactory.getFoodSourceLocationColumn(),
							FAXMLFactory.getMaxAntsPerSim(), FAXMLFactory.getMaxAntsPerLocation(),
							FAXMLFactory.getAntLifetime(), FAXMLFactory.getNumInitialAnts(),
							FAXMLFactory.getAntsBornPerStep(), FAXMLFactory.getMinPheromone(),
							FAXMLFactory.getMaxPheromone(), FAXMLFactory.getEvapRatio(),
							FAXMLFactory.getDiffusionRatio());
					input = new ForagingAntsInput(stageNew, FAXMLFactory, myForagingAntsSimulation);
					break;
				case "SLIME MOLD":
					stageTitle = Name;
					stageNew = new Stage();
					stageNew.setTitle(stageTitle);
					SlimeXMLFactory slimeFactory = new SlimeXMLFactory(
							parser.getRootElement(xmlFileRoot));
					SlimeMoldsSimulation sim = new SlimeMoldsSimulation(slimeFactory.getGridSize(), 
							slimeFactory.getDiffusionAmt(), slimeFactory.getStepAmt(), 
							slimeFactory.getThreshold(), slimeFactory.getDissipateAmt(),
							slimeFactory.getProbMold(), CellType.SQUARE);

					input = new SlimeMoldsInput(stageNew, slimeFactory, sim);
					break;
				}

			});
		}//Closes MenuItem Object
	}//Closes MenuItem Declaration

	/**
	 * Sets up background, big text, as well as all of the option boxes and 
	 * Instructions window
	 */
	public Parent setUpWindow() {
		Pane gameWindow = new Pane();
		gameWindow.setPrefSize(MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
		Image background = new Image(getClass().getClassLoader()
				.getResourceAsStream("BackgroundCellSoc.jpg")); 
		ImageView backgroundImageMainScreen = new ImageView(background);
		backgroundImageMainScreen.setFitWidth(MAIN_MENU_WIDTH + 50);
		backgroundImageMainScreen.setFitHeight(MAIN_MENU_HEIGHT);
		gameWindow.getChildren().add(backgroundImageMainScreen); 

		BigGameNameText titleText = new BigGameNameText("CELL SOCIETY");
		titleText.setTranslateX(125);
		titleText.setTranslateY(120);
		gameWindow.getChildren().add(titleText);

		OptionContainer optionList = new OptionContainer(
				new MenuItem("FOREST BURNING"),
				new MenuItem("PREDATOR PREY"),
				new MenuItem("SEGREGATION"),
				new MenuItem("GAME OF LIFE"),
				new MenuItem("FORAGING ANTS"),
				new MenuItem("SUGARSCAPE"),
				new MenuItem("SLIME MOLD"));
		optionList.setTranslateX(200);
		optionList.setTranslateY(200);
		gameWindow.getChildren().add(optionList);

		return gameWindow;
	}

	/**
	 * @author Brian
	 *
	 */
	private static class BigGameNameText extends StackPane {
		/**
		 * @param Name
		 */
		public BigGameNameText(String Name) {
			Text titleText = new Text(Name);
			titleText.setFont(Font.font("Rockwell", FontWeight.BOLD, 60));
			titleText.setFill(gradient);
			getChildren().add(titleText);
		}
	}

	/**
	 * @author Brian
	 *
	 */
	public static class OptionContainer extends VBox {
		/**
		 * @param items
		 */
		public OptionContainer(MenuItem...items) {
			getChildren().add(createline());

			for(MenuItem item : items) {
				getChildren().addAll(item,createline());
			}
		}

		/**
		 * @return
		 */
		private Line createline() {
			Line sep = new Line();
			sep.setEndX(300);
			sep.setStroke(Color.DARKGREY);
			return sep;
		}
	}

}