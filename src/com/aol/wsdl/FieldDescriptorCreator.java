package com.aol.wsdl;

import org.apache.axis.wsdl.symbolTable.CollectionType;
import org.apache.axis.wsdl.symbolTable.ElementDecl;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.apache.axis.wsdl.symbolTable.TypeEntry;

import javax.xml.namespace.QName;
import java.util.Vector;

public class FieldDescriptorCreator {
    static OperationParameters createOperationParameters(Vector<Parameter> list) throws ClassNotFoundException {
        OperationParameters operationParameters = new OperationParameters();
        for (Parameter parameter : list) {
            operationParameters.add(createFieldDescriptor("", parameter.getType(), parameter.getName(), parameter.getQName(), 0));
        }
        return operationParameters;
    }

    static FieldDescriptor createFieldDescriptor(String nameUpToNow, TypeEntry typeEntry, String name, QName qName, int depth) throws ClassNotFoundException {
        FieldDescriptor descriptor;
        if (typeEntry.isBaseType()) {
            descriptor = new SimpleFieldDescriptor(name, nameUpToNow + name, depth, qName, typeEntry.getQName().getLocalPart());
        } else {
            descriptor = new ComplexFieldDescriptor(name, nameUpToNow + name, depth, qName);
            addFieldDescriptors(typeEntry, (ComplexFieldDescriptor) descriptor, descriptor.getFormName() + ".");
        }
        return descriptor;
    }

    static void addFieldDescriptors(TypeEntry typeEntry, ComplexFieldDescriptor descriptor, String nameUpToNow) throws ClassNotFoundException {
        Vector containedElements = typeEntry.getContainedElements();
        for (Object obj : containedElements) {
            ElementDecl containedElement = (ElementDecl) obj;
            QName qName = containedElement.getQName();
            String localPart = qName.getLocalPart();
            localPart = localPart.substring(localPart.lastIndexOf('>') + 1, localPart.length());
            FieldDescriptor innerDescriptor;
            TypeEntry containedTypeEntry = containedElement.getType();
            if (containedTypeEntry instanceof CollectionType) {
                innerDescriptor = createCollection(containedTypeEntry);
            } else {
                innerDescriptor = createFieldDescriptor(
                        nameUpToNow,
                        containedTypeEntry,
                        localPart,
                        qName,
                        descriptor.getDepth() + 1);
            }
            descriptor.add(innerDescriptor);
        }
    }

    private static FieldDescriptor createCollection(TypeEntry typeEntry) {
        return null;
    }

}
