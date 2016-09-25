package xml;

import org.w3c.dom.Element;

public class WaTorWorldXMLFactory extends SimulationXMLFactory{

    public WaTorWorldXMLFactory (Element rootElement) {
        super(rootElement);
    }

    @Override
    public String getSimulationType () {
        return "Wator World";
    }
    
    public double getFracFish(){
        return Double.parseDouble(getTextValue("fracFish"));
    }
    
    public double getFracShark(){
        return Double.parseDouble(getTextValue("fracShark"));
    }
    
    public int getFishBreedTime(){
        return Integer.parseInt(getTextValue("fishBreedTime"));
    }
    
    public int getSharkBreedTime(){
        return Integer.parseInt(getTextValue("sharkBreedTime"));
    }
    
    public int getStarveTime(){
        return Integer.parseInt(getTextValue("starveTime"));
    }
    


}
