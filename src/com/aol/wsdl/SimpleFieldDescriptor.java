package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;

class SimpleFieldDescriptor implements FieldDescriptor {
    private Class javaType;
    private final String name;
    private final int depth;
    private final String formName;
    private final QName qname;
    private String value;
    private String validationMessage = "";

    public SimpleFieldDescriptor(String name, String formName, int depth, QName qname, String javaType) throws ClassNotFoundException {
        this.name = name;
        this.formName = formName;
        this.depth = depth;
        this.qname = qname;
        setJavaType(javaType);
    }

    public String getName() {
        return name;
    }

    public boolean isPrimitive() {
        return true;
    }

    public int getDepth() {
        return depth;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    void setValue(String value) {
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

    private void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    private Class getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaTypeName) throws ClassNotFoundException {
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

    public void setValues(Map<String, String[]> map) {
        setValue(getRequestParameter(map, getFormName()));
    }

    private String getRequestParameter(Map<String, String[]> map, String formName) {
        String[] strings = map.get(formName);
        if (strings == null || strings.length == 0) {
            return null;
        }
        return strings[0];
    }

    public void serialize(SerializationContext context) throws IOException {
        context.serialize(getQname(), null, "".equals(getValue()) ? null : getValue(), context.getQNameForClass(javaType), javaType);
    }

    public boolean isValid() {
        boolean retValue = true;
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
        return retValue;
    }

}
