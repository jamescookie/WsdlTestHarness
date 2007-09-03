package com.aol.wsdl.actions;

import com.aol.wsdl.ComplexFieldDescriptor;
import com.aol.wsdl.ServiceLocator;

import java.util.List;

public class SetupOperationAction extends CommonAction {
    private String result = null;
    private ComplexFieldDescriptor fieldDescriptor;
    private Exception exception;

    public ComplexFieldDescriptor getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(ComplexFieldDescriptor fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean getAnyError() {
        return exception != null;
    }

    public boolean getResultGenerated() {
        return result != null;
    }

    public String getOperation() {
        return (String) map.get(OPERATION);
    }

    public String execute() {
        return INPUT;
    }

    public String doPerformOperation() {
        return SUCCESS;
    }

    public List getParameters() throws ClassNotFoundException {
        if (fieldDescriptor == null) {
            ServiceLocator serviceLocator = (ServiceLocator) map.get(SERVICE_LOCATOR);
            fieldDescriptor = serviceLocator.createFieldDescriptor((String) map.get(OPERATION));
        }

        return fieldDescriptor;
    }

}