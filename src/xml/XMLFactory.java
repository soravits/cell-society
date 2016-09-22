package xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The base for all XMLFactories, regardless of what they actually return.
 *
 * @author Rhondu Smithwick
 */
public abstract class XMLFactory {

    private final Element rootElement;

    public XMLFactory (Element rootElement) {
        this.rootElement = rootElement;
    }

    /**
     * @return if this is a valid XML file for this specific XML object type
     */
    public abstract boolean isValidFile ();

    /**
     * Get the value of an attribute.
     * <p>
     * Why might it not be good design to include this and getTextValue in this class?
     * What happens when you need more transformation methods?
     * </p>
     *
     * @param attributeName the attribute's name
     * @return the value of the attribute
     */
    protected String getAttribute (String attributeName) {
        return rootElement.getAttribute(attributeName);
    }

    /**
     * Get the text value of a node.
     * <p>
     * Why might it not be good design to include this and getAttribute in this class?
     * What happens when I need more transformation methods?
     * </p>
     * <p>
     * Assumes you want the textValue of the first node with this tagName.
     * </p>
     *
     * @param tagName the name of the node's tag
     * @return the value of the node
     */
    protected String getTextValue (String tagName) {
        String textValue = null;
        NodeList nodeList = rootElement.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            textValue = nodeList.item(0).getTextContent();
        }
        return textValue;
    }
}
