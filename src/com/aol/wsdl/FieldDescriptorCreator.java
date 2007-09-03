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
                returnParameters.add(createSimpleType(parameter, ""));
            } else {
                returnParameters.add(createComplexType(null, "", paramType, parameter.getName(), paramType.getQName()));
            }
        }
        return returnParameters;
    }

    static FieldDescriptor createComplexType(Parameter parameter, String nameUpToNow, TypeEntry typeEntry, String name, QName qName) throws ClassNotFoundException {
        FieldDescriptor descriptor = new FieldDescriptor();
        descriptor.setName(name);
        descriptor.setQName(qName);
        descriptor.setFormName(nameUpToNow + name);
        descriptor.setParameter(parameter);
        descriptor.setPrimitive(false);
        nameUpToNow = descriptor.getFormName() + ".";
        addFieldDescriptors(typeEntry, descriptor, nameUpToNow);
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
                        null,
                        true,
                        descriptor.getDepth() + 1,
                        containedElement.getType().getQName().getLocalPart()
                );
            } else {
                innerDescriptor = createComplexType(
                        null,
                        nameUpToNow + localPart + ".",
                        containedElement.getType(),
                        localPart,
                        qName
                );
            }
            descriptor.add(innerDescriptor);
        }
    }

    static FieldDescriptor createFieldDescriptor(String name, QName qname, String formName, Parameter parameter, boolean primitive, int depth, String javaType) throws ClassNotFoundException {
        FieldDescriptor descriptor = new FieldDescriptor();
        descriptor.setQName(qname);
        descriptor.setName(name);
        descriptor.setFormName(formName);
        descriptor.setParameter(parameter);
        descriptor.setPrimitive(primitive);
        descriptor.setDepth(depth);
        descriptor.setJavaType(javaType);
        return descriptor;
    }

    static FieldDescriptor createSimpleType(Parameter parameter, String nameUpToNow) throws ClassNotFoundException {
        return createFieldDescriptor(
                parameter.getName(),
                parameter.getQName(),
                nameUpToNow + parameter.getName(),
                parameter,
                true,
                0,
                parameter.getType().getQName().getLocalPart()
        );
    }


}
