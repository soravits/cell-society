package xml;

import org.w3c.dom.Element;

/**
 * @author Brian
 *
 */
public class GameOfLifeXMLFactory extends SimulationXMLFactory{

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

}


