package xml;

import org.w3c.dom.Element; 

/**
 * @author Delia
 *
 */
public class SugarScapeXMLFactory  extends SimulationXMLFactory {
	private static final int SUGARSCAPE_GRIDSIZE = 51;

	/**
	 * @param rootElement
	 */
	public SugarScapeXMLFactory(Element rootElement) {
		super(rootElement);
	}

	@Override
	public String getSimulationType() {
		return "SugarScape";
	}

	/**
	 * @return
	 */
	public int getSugarGridSize() {
		return SUGARSCAPE_GRIDSIZE;
	}

	/**
	 */
	/**
	 * @return
	 */
	public int getMaxSugarPerPatch() {
		int sugar = 4; //default value if xml is wrong
		try{
			sugar = Integer.parseInt(getTextValue("maxSugarPerPatch"));
		}
		catch(NullPointerException e){
			errorPopup("Could not find maximum patch sugar content in XML.");
		}
		catch(NumberFormatException e){
			errorPopup("The format of maximum patch sugar content in your XML is incorrect. ");
		}
		if(sugar < 0){
			errorPopup("Maximum patch sugar content cannot be negative.");
			sugar = 4;
		}
		if(sugar > 12){
			errorPopup("To observe the effects of sugarscape, " 
					+ " maximum patch sugar content cannot be greater than 100.");
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
			errorPopup("Could not find total agents in XML.");
		}
		catch(NumberFormatException e){
			errorPopup("The format of total agents in your XML is incorrect. ");
		}
		if(agents < 0){
			errorPopup("total agents cannot be negative.");
			agents = 300;
		}

		return agents;
	}

	/**
	 */
	/**
	 * @return
	 */
	public int getGrowBackRate() {
		int grow = 1; //default value if xml is wrong
		try{
			grow = Integer.parseInt(getTextValue("growBackRate"));
		}
		catch(NullPointerException e){
			errorPopup("Could not find growback rate in XML.");
		}
		catch(NumberFormatException e){
			errorPopup("The format of growback rate in your XML is incorrect. ");
		}
		if(grow < 0){
			errorPopup("Growback rate cannot be negative.");
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
	/**
	 * @return
	 */
	public int getAgentMaxCarbs() {
		int carbs = 25; //default value if xml is wrong
		try{
			carbs = Integer.parseInt(getTextValue("agentMaxCarbs"));
		}
		catch(NullPointerException e){
			errorPopup("Could not find agent max carbs in XML.");
		}
		catch(NumberFormatException e){
			errorPopup("The format of agent max carbs in your XML is incorrect. ");
		}
		if(carbs < 0){
			errorPopup("Agent max carbs cannot be negative.");
			carbs = 25;
		}

		return carbs;
	}

	/**
	 */
	/**
	 * @return
	 */
	public int getAgentMinCarbs() {
		int carbs = 4; //default value if xml is wrong
		try{
			carbs = Integer.parseInt(getTextValue("agentMinCarbs"));
		}
		catch(NullPointerException e){
			errorPopup("Could not find agent min carbs in XML.");
		}
		catch(NumberFormatException e){
			errorPopup("The format of agent min carbs in your XML is incorrect. ");
		}
		if(carbs < 0){
			errorPopup("Agent min carbs cannot be negative.");
			carbs = 4;
		}

		return carbs;
	}

	/**
	 */
	/**
	 * @return
	 */
	public int getAgentMetabRate() {
		int metab = 1; //default value if xml is wrong
		try{
			metab = Integer.parseInt(getTextValue("agentMetabRate"));
		}
		catch(NullPointerException e){
			errorPopup("Could not find agent metab rate in XML.");
		}
		catch(NumberFormatException e){
			errorPopup("The format of agent metab rate in your XML is incorrect. ");
		}
		if(metab < 0){
			errorPopup("Agent metab rate cannot be negative.");
			metab = 1;
		}
		return metab;
	}

	/**
	 */
	/**
	 * @return
	 */
	public int getAgentVision() {
		int vision = 4; //default value if xml is wrong
		try{
			vision = Integer.parseInt(getTextValue("agentVision"));
		}
		catch(NullPointerException e){
			errorPopup("Could not find vision in XML.");
		}
		catch(NumberFormatException e){
			errorPopup("The format of vision in your XML is incorrect. ");
		}
		if(vision < 0){
			errorPopup("Vision cannot be negative.");
			vision = 4;
		}
		if(vision > 51){
			errorPopup("To observe the effects of SugarScape, " 
					+ "vision cannot be greater than 51.");
			vision = 4;
		}

		return vision;
	}

	/**
	 * @return
	 */
	public int getPreset() {
		int preset = 1; //default value if xml is wrong
		try{
			preset = Integer.parseInt(getTextValue("preset"));
		}
		catch(NullPointerException e){
			errorPopup("Could not find presetin XML.");
		}
		catch(NumberFormatException e){
			errorPopup("The format of preset in your XML is incorrect. ");
		}
		if(preset < 1){
			errorPopup("Preset must be 1 or 2.");
			preset = 1;
		}
		if(preset > 2){
			errorPopup("Preset cannot be greater than 2.");
			preset = 1;
		}

		return preset;
	}
}
