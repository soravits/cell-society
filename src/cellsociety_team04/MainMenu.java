package balloonDodge;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class MainMenu {
	public static final int mainMenuWIDTHOFSCREEN = 1300;
	public static final int mainMenuHEIGHTOFSCREEN = 750;	
	public static final int FRAMES_PER_SECOND = 30;
	public static final int MILLISECOND_DELAY = 250 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static Stage stage;
	
	private HashMap<String,String> IconToDescriptionMap = new HashMap<String,String>();
	private Rectangle infoFullWindow;
	private Button clearInfoWindowButton;

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
			LinearGradient gradient = new LinearGradient(0d,1d,1d,0d, true, CycleMethod.NO_CYCLE, 
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
				

				if(Name.equals("FOREST FIRE")){
					Simulation mySim = new Simulation(10);
					scene = mySim.init(stage,false);
					stage.setScene(scene);
			        stage.show();
				}
				
			});		
		}//Closes MenuItem Object
	}//Closes MenuItem Declaration
	
	/**
	 * Sets up background, big text, as well as all of the option boxes and Instructions window
	 */
	public Parent setUpWindow() {
		Pane gameWindow = new Pane();
		setUpMap();
		gameWindow.setPrefSize(mainMenuWIDTHOFSCREEN,mainMenuHEIGHTOFSCREEN);
		Image background = new Image(getClass().getClassLoader().getResourceAsStream("BackgroundCellSoc.jpg")); 
		ImageView backgrondImageMainScreen = new ImageView(background);
		backgrondImageMainScreen.setFitWidth(mainMenuWIDTHOFSCREEN);
		backgrondImageMainScreen.setFitHeight(mainMenuHEIGHTOFSCREEN);;
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
		optionList.setTranslateX(200);
		optionList.setTranslateY(350);
		gameWindow.getChildren().add(optionList);
		
		return gameWindow;
	}
		
	private void setUpMap(){
		IconToDescriptionMap.put("wb_life.png", "LIFE&Gives you an additional life! Most rare Power Up");
		IconToDescriptionMap.put("wb_speed.png", "SPEED UP&Slows the balloons down --- Can be good or bad, doesn't accelerate\n" + "balloon generation so it prevents accumulation of balloons");
		IconToDescriptionMap.put("wb_slow.png", "SLOW DOWN&Speeds up balloons --- Can be good or bad, doesn't accelerate\n" + "balloon generation so they're easier to dodge but accumulate easily");
		IconToDescriptionMap.put("wb_armor.png", "ARMOR&Temporarily shields your monkey, making it immune to all\n" + "balloons for 2.5 seconds, turns red when about to expire");
		IconToDescriptionMap.put("wb_big.png", "BIG&Makes your monkey bigger, generally bad but you can get \nit to challenge yourself");
		IconToDescriptionMap.put("wb_small.png", "SMALL&Makes your monkey smaller, good! Can only stack up to 3x");
	}
	
	private static class BigGameNameText extends StackPane{
		public BigGameNameText(String Name){
			Text titleText = new Text(Name);
			titleText.setFont(Font.font("Rockwell", FontWeight.BOLD,60));
			LinearGradient gradient = new LinearGradient(0d,1d,1d,0d, true, CycleMethod.NO_CYCLE, 
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
