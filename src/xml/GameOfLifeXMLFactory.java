package xml;

import org.w3c.dom.Element;

public class GameOfLifeXMLFactory extends SimulationXMLFactory{

    public GameOfLifeXMLFactory (Element rootElement) {
        super(rootElement);
    }

    @Override
    public String getSimulationType () {
        return "Game Of Life";
    }

}


