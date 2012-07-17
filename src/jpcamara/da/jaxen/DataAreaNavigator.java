package jpcamara.da.jaxen;

import jpcamara.da.DataAreaNode;
import org.jaxen.*;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.util.SingleObjectIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Allows navigation of data areas using XPath, through the magic of Jaxen.
 */
public class DataAreaNavigator extends DefaultNavigator implements NamedAccessNavigator {
    @Override
    public Iterator getChildAxisIterator(Object contextNode, String localName, String namespacePrefix,
                                         String namespaceURI) throws UnsupportedAxisException {
        DataAreaElement element = (DataAreaElement)contextNode;
        int offset = element.getOffset(), newOffset = offset;
        int length = element.getLength();
        byte[] payload = element.getPayload();

        for (DataAreaNode child : element.getDefinition().getChildren()) {
            if (localName.equals(child.getName())) {
                int occurs = child.getOccurs();
                int newLength = child.getLength();
                if (occurs == 1) {
                    DataAreaElement childElement =
                        new DataAreaElement(element, localName, payload, newOffset, newLength, child);
                    return new SingleObjectIterator(childElement);
                }
                
//                int newOffset = offset;
                List<DataAreaElement> elements = new ArrayList<DataAreaElement>();
                for (int i = 0; i < occurs; i++) {
                    DataAreaElement childElement =
                            new DataAreaElement(element, localName, payload, newOffset, newLength, child);
                    elements.add(childElement);
                    newOffset += child.getLength();
                }
                return new DataAreaElementIterator(element, elements.iterator());
            } else {
                newOffset += child.getLength() * child.getOccurs(); //move to next offset
            }
        }

        throw new IllegalArgumentException(String.format("[%s] is not a valid child of [%s]", localName, element.getName()));
    }

    @Override
    public Iterator getAttributeAxisIterator(Object o, String s, String s1, String s2) throws UnsupportedAxisException {
        return JaxenConstants.EMPTY_ITERATOR; //no concept of attributes
    }

    @Override
    public String getElementNamespaceUri(Object o) {
        return "";
    }

    @Override
    public String getElementName(Object o) {
        return ((DataAreaElement)o).getName();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getElementQName(Object o) {
        return "";
    }

    @Override
    public String getAttributeNamespaceUri(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAttributeName(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAttributeQName(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isDocument(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isElement(Object o) {
        return o instanceof DataAreaElement;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAttribute(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isNamespace(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isComment(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isText(Object o) {
        return o instanceof String;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isProcessingInstruction(Object o) {
        return false;
    }

    @Override
    public String getCommentStringValue(Object o) {
        return null;
    }

    @Override
    public String getElementStringValue(Object o) {
        return null;
    }

    @Override
    public String getAttributeStringValue(Object o) {
        return o.toString();
    }

    @Override
    public String getNamespaceStringValue(Object o) {
        return o.toString();
    }

    @Override
    public String getTextStringValue(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getNamespacePrefix(Object o) {
        return null;
    }

    @Override
    public XPath parseXPath(String xpath) throws SAXPathException {
        return new BaseXPath(xpath, this);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String translateNamespacePrefixToUri(String prefix, Object context) {
        return null;
    }

    @Override
    public short getNodeType(Object node) {
        return 0;
    }

    @Override
    public Object getDocument(String uri) throws FunctionCallException {
        return null;
    }

    @Override
    public java.lang.String getProcessingInstructionTarget(java.lang.Object obj) {
        return null;
    }

    @Override
    public java.lang.String getProcessingInstructionData(java.lang.Object obj) {
        return null;
    }
}
