package com.aol.wsdl;

import javax.xml.namespace.QName;

interface FieldDescriptor extends Parameters {
    String getName();

    boolean isPrimitive();

    int getDepth();

    String getValue();

    String getFormName();

    QName getQname();
}
