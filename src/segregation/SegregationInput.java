package segregation;

import xml.SegregationXMLFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import base.UserInput;

public class SegregationInput extends UserInput{
	private Scene segScene;
	private Segregation segregation;
	private GridPane grid = new GridPane();;

	public SegregationInput(Stage s, SegregationXMLFactory factory, Segregation mySim) {
		super(s);
		this.segregation = mySim;

		//		System.out.println(stage);
		// TODO Auto-generated constructor stub
	}

	public void selectPercEmpty() {

	}

	public void selectPercA() {
		//lowest, highest, default value, increment size
		Spinner<Double> percASpinner = new Spinner<>(0.05, 1.0, 0.5, 0.05);
        percASpinner.setEditable(true);
		segScene = new Scene(grid, INPUT_MENU_WIDTH, INPUT_MENU_HEIGHT);
		
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
//		grid.add(setBackground(), 0, 0);
		grid.add(new Label("% Color A"), 0, 1);
		grid.add(percASpinner, 1, 1);
		
		
		stage.setScene(segScene);
		stage.show();
	}

	public void selectPercB() {

	}

	public void selectSatisfyThresh() {

	}

	public void selectColors(){

	}

	@Override
	public void manualInput() {
		// TODO Auto-generated method stub
//		selectPercEmpty();
		selectPercA();
//		selectPercB();
//		selectSatisfyThresh();
//		selectColors();
	}

	@Override
	public void startXMLSimulation() {
		// TODO Auto-generated method stub
		segScene = segregation.init(stage);
		stage.setScene(segScene);
		stage.show();
	}
}
