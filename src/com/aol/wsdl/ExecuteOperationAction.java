package com.aol.wsdl;

import org.apache.struts2.interceptor.ParameterAware;

import java.util.Map;

public class ExecuteOperationAction extends CommonAction implements ParameterAware {
    private String operation;
    private Map requestParameters;
    private String result;
    private ComplexFieldDescriptor fieldDescriptor;
    private Exception exception;

    public ComplexFieldDescriptor getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(ComplexFieldDescriptor fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public String execute() throws Exception {
        ServiceLocator serviceLocator = getServiceLocator();
        fieldDescriptor = serviceLocator.createFieldDescriptor(operation);
        setValues(fieldDescriptor);
        if (fieldDescriptor.isValid()) {
            try {
                setResult(serviceLocator.invoke(operation, fieldDescriptor));
            } catch (Exception e) {
                setException(e);
            }
        }

        return SUCCESS;
    }

    void setValues(ComplexFieldDescriptor fieldDescriptor) {
        for (FieldDescriptor descriptor : fieldDescriptor) {
            if (descriptor.isPrimitive()) {
                descriptor.setValue(getRequestParameter(descriptor));
            } else {
                setValues((ComplexFieldDescriptor) descriptor);
            }
        }
    }

    private String getRequestParameter(FieldDescriptor descriptor) {
        String[] strings = ((String[]) requestParameters.get(descriptor.getFormName()));
        if (strings == null || strings.length == 0) {
            return null;
        }
        return strings[0];
    }

    public void setParameters(Map map) {
        requestParameters = map;
    }
}
