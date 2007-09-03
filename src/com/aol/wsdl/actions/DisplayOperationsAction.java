package com.aol.wsdl.actions;

import java.util.List;

public class DisplayOperationsAction extends CommonAction {

    public String execute() {
        return INPUT;
    }

    public List<String> getOperations() {
        return getServiceLocator().getOperationNames();
    }

    public String doChooseOperation() {
        return SUCCESS;
    }

    public void setOperation(String operation) {
        map.put(OPERATION, operation);
    }
}
