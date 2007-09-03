package com.aol.wsdl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ServiceLocatorTest {
    private static ServiceLocator serviceLocator;

    @BeforeClass
    public static void beforeClass() {
        serviceLocator = new ServiceLocator("http://s3.amazonaws.com/doc/2006-03-01/AmazonS3.wsdl");
    }

    @Test
    public void shouldShowCorrectOperations() throws Exception {
        List<String> names = serviceLocator.getOperationNames();
        Assert.assertEquals(15, names.size());
        for (String name : names) {
            System.out.println("name = " + name);
            serviceLocator.createOperationParameters(name);
        }
    }

    @Test
    public void shouldBeAbleToHandleCollections() throws Exception {
        serviceLocator.createOperationParameters("CreateBucket");
    }
}
