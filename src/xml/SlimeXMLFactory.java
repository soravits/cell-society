package xml;

import org.w3c.dom.Element;

public class SlimeXMLFactory extends SimulationXMLFactory{

    /**
     * @param rootElement
     */
    public SlimeXMLFactory(Element rootElement) {
        super(rootElement);
    }


    @Override
    public String getSimulationType() {
        return "SlimeMolds";
    }

    /**
     * @return double value for percentage of identical neighbors to satisfy a cell
     */
    public double getThreshold() {
    	double thresh = 1.5; //default value if xml is wrong
    	try{
    		thresh = Double.parseDouble(getTextValue("threshold"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find threshold in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the threshold in your XML is incorrect. ");
    	}
    	if(thresh < 0){
    		errorPopup("Satisfaction cannot be negative.");
    		thresh = 0.4;
    	}
    	
    	return thresh;
    }
    
    /**
     * @return
     */
    public double getProbMold() {
    	double probMold = 0.05;
    	try{
    		probMold = Double.parseDouble(getTextValue("probMold"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find ignition probability in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of ignition probability in your XML is incorrect. ");
    	}
    	if(probMold < 0){
    		errorPopup("Ignition probability cannot be negative.");
    		probMold = 0.05;
    	}
        return probMold;
    }

    /**
     * @return percentage of inhabited cells that will be type A
     */
    public double getDiffusionAmt() {
    	double diffusionAmt = 0.7; //default value if xml is wrong
    	try{
    		diffusionAmt = Double.parseDouble(getTextValue("diffusionAmt"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(diffusionAmt < 0){
    		errorPopup("Percentage A cannot be negative.");
    		diffusionAmt = 0.5;
    	}
    	
    	return diffusionAmt;
    }
    /**
     * @return percentage of inhabited cells that will be type A
     */
    public double getStepAmt() {
    	double stepAmt = 0.7; //default value if xml is wrong
    	try{
    		stepAmt = Double.parseDouble(getTextValue("stepAmt"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(stepAmt < 0){
    		errorPopup("Percentage A cannot be negative.");
    		stepAmt = 0.5;
    	}
    	
    	return stepAmt;
    }
    
    public double getDissipateAmt() {
    	double dissipateAmt = 0.7; //default value if xml is wrong
    	try{
    		dissipateAmt = Double.parseDouble(getTextValue("dissipateAmt"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(dissipateAmt < 0){
    		errorPopup("Percentage A cannot be negative.");
    		dissipateAmt = 0.5;
    	}
    	
    	return dissipateAmt;
    }
    
}
