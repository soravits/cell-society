package xml;

import org.w3c.dom.Element;

public class FireXMLFactory extends SimulationXMLFactory{

    public FireXMLFactory (Element rootElement) {
        super(rootElement);
    }

    @Override
    public String getSimulationType () {
        return "Spreading of Fire";
    }

    public double getProbCatch(){
        return Double.parseDouble(getTextValue("probCatch"));
    }



}
