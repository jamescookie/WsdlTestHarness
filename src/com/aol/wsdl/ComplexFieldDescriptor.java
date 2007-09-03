package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import javax.xml.namespace.QName;
import java.io.IOException;

class ComplexFieldDescriptor extends OperationParameters implements FieldDescriptor {
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

}
