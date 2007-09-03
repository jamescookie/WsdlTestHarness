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
            TypeEntry paramType = parameter.getType();
            if (paramType.isBaseType()) {
                returnParameters.add(createSimpleType("", paramType, parameter.getName(), parameter.getQName()));
            } else {
                returnParameters.add(createComplexType("", paramType, parameter.getName(), paramType.getQName()));
            }
        }
        return returnParameters;
    }

    static FieldDescriptor createComplexType(String nameUpToNow, TypeEntry typeEntry, String name, QName qName) throws ClassNotFoundException {
        FieldDescriptor descriptor = new FieldDescriptor(false, qName, name, nameUpToNow + name, 0, null);
        addFieldDescriptors(typeEntry, descriptor, descriptor.getFormName() + ".");
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
                        localPart,
                        qName,
                        nameUpToNow + localPart,
                        true,
                        descriptor.getDepth() + 1,
                        containedElement.getType().getQName().getLocalPart()
                );
            } else {
                innerDescriptor = createComplexType(
                        nameUpToNow + localPart + ".",
                        containedElement.getType(),
                        localPart,
                        qName
                );
            }
            descriptor.add(innerDescriptor);
        }
    }

    static FieldDescriptor createFieldDescriptor(String name, QName qname, String formName, boolean primitive, int depth, String javaType) throws ClassNotFoundException {
        return new FieldDescriptor(primitive, qname, name, formName, depth, javaType);
    }

    static FieldDescriptor createSimpleType(String nameUpToNow, TypeEntry typeEntry, String name, QName qName) throws ClassNotFoundException {
        return createFieldDescriptor(
                name,
                qName,
                nameUpToNow + name,
                true,
                0,
                typeEntry.getQName().getLocalPart()
        );
    }


}
