package com.aol.wsdl;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionalTest {

    @Test
    public void shouldShowCorrectOperations() throws Exception {
        ServiceLocator serviceLocator = new ServiceLocator("http://samples.gotdotnet.com/quickstart/aspplus/samples/services/MathService/VB/MathService.asmx?WSDL");
        List<String> names = serviceLocator.getOperationNames();
        Assert.assertEquals(4, names.size());
        for (String name : names) {
            OperationParameters parameters = serviceLocator.createOperationParameters(name);
            Map<String,String[]> map = new HashMap<String, String[]>();
            map.put("A", new String[] {"6"});
            map.put("B", new String[] {"3"});
            parameters.setValues(map);
            String s = serviceLocator.invoke(name, parameters);
            if (name.equalsIgnoreCase("add")) {
                Assert.assertTrue(s.contains("9"));
            } else if (name.equalsIgnoreCase("subtract")) {
                Assert.assertTrue(s.contains("3"));
            } else if (name.equalsIgnoreCase("divide")) {
                Assert.assertTrue(s.contains("2"));
            } else if (name.equalsIgnoreCase("multiply")) {
                Assert.assertTrue(s.contains("18"));
            }
        }
    }
    
}
