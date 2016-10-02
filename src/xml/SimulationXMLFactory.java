package xml;

import org.w3c.dom.Element;

import java.util.Objects;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * An XMLFactory that gives back a Person object.
 *
 * @author Rhondu Smithwick, Delia 
 */
public abstract class SimulationXMLFactory extends XMLFactory {

	private Alert alert;
	private String defaultVal = " Default value assigned.";

    /**
     * @param rootElement
     */
    public SimulationXMLFactory (Element rootElement) {
        super(rootElement);
    }

    /* (non-Javadoc)
     * @see xml.XMLFactory#isValidFile()
     */
    @Override
    public boolean isValidFile () {
        return Objects.equals(getAttribute("SimulationType"), getSimulationType());
    }

    /**
     * Search XML for grid size, account for formatting and other errors
     * @return gridSize		int value for number of cells on one side of square grid
     */
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
    
	/**
	 * Generates error popup window displaying message for why the value is wrong.
	 * Notifies user that default value will be used instead
	 * @param errorText		String describing the error
	 */
	public void errorPopup(String errorText) {
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("XML Error");

		String s = errorText + defaultVal;
		alert.setContentText(s);
		alert.showAndWait();
	}

    /**
     * Looks for which type of simulation it is
     * @return	String for the name
     */
    public abstract String getSimulationType();
}