package foragingants;

/**
 * Created by Soravit on 10/3/2016.
 */

import base.Simulation;
import base.UserInput;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Stage;
import base.Simulation.CellType;
import base.UserInput;
import xml.ForagingAntsXMLFactory;

public class ForagingAntsInput extends UserInput {
    private Scene foragingAntsScene;
    private String lifeString = "Foraging Ants";
    private ForagingAntsSimulation foragingAnts;

    public ForagingAntsInput(Stage s, ForagingAntsXMLFactory factory, ForagingAntsSimulation mySim) {
        super(s);
        this.foragingAnts = mySim;
    }


    @Override
    public void startXMLSimulation() {
        foragingAntsScene = foragingAnts.init(stage, Simulation.CellType.SQUARE);
        stage.setScene(foragingAntsScene);
        stage.show();

    }


    @Override
    public void startManualSimulation(Simulation.CellType type) {
//        foragingAnts = new ForagingAntsSimulation(getGridSize(), type);
        foragingAntsScene = foragingAnts.init(stage, type);
        stage.setScene(foragingAntsScene);
        stage.show();
    }


    @Override
    public void generateNodes() {
        selectGridSize();
        getGrid().add(beginHexButton(lifeString), 0, 1);
        getGrid().add(beginTriangleButton(lifeString), 0, 2);
        getGrid().add(beginSquareButton(lifeString), 0, 3);
    }
}