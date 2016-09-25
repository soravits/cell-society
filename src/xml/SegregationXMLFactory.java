package xml;

import org.w3c.dom.Element;

/**
 * @author Delia
 *
 */
public class SegregationXMLFactory extends SimulationXMLFactory{

    /**
     * @param rootElement
     */
    public SegregationXMLFactory(Element rootElement) {
        super(rootElement);
    }

    /* (non-Javadoc)
     * @see xml.SimulationXMLFactory#getSimulationType()
     */
    @Override
    public String getSimulationType() {
        return "Segregation";
    }

    /**
     * @return
     */
    public double getSatisfyThreshold(){
        return Double.parseDouble(getTextValue("satisfyThresh")); //not sure of the variable naming
    }

    /**
     * @return
     */
    public double getPercA(){
        return Double.parseDouble(getTextValue("percentageA"));
    }
    
    /**
     * @return
     */
    public double getPercB(){
        return Double.parseDouble(getTextValue("percentageB"));
    }
    
    /**
     * @return
     */
    public double getPercEmpty(){
        return Double.parseDouble(getTextValue("percentageEmpty"));
    }
}
