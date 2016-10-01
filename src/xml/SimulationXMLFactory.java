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
        return Integer.parseInt(getTextValue("gridSize"));
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
