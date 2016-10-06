package xml;

import org.w3c.dom.Element;

/**
 * @author Soravit
 * Error checking- Delia
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
     * @return percentage of grid that will be fish
     */
    public double getFracFish() {
    	double fracFish = 0.4;
    	try{
    		fracFish = Double.parseDouble(getTextValue("fracFish"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find fish population percentage in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the fish population percentage in your XML is incorrect. ");
    	}
    	if(fracFish < 0){
    		errorPopup("Fish population percentage cannot be negative.");
    		fracFish = 0.4;
    	}
    	if(fracFish > 100){
    		errorPopup("To observe the effects of a predator-prey system, " 
    				+ "fish population percentage cannot be greater than 100.");
    		fracFish = 0.4;
    	}
        return fracFish;
        
    }

    /**
     * @return percentage of grid that will be sharks
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
     * @return How many step cycles until a fish breeds
     */
    public int getFishBreedTime() {
    	int fishBreed = 3;
    	try{
    		fishBreed = Integer.parseInt(getTextValue("fishBreedTime"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find fish breed time in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the fish breed time in your XML is incorrect. ");
    	}
    	if(fishBreed < 0){
    		errorPopup("Fish breed time cannot be negative.");
    		fishBreed = 3;
    	}
        return fishBreed;
    }

    /**
     * @return How many step cycles until a shark breeds
     */
    public int getSharkBreedTime() {
    	int sharkBreed = 20;
    	try{
    		sharkBreed = Integer.parseInt(getTextValue("sharkBreedTime"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find shark breed time in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the shark breed time in your XML is incorrect. ");
    	}
    	if(sharkBreed < 0){
    		errorPopup("Shark breed time cannot be negative.");
    		sharkBreed = 20;
    	}
        return sharkBreed;
    }

    /**
     * @return  How many step cycles until a shark starves
     */
    public int getStarveTime() {
    	int starveTime = 3;
    	try{
    		starveTime = Integer.parseInt(getTextValue("starveTime"));
    	}
    	catch(NullPointerException e){
    		errorPopup("Could not find starve time in XML.");
    	}
    	catch(NumberFormatException e){
    		errorPopup("The format of the starve time in your XML is incorrect. ");
    	}
    	if(starveTime < 0){
    		errorPopup("Starve time cannot be negative.");
    		starveTime = 3;
    	}
        return starveTime;
    }
}
