package com.aol.wsdl;

import javax.xml.namespace.QName;

public interface FieldDescriptor extends Parameters {
    String getName();

    boolean isPrimitive();

    int getDepth();

    String getValue();

    void setValue(String value);

    String getFormName();

    QName getQname();

}
