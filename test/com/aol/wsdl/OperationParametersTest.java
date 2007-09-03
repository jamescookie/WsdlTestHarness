package com.aol.wsdl;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class OperationParametersTest {
    
    @Test
    public void shouldSetValuesCorrectly() throws Exception {
        String formName = "dslNumber";
        SimpleFieldDescriptor fieldDescriptor = new SimpleFieldDescriptor(null, formName, 0, null, "string");
        OperationParameters parameter = new OperationParameters();
        parameter.add(fieldDescriptor);

        HashMap<String, String[]> map = new HashMap<String, String[]>();
        String value = "something";
        map.put(formName, new String[] {value});
        parameter.setValues(map);

        Assert.assertEquals(value, parameter.get(0).getValue());    
    }

}
