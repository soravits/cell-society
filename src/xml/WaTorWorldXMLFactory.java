package xml;

import org.w3c.dom.Element;

/**
 * @author Soravit
 *
 */
public class WaTorWorldXMLFactory extends SimulationXMLFactory {

    /**
     * @param rootElement
     */
    public WaTorWorldXMLFactory (Element rootElement) {
        super(rootElement);
    }

    @Override
    public String getSimulationType () {
        return "Wator World";
    }

    /**
     * @return
     */
    public double getFracFish() {
    	double fracFish = 0.15;
    	try{
    		fracFish = Double.parseDouble(getTextValue("percentageEmpty"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find percentage empty in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of percentage empty in your XML is incorrect. ");
    	}
    	if(fracFish < 0){
    		errorPopup("Percentage empty cannot be negative.");
    		fracFish = 0.15;
    	}
    	if(fracFish > 100){
    		errorPopup("To observe the effects of segregation, " 
    				+ "Percentage empty cannot be greater than 100.");
    		fracFish = 0.15;
    	}
        return fracFish;
        return Double.parseDouble(getTextValue("fracFish"));
    }

    /**
     * @return
     */
    public double getFracShark() {

    	double fracShark = 0.1;
    	try{
    		fracShark = Double.parseDouble(getTextValue("fracShark"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find shark population percentage in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the shark population percentage in your XML is incorrect. ");
    	}
    	if(fracShark < 0){
    		errorPopup("Shark population percentage cannot be negative.");
    		fracShark = 0.15;
    	}
    	if(fracShark > 100){
    		errorPopup("To observe the effects of a predator-prey system, " 
    				+ "shark population percentage cannot be greater than 100.");
    		fracShark = 0.15;
    	}
        return fracShark;
    }

    /**
     * @return
     */
    public int getFishBreedTime() {
        return Integer.parseInt(getTextValue("fishBreedTime"));
    }

    /**
     * @return
     */
    public int getSharkBreedTime() {
        return Integer.parseInt(getTextValue("sharkBreedTime"));
    }

    /**
     * @return
     */
    public int getStarveTime() {
        return Integer.parseInt(getTextValue("starveTime"));
    }
}
