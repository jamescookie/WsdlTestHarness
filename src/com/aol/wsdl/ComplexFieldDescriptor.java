package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.ArrayList;

public class ComplexFieldDescriptor extends ArrayList<FieldDescriptor> implements FieldDescriptor {
    protected final String name;
    protected final int depth;
    protected final String formName;
    protected final QName qname;

    public ComplexFieldDescriptor(String name, String formName, int depth, QName qname) {
        this.name = name;
        this.formName = formName;
        this.depth = depth;
        this.qname = qname;
    }

    public String getName() {
        return name;
    }

    public boolean isPrimitive() {
        return false;
    }

    public int getDepth() {
        return depth;
    }

    public String getValue() {
        return null;
    }

    public void setValue(String value) {
        //do nothing
    }

    public String getFormName() {
        return formName;
    }

    public QName getQname() {
        return qname;
    }

    public void serialize(SerializationContext context) throws IOException {
        context.startElement(getQname(), null);

        for (FieldDescriptor child : this) {
            context.setItemQName(null);
            child.serialize(context);
        }

        context.endElement();
    }

    public boolean isValid() {
        boolean retValue = true;
        for (FieldDescriptor child : this) {
            if (!child.isValid()) {
                retValue = false;
            }
        }
        return retValue;
    }

}
