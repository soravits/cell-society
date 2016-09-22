package xml;

import org.w3c.dom.Element;

public class FireXMLFactory extends SimulationXMLFactory{

    public FireXMLFactory (Element rootElement) {
        super(rootElement);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getSimulationType () {
        // TODO Auto-generated method stub
        return "Spreading of Fire";
    }
    
    public double getProbCatch(){
        return Double.parseDouble(getTextValue("probCatch"));
    }
    


}
