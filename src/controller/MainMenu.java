
package controller;

import base.Simulation;
import gameoflife.GameOfLifeSimulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import spreadingoffire.SpreadingOfFireSimulation;
import xml.FireXMLFactory;
import xml.GameOfLifeXMLFactory;
import xml.XMLParser;

public class MainMenu {
    public static final int MAIN_MENU_WIDTH = 700;
    public static final int MAIN_MENU_HEIGHT = 600;	
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    public static int DimensionsOfGrid = 10;
    public static Stage stage;
    private static final String xmlFileRoot = "data/xml/rules.xml";

    public MainMenu(Stage s){
        stage= s;		
    }

    /**
     * Sets scene to the new main menu scene
     */
    public void display() {
        stage.setTitle("Cell Society");
        stage.setScene(new Scene(setUpWindow()));
    }

    public class MenuItem extends StackPane{
        private Scene scene = null;
        private KeyFrame frame = null;
        public MenuItem(String Name) {	
            LinearGradient gradient = new LinearGradient(0d,1d,1d,0d, true, 
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

            Rectangle bg = new Rectangle(300,50);
            bg.setOpacity(0.4);

            Text optionText = new Text(Name);
            optionText.setFill(Color.web("rgba(204,204,204,1.0)"));
            optionText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD,22));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,optionText);

            //On Mouse Over	For Option/Mode Hovering
            setOnMouseEntered(event ->{
                bg.setFill(gradient);
                optionText.setFill(Color.WHITE);
            });

            //On Mouse Exit (Return buttons to default state)	
            setOnMouseExited(event ->{
                bg.setFill(Color.BLACK);
                optionText.setFill(Color.web("rgba(204,204,204,1.0)"));
            });

            //Change button appearance when they are pressed
            setOnMousePressed(event ->{
                bg.setFill(Color.DARKVIOLET);
            });

            setOnMouseReleased(event ->{
                bg.setFill(gradient);
                Simulation mySim = null;
                XMLParser parser = new XMLParser();
                switch(Name){
                    case "FOREST FIRE":
                    	FireXMLFactory factory = new FireXMLFactory(parser.getRootElement(xmlFileRoot));
                        mySim = new SpreadingOfFireSimulation(factory.getGridSize(),factory.getProbCatch()); 
                        break;
                    case "GAME OF LIFE":
                      GameOfLifeXMLFactory GoLFactory = new GameOfLifeXMLFactory(parser.getRootElement(xmlFileRoot));
                      mySim = new GameOfLifeSimulation(GoLFactory.getGridSize()); 
                      break;
                }
                
                scene = mySim.init(stage);
                stage.setScene(scene);
                stage.show();

            });		
        }//Closes MenuItem Object
    }//Closes MenuItem Declaration

    /**
     * Sets up background, big text, as well as all of the option boxes and 
     * Instructions window
     */
    public Parent setUpWindow() {
        Pane gameWindow = new Pane();
        gameWindow.setPrefSize(MAIN_MENU_WIDTH,MAIN_MENU_HEIGHT);
        Image background = new Image(getClass().getClassLoader()
                                     .getResourceAsStream("BackgroundCellSoc.jpg")); 
        ImageView backgrondImageMainScreen = new ImageView(background);
        backgrondImageMainScreen.setFitWidth(MAIN_MENU_WIDTH+50);
        backgrondImageMainScreen.setFitHeight(MAIN_MENU_HEIGHT);;
        gameWindow.getChildren().add(backgrondImageMainScreen); 

        BigGameNameText titleText = new BigGameNameText("CELL SOCIETY");
        titleText.setTranslateX(125);
        titleText.setTranslateY(200);
        gameWindow.getChildren().add(titleText);

        OptionContainer optionList = new OptionContainer(
                                                         new MenuItem("FOREST FIRE"),
                                                         new MenuItem("PREDATOR PREY"),
                                                         new MenuItem("SEGREGATION"),
                                                         new MenuItem("GAME OF LIFE"));
        //new MenuItem("READ XML FILE"));
        optionList.setTranslateX(200);
        optionList.setTranslateY(350);
        gameWindow.getChildren().add(optionList);

        return gameWindow;
    }

    private static class BigGameNameText extends StackPane{
        public BigGameNameText(String Name){
            Text titleText = new Text(Name);
            titleText.setFont(Font.font("Rockwell", FontWeight.BOLD,60));
            LinearGradient gradient = new LinearGradient(0d,1d,1d,0d, true, 
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
            titleText.setFill(gradient);
            getChildren().add(titleText);
        }
    }

    private static class OptionContainer extends VBox{
        public OptionContainer(MenuItem...items) {
            getChildren().add(createline());

            for(MenuItem item: items) {
                getChildren().addAll(item,createline());
            }
        }

        private Line createline() {
            Line sep = new Line();
            sep.setEndX(300);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }

}
