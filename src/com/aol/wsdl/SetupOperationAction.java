package com.aol.wsdl;

import java.util.List;

public class SetupOperationAction extends CommonAction {
    private String result = null;
    private FieldDescriptor fieldDescriptor;


    public FieldDescriptor getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(FieldDescriptor fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
