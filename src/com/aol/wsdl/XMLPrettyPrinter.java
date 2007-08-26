package com.aol.wsdl;

import org.jdom.Element;
import org.jdom.JDOMException;

import java.util.List;

public class XMLPrettyPrinter {

    public static String prettyPrint(org.jdom.Element element, int indent) throws JDOMException {
        String toString = "";

        List<Element> elementContent = element.getChildren();
        for (org.jdom.Element tempElement : elementContent) {
            toString += createIndent(indent) + getObjectName(tempElement);

            if (hasChildren(tempElement)) {
                toString += "<br>" + prettyPrint(tempElement, indent + 1);
            } else {
                toString += tempElement.getText();
            }
            toString += "<br>";
        }

        return toString;
    }

    private static boolean hasChildren(Element element) {
        return element.getChildren().size() > 0;
    }

    private static String getObjectName(Element element) {
        String toString = "";

        boolean complexElement = hasChildren(element);

        if (complexElement) {
            toString += "<font color='blue'>";
        }

        toString += element.getName() + ": ";

        if (complexElement) {
            toString += element.getAttributes() + "<br></font>";
        }
        return toString;
    }

    private static String createIndent(int indent) {
        String retValue = "";
        for (int i = 0; i < indent; i++) {
            retValue += "&nbsp;&nbsp;&nbsp;";
        }
        return retValue;
    }

}
