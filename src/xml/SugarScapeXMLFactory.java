package xml;

import org.w3c.dom.Element;

public class SugarScapeXMLFactory  extends SimulationXMLFactory {

	public SugarScapeXMLFactory(Element rootElement) {
		super(rootElement);
	}

	@Override
	public String getSimulationType() {
		return "SugarScape";
	}

    public int getSugarGridSize() {
    	return 51;
    }

    /**
     */
    public int getMaxSugarPerPatch() {
    	int sugar = 4; //default value if xml is wrong
    	try{
    		sugar = Integer.parseInt(getTextValue("maxSugarPerPatch"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(sugar < 0){
    		errorPopup("Percentage A cannot be negative.");
        	sugar = 4;
    	}
    	if(sugar > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage A cannot be greater than 100.");
        	sugar = 4;
    	}
    	
    	return sugar;
    }
    
    /**
     * @return percentage of inhabited cells that will be type B
     */
    /**
     */
    public int getTotalAgents() {
    	int agents = 300; //default value if xml is wrong
    	try{
    		agents = Integer.parseInt(getTextValue("totalAgents"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(agents < 0){
    		errorPopup("Percentage A cannot be negative.");
        	agents = 300;
    	}
    	if(agents > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage A cannot be greater than 100.");
        	agents = 300;
    	}
    	
    	return agents;
    }
    
    /**
     */
    public int getGrowBackRate() {
    	int grow = 1; //default value if xml is wrong
    	try{
    		grow = Integer.parseInt(getTextValue("growBackRate"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(grow < 0){
    		errorPopup("Percentage A cannot be negative.");
        	grow = 1;
    	}
    	if(grow > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage A cannot be greater than 100.");
        	grow = 1;
    	}
    	
    	return grow;
    }
    
    /**
     */
    public int getAgentMaxCarbs() {
    	int carbs = 25; //default value if xml is wrong
    	try{
    		carbs = Integer.parseInt(getTextValue("agentMaxCarbs"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(carbs < 0){
    		errorPopup("Percentage A cannot be negative.");
        	carbs = 25;
    	}
    	if(carbs > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage A cannot be greater than 100.");
        	carbs = 25;
    	}
    	
    	return carbs;
    }
    
    /**
     */
    public int getAgentMinCarbs() {
    	int carbs = 4; //default value if xml is wrong
    	try{
    		carbs = Integer.parseInt(getTextValue("agentMinCarbs"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(carbs < 0){
    		errorPopup("Percentage A cannot be negative.");
        	carbs = 4;
    	}
    	if(carbs > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage A cannot be greater than 100.");
        	carbs = 4;
    	}
    	
    	return carbs;
    }
    
    /**
     */
    public int getMaxSugarPerPatch() {
    	int sugar = 4; //default value if xml is wrong
    	try{
    		sugar = Integer.parseInt(getTextValue("maxSugarPerPatch"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(sugar < 0){
    		errorPopup("Percentage A cannot be negative.");
        	sugar = 4;
    	}
    	if(sugar > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage A cannot be greater than 100.");
        	sugar = 4;
    	}
    	
    	return sugar;
    }
    
    /**
     */
    public int getMaxSugarPerPatch() {
    	int sugar = 4; //default value if xml is wrong
    	try{
    		sugar = Integer.parseInt(getTextValue("maxSugarPerPatch"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage A in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage A in your XML is incorrect. ");
    	}
    	if(sugar < 0){
    		errorPopup("Percentage A cannot be negative.");
        	sugar = 4;
    	}
    	if(sugar > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage A cannot be greater than 100.");
        	sugar = 4;
    	}
    	
    	return sugar;
    }
}
