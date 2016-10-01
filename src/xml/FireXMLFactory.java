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
    	double probCatch = 0.5;
    	try{
    		probCatch = Double.parseDouble(getTextValue("probCatch"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find ignition probability in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of ignition probability in your XML is incorrect. ");
    	}
    	if(probCatch < 0){
    		errorPopup("Ignition probability cannot be negative.");
    		probCatch = 0.15;
    	}
        return probCatch;
    }



}
