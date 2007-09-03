package com.aol.wsdl.actions;

import com.aol.wsdl.OperationParameters;
import com.aol.wsdl.ServiceLocator;

import java.util.List;

public class SetupOperationAction extends CommonAction {
    private String result = null;
    private OperationParameters operationParameters;
    private Exception exception;

    public OperationParameters getOperationParameters() {
        return operationParameters;
    }

    public void setOperationParameters(OperationParameters operationParameters) {
        this.operationParameters = operationParameters;
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
        if (operationParameters == null) {
            ServiceLocator serviceLocator = (ServiceLocator) map.get(SERVICE_LOCATOR);
            operationParameters = serviceLocator.createOperationParameters((String) map.get(OPERATION));
        }

        return operationParameters;
    }

}
