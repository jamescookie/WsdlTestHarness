package com.aol.wsdl;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FunctionalTest {

    @Test
    public void shouldShowCorrectOperations() throws Exception {
        ServiceLocator serviceLocator = new ServiceLocator("http://samples.gotdotnet.com/quickstart/aspplus/samples/services/MathService/VB/MathService.asmx?WSDL");
        List<String> names = serviceLocator.getOperationNames();
        Assert.assertEquals(4, names.size());
        for (String name : names) {
            ComplexFieldDescriptor descriptor = serviceLocator.createFieldDescriptor(name);
            descriptor.get(0).setValue("6");
            descriptor.get(1).setValue("3");
            String s = serviceLocator.invoke(name, descriptor);
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
