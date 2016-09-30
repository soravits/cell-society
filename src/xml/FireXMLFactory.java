package xml;

import org.w3c.dom.Element;

/**
 * @author Soravit
 *
 */
public class FireXMLFactory extends SimulationXMLFactory { 

    /**
     * @param rootElement
     */
    public FireXMLFactory (Element rootElement) {
        super(rootElement);
    }

    /* (non-Javadoc)
     * @see xml.SimulationXMLFactory#getSimulationType()
     */
    @Override
    public String getSimulationType () {
        return "Spreading of Fire";
    }

    /**
     * @return
     */
    public double getProbCatch() {
        return Double.parseDouble(getTextValue("probCatch"));
    }



}
