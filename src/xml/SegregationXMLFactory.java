package xml;

import org.w3c.dom.Element;

/**
 * @author Delia
 *
 */
public class SegregationXMLFactory extends SimulationXMLFactory {

    /**
     * @param rootElement
     */
    public SegregationXMLFactory(Element rootElement) {
        super(rootElement);
    }


    @Override
    public String getSimulationType() {
        return "Segregation";
    }

    /**
     * @return double value for percentage of identical neighbors to satisfy a cell
     */
    public double getSatisfyThreshold() {
        return Double.parseDouble(getTextValue("satisfyThresh"));
    }

    /**
     * @return percentage of inhabited cells that will be type A
     */
    public double getPercA() {
        return Double.parseDouble(getTextValue("percentageA"));
    }
    
    /**
     * @return percentage of inhabited cells that will be type B
     */
    public double getPercB() {
        return Double.parseDouble(getTextValue("percentageB"));
    }
    
    /**
     * @return percentage of grid that is empty
     */
    public double getPercEmpty() {
        return Double.parseDouble(getTextValue("percentageEmpty"));
    }
}
