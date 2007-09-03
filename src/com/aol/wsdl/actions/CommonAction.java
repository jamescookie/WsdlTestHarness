package com.aol.wsdl.actions;

import com.aol.wsdl.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class CommonAction extends ActionSupport implements SessionAware {
    protected Map map;
    protected static final String WSDL = "WSDL";
    protected static final String OPERATION = "OPERATION";
    protected static final String SERVICE_LOCATOR = "serviceLocator";

    public String getUrl() {
        String url = (String) map.get(WSDL);
        if (url == null) {
            url = "Undefined";
        }
        return url;
    }

    public void setSession(Map map) {
        this.map = map;
    }

    protected void clearWsdl() {
        map.remove(WSDL);    
    }

    protected ServiceLocator getServiceLocator() {
        return (ServiceLocator) map.get(SERVICE_LOCATOR);
    }

    public boolean isWsdlDefined() {
        return map.get(WSDL) != null;
    }
}
