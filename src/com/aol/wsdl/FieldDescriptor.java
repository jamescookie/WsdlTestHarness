package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class FieldDescriptor extends ArrayList<FieldDescriptor> {
    private final boolean primitive;
    private final String name;
    private final int depth;
    private final String formName;
    private final QName qname;
    private Class javaType;
    private String value;
    private String validationMessage = "";

    public FieldDescriptor(boolean primitive, QName qname, String name, String formName, int depth, String javaType) throws ClassNotFoundException {
        this.primitive = primitive;
        this.qname = qname;
        this.name = name;
        this.formName = formName;
        this.depth = depth;
        if (primitive) {
            setJavaType(javaType);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public int getDepth() {
        return depth;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormName() {
        return formName;
    }

    public QName getQname() {
        return qname;
    }

    public boolean getValidationMessageRequired() {
        return validationMessage != null;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaTypeName) throws ClassNotFoundException {
        if (isPrimitive()) {
            javaTypeName = new String(new char[]{javaTypeName.charAt(0)}).toUpperCase() + javaTypeName.substring(1);
            if ("Int".equals(javaTypeName)) {
                javaTypeName = "Integer";
            } else if ("Char".equals(javaTypeName)) {
                javaTypeName = "Character";
            } else if ("DateTime".equals(javaTypeName)) {
                this.javaType = Class.forName("java.util.Date");
                return;
            }
            this.javaType = Class.forName("java.lang." + javaTypeName);
        }
    }

    public void serialize(SerializationContext context) throws IOException {
        if (isPrimitive()) {
            context.serialize(getQname(), null, "".equals(getValue()) ? null : getValue(), context.getQNameForClass(javaType), javaType);
        } else {
            context.startElement(getQname(), null);

            for (FieldDescriptor child : this) {
                context.setItemQName(null);
                child.serialize(context);
            }

            context.endElement();
        }
    }

    public boolean isValid() {
        boolean retValue = true;
        if (isPrimitive()) {
            Class javaType = getJavaType();
            String value = getValue();
            if (Character.class.equals(javaType)) {
                retValue = value.length() == 1;
            } else {
                try {
                    Constructor constructor = javaType.getConstructor(String.class);
                    constructor.newInstance(value);
                } catch (Exception e) {
                    retValue = false;
                }
            }
            if (!retValue) {
                setValidationMessage("Unable to create " + javaType + " from '" + value + "'");
            }
        } else {
            for (FieldDescriptor child : this) {
                if (!child.isValid()) {
                    retValue = false;
                }
            }
        }
        return retValue;
    }

    public String toString() {
        return "FieldDescriptor: " +
                " primitive: " + primitive +
                super.toString();
    }
}
