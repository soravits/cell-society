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
        return Double.parseDouble(getTextValue("fracFish"));
    }

    /**
     * @return
     */
    public double getFracShark() {
        return Double.parseDouble(getTextValue("fracShark"));
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
