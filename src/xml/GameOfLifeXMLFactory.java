package xml;

import org.w3c.dom.Element;

/**
 * @author Brian
 *
 */
public class GameOfLifeXMLFactory extends SimulationXMLFactory {

    /**
     * @param rootElement
     */
    public GameOfLifeXMLFactory (Element rootElement) {
        super(rootElement);
    }

    @Override
    public String getSimulationType () {
        return "Game Of Life";
    }
    
    /**
     * @return double value for percentage of identical neighbors to satisfy a cell
     */
    public double getPercentageAlive() {
    	double thresh = 0.5; //default value if xml is wrong
    	try{
    		thresh = Double.parseDouble(getTextValue("percentageAlive"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentAlive in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the percentAlive in your XML is incorrect. ");
    	}
    	if(thresh < 0){
    		errorPopup("percentAlive cannot be negative.");
    		thresh = 0.4;
    	}
    	if(thresh > 1){
    		errorPopup("To observe the effects of Game Of Life, " 
    				+ "percentAlive cannot be more than 1.");
    		thresh = 0.4;
    	}
    	
    	return thresh;
    }

}


