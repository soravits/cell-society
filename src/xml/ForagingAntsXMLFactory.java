package xml;

import org.w3c.dom.Element;

/**
 * Created by Soravit on 10/2/2016.
 */
public class ForagingAntsXMLFactory extends SimulationXMLFactory{
    /**
     * @param rootElement
     */
    public ForagingAntsXMLFactory(Element rootElement) {
        super(rootElement);
    }

    /* (non-Javadoc)
     * @see xml.SimulationXMLFactory#getSimulationType()
     */
    @Override
    public String getSimulationType() {
        return "Foraging Ants";
    }

    /**
     * @return
     */
    public int getDuration(){
        int duration = 5000;
        try{
            duration = Integer.parseInt(getTextValue("simDuration"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the simulation duration in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the simulation duration in your XML is incorrect.");
        }
        if(duration < 0){
            errorPopup("Simulation duration cannot be negative.");
            duration = 5000;
        }
        return duration;
    }

    /**
     * @return
     */
    public int getNestLocationRow(){
        int row = 0;
        try{
            row = Integer.parseInt(getTextValue("nestLocationRow"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the nest location row in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the nest location row in your XML is incorrect.");
        }
        if(row < 0 || row > getGridSize() - 1){
            errorPopup("The nest location row in your XML is invalid for the specified grid size.");
            row = 0;
        }
        return row;
    }

    /**
     * @return
     */
    public int getNestLocationColumn(){
        int col = 0;
        try{
            col = Integer.parseInt(getTextValue("nestLocationColumn"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the nest location column in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the nest location column in your XML is incorrect.");
        }
        if(col < 0 || col > getGridSize() - 1){
            errorPopup("The nest location column in your XML is invalid for the specified grid size.");
            col = 0;
        }
        return col;
    }

    /**
     * @return
     */
    public int getFoodSourceLocationRow(){
        int row = getGridSize()/2;
        try{
            row = Integer.parseInt(getTextValue("foodSourceLocationRow"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the food source location row in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the food source location row in your XML is incorrect.");
        }
        if(row < 0 || row > getGridSize() - 1){
            errorPopup("The food source location row in your XML is invalid for the specified grid size.");
            row = getGridSize()/2;
        }
        return row;
    }

    /**
     * @return
     */
    public int getFoodSourceLocationColumn(){
        int col = getGridSize()/2;
        try{
            col = Integer.parseInt(getTextValue("foodSourceLocationColumn"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the food source location column in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the food source location column in your XML is incorrect.");
        }
        if(col < 0 || col > getGridSize() - 1){
            errorPopup("The food source location column in your XML is invalid for the specified grid size.");
            col = getGridSize()/2;
        }
        return col;
    }

    /**
     * @return
     */
    public int getMaxAntsPerSim(){
        int max = 1000;
        try{
            max = Integer.parseInt(getTextValue("maxAntsPerSim"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the max number of ants per simulation in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the max number of ants per simulation in your XML is incorrect.");
        }
        if(max < 0){
            errorPopup("The max number of ants per simulation cannot be negative.");
            max = 1000;
        }
        return max;
    }

    /**
     * @return
     */
    public int getMaxAntsPerLocation(){
        int max = 10;
        try{
            max = Integer.parseInt(getTextValue("maxAntsPerLocation"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the max number of ants per location in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the max number of ants per location in your XML is incorrect.");
        }
        if(max < 0){
            errorPopup("The max number of ants per location cannot be negative.");
            max = 10;
        }
        return max;
    }

    /**
     * @return
     */
    public int getAntLifetime(){
        int lifetime = 500;
        try{
            lifetime = Integer.parseInt(getTextValue("antLifetime"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the ant lifetime in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the ant lifetime in your XML is incorrect.");
        }
        if(lifetime < 0){
            errorPopup("The ant lifetime cannot be negative.");
            lifetime = 500;
        }
        return lifetime;
    }

    /**
     * @return
     */
    public int getNumInitialAnts(){
        int numAnts = 2;
        try{
            numAnts = Integer.parseInt(getTextValue("numInitialAnts"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the initial number of ants in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the initial number of ants in your XML is incorrect.");
        }
        if(numAnts < 0 || numAnts > getMaxAntsPerSim()){
            errorPopup("Your initial number of ants is invalid.");
            numAnts = 2;
        }
        return numAnts;
    }

    /**
     * @return
     */
    public int getAntsBornPerStep(){
        int antsBorn = 2;
        try{
            antsBorn = Integer.parseInt(getTextValue("AntsBornPerStep"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the number of ants born per step in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the number of ants born per step in your XML is incorrect. ");
        }
        if(antsBorn < 0 && antsBorn < getMaxAntsPerSim()){
            errorPopup("Your number of ants born per step is invalid.");
            antsBorn = 2;
        }
        return antsBorn;
    }

    /**
     * @return
     */
    public double getMinPheromone(){
        double minPheromone = 0.0;
        try{
            minPheromone = Double.parseDouble(getTextValue("minPheromone"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the minimum amount of pheromone in your XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the minimum amount of pheromone in your XML is incorrect. ");
        }
        if(minPheromone < 0){
            errorPopup("Your minimum amount of pheromone cannot be negative.");
            minPheromone = 0.0;
        }
        return minPheromone;
    }

    /**
     * @return
     */
    public double getMaxPheromone(){
        double maxPheromone = 1000.0;
        try{
            maxPheromone = Double.parseDouble(getTextValue("maxPheromone"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the maximum amount of pheromone in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the maximum amount of pheromone in your XML is incorrect. ");
        }
        if(maxPheromone < 0){
            errorPopup("Your maximum amount of pheromone cannot be negative.");
            maxPheromone = 1000.0;
        }
        return maxPheromone;
    }

    /**
     * @return
     */
    public double getEvapRatio(){
        double evapRatio = 0.001;
        try{
            evapRatio = Double.parseDouble(getTextValue("evapRatio"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the evaporation ratio in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the evaporation ratio in your XML is incorrect. ");
        }
        if(evapRatio < 0){
            errorPopup("Your evaporation ratio cannot be negative.");
            evapRatio = 0.001;
        }
        return evapRatio;
    }

    /**
     * @return
     */
    public double getDiffusionRatio(){
        double diffusionRatio = 0.001;
        try{
            diffusionRatio = Double.parseDouble(getTextValue("diffusionRatio"));
        }
        catch(NullPointerException e){
            errorPopup("Could not find the diffusion ratio in XML.");
        }
        catch(NumberFormatException e){
            errorPopup("The format of the diffusion ratio in your XML is incorrect. ");
        }
        if(diffusionRatio < 0){
            errorPopup("Your diffusion ratio cannot be negative.");
            diffusionRatio = 0.001;
        }
        return diffusionRatio;
    }
}