package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import javax.xml.namespace.QName;
import java.io.IOException;

public interface FieldDescriptor {
    String getName();

    boolean isPrimitive();

    int getDepth();

    String getValue();

    void setValue(String value);

    String getFormName();

    QName getQname();

    void serialize(SerializationContext context) throws IOException;

    boolean isValid();
}
