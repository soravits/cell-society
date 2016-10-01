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
    	double thresh = 0.4; //default value if xml is wrong
    	try{
    		thresh = Double.parseDouble(getTextValue("satisfyThresh"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find satisfaction threshold in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the satisfaction threshold in your XML is incorrect. ");
    	}
    	if(thresh < 0){
    		errorPopup("Satisfaction threshold cannot be negative.");
    		thresh = 0.4;
    	}
    	if(thresh > 1){
    		errorPopup("To observe the effects of segregation, " 
    				+ "satisfaction threshold cannot be more than 1.");
    		thresh = 0.4;
    	}
    	
    	return thresh;
    }

    /**
     * @return percentage of inhabited cells that will be type A
     */
    public double getPercA() {
    	double percA = 0.5; //default value if xml is wrong
    	try{
    		percA = Double.parseDouble(getTextValue("percentageA"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(percA < 0){
    		errorPopup("Percentage A cannot be negative.");
        	percA = 0.5;
    	}
    	if(percA > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage A cannot be greater than 100.");
        	percA = 0.5;
    	}
    	
    	return percA;
    }
    
    /**
     * @return percentage of inhabited cells that will be type B
     */
    public double getPercB() {
    	double percB = 0.5;
    	try{
    		percB = Double.parseDouble(getTextValue("percentageB"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage B in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage B in your XML is incorrect. ");
    	}
    	if(percB < 0){
    		errorPopup("Percentage B cannot be negative.");
    		percB = 0.5;
    	}
    	if(percB > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage B cannot be greater than 100.");
    		percB = 0.5;
    	}
        return percB;
    }
    
    /**
     * @return percentage of grid that is empty
     */
    public double getPercEmpty() {
    	double percEmpty = 0.15;
    	try{
    		percEmpty = Double.parseDouble(getTextValue("percentageEmpty"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage empty in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage empty in your XML is incorrect. ");
    	}
    	if(percEmpty < 0){
    		errorPopup("Percentage empty cannot be negative.");
    		percEmpty = 0.15;
    	}
    	if(percEmpty > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage empty cannot be greater than 100.");
    		percEmpty = 0.15;
    	}
        return percEmpty;
    }
}
