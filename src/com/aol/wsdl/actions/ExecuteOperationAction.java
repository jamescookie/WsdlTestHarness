package com.aol.wsdl.actions;

import com.aol.wsdl.OperationParameters;
import com.aol.wsdl.ServiceLocator;
import org.apache.struts2.interceptor.ParameterAware;

import java.util.Map;

public class ExecuteOperationAction extends CommonAction implements ParameterAware {
    private String operation;
    private Map requestParameters;
    private String result;
    private OperationParameters operationParameters;
    private Exception exception;

    public OperationParameters getOperationParameters() {
        return operationParameters;
    }

    public void setOperationParameters(OperationParameters operationParameters) {
        this.operationParameters = operationParameters;
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
        operationParameters = serviceLocator.createOperationParameters(operation);
        //noinspection unchecked
        operationParameters.setValues(requestParameters);
        if (operationParameters.isValid()) {
            try {
                setResult(serviceLocator.invoke(operation, operationParameters));
            } catch (Exception e) {
                setException(e);
            }
        }

        return SUCCESS;
    }

    public void setParameters(Map map) {
        requestParameters = map;
    }
}
