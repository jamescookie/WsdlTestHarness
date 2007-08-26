package com.aol.wsdl;

import java.net.URL;

public class HomeAction extends CommonAction {
    private String error = "";
    private String wsdlUrl;

    public String getWsdlUrl() {
        return wsdlUrl;
    }

    public void setWsdlUrl(String url) {
        wsdlUrl = url;        
    }

    public String execute() throws Exception {
        clearWsdl();
        return INPUT;
    }

    

    public String doWsdl() {
        error = "";
        ServiceLocator serviceLocator = null;

        if (wsdlUrl.equals("")) {
            error = "You must enter a WSDL URL";
        } else {
            try {
                new URL(wsdlUrl);
                serviceLocator = new ServiceLocator(wsdlUrl);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }

        if (!"".equals(error)) {
            return INPUT;
        } else {
            map.put(WSDL, wsdlUrl);
            map.put(SERVICE_LOCATOR, serviceLocator);
            return SUCCESS;
        }

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean getHasErrors() {
        return !getError().equals("");
    }
}
