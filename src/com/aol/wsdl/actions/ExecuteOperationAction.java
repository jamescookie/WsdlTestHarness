package com.aol.wsdl.actions;

import com.aol.wsdl.ComplexFieldDescriptor;
import com.aol.wsdl.FieldDescriptor;
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
        setValues(operationParameters);
        if (operationParameters.isValid()) {
            try {
                setResult(serviceLocator.invoke(operation, operationParameters));
            } catch (Exception e) {
                setException(e);
            }
        }

        return SUCCESS;
    }

    void setValues(OperationParameters operationParameters) {
        for (FieldDescriptor descriptor : operationParameters) {
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
