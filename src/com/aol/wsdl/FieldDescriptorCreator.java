package com.aol.wsdl;

import org.apache.axis.wsdl.symbolTable.ElementDecl;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.apache.axis.wsdl.symbolTable.TypeEntry;

import javax.xml.namespace.QName;
import java.util.Vector;

public class FieldDescriptorCreator {
    static FieldDescriptor createFieldDescriptors(Vector<Parameter> list) throws ClassNotFoundException {
        FieldDescriptor returnParameters = new FieldDescriptor(false, null, null, null, 0, null);
        for (Parameter parameter : list) {
            returnParameters.add(createFieldDescriptor("", parameter.getType(), parameter.getName(), parameter.getQName(), 0));
        }
        return returnParameters;
    }

    static FieldDescriptor createFieldDescriptor(String nameUpToNow, TypeEntry typeEntry, String name, QName qName, int depth) throws ClassNotFoundException {
        FieldDescriptor descriptor;
        if (typeEntry.isBaseType()) {
            descriptor = createFieldDescriptor(name, qName, nameUpToNow + name, typeEntry.isBaseType(), depth, typeEntry.getQName().getLocalPart());
        } else {
            descriptor = createFieldDescriptor(name, qName, nameUpToNow + name, typeEntry.isBaseType(), depth, null);
            addFieldDescriptors(typeEntry, descriptor, descriptor.getFormName() + ".");
        }
        return descriptor;
    }

    static void addFieldDescriptors(TypeEntry typeEntry, FieldDescriptor descriptor, String nameUpToNow) throws ClassNotFoundException {
        Vector containedElements = typeEntry.getContainedElements();
        for (Object obj : containedElements) {
            ElementDecl containedElement = (ElementDecl) obj;
            QName qName = containedElement.getQName();
            String localPart = qName.getLocalPart();
            localPart = localPart.substring(localPart.lastIndexOf('>') + 1, localPart.length());
            FieldDescriptor innerDescriptor;
            if (containedElement.getType().isBaseType()) {
                innerDescriptor = createFieldDescriptor(
                        nameUpToNow,
                        containedElement.getType(),
                        localPart,
                        qName,
                        descriptor.getDepth() + 1);
            } else {
                innerDescriptor = createFieldDescriptor(
                        nameUpToNow + localPart + ".",
                        containedElement.getType(),
                        localPart,
                        qName,
                        descriptor.getDepth() + 1);
            }
            descriptor.add(innerDescriptor);
        }
    }

    static FieldDescriptor createFieldDescriptor(String name, QName qname, String formName, boolean primitive, int depth, String javaType) throws ClassNotFoundException {
        return new FieldDescriptor(primitive, qname, name, formName, depth, javaType);
    }



}
