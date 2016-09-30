package xml;

import org.w3c.dom.Element;

import java.util.Objects;


/**
 * An XMLFactory that gives back a Person object.
 *
 * @author Rhondu Smithwick
 */
public abstract class SimulationXMLFactory extends XMLFactory {

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

    public abstract String getSimulationType();
}
