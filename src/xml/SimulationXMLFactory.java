package xml;

import org.w3c.dom.Element;

import java.util.Objects;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * An XMLFactory that gives back a Person object.
 *
 * @author Rhondu Smithwick
 */
public abstract class SimulationXMLFactory extends XMLFactory {

	private Alert alert;
	private String defaultVal = " Default value assigned.";

    public SimulationXMLFactory (Element rootElement) {
        super(rootElement);
    }

    @Override
    public boolean isValidFile () {
        return Objects.equals(getAttribute("SimulationType"), getSimulationType());
    }

    public int getGridSize() {
    	int gridSize = 20;
    	try{
    		gridSize = Integer.parseInt(getTextValue("gridSize"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find grid size in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the grid size in your XML is incorrect. ");
    	}
    	if(gridSize < 0){
    		errorPopup("Grid size cannot be negative.");
    		gridSize = 20;
    	}
        return gridSize;
    }
    
	public void errorPopup(String errorText){
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("XML Error");

		String s = errorText + defaultVal;
		alert.setContentText(s);
		alert.showAndWait();
		//		    String msg = "Invalid text entered: ";

	}

    public abstract String getSimulationType();
}
