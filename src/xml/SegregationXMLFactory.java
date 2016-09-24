package xml;

import org.w3c.dom.Element;

public class SegregationXMLFactory extends SimulationXMLFactory{

	public SegregationXMLFactory(Element rootElement) {
		super(rootElement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSimulationType() {
		// TODO Auto-generated method stub
		return "Segregation";
	}
	
    public double getSatisfyThreshold(){
        return Double.parseDouble(getTextValue("satisfyThresh")); //not sure of the variable naming
    }
    
    public double getPercA(){
        return Double.parseDouble(getTextValue("percentageA"));
    }
    public double getPercB(){
        return Double.parseDouble(getTextValue("percentageB"));
    }
    public double getPercEmpty(){
        return Double.parseDouble(getTextValue("percentageEmpty"));
    }
    

}
