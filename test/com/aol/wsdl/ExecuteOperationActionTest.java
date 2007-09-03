package com.aol.wsdl;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class ExecuteOperationActionTest {

    @Test
    public void shouldSetValuesCorrectly() throws Exception {
        String formName = "dslNumber";
        SimpleFieldDescriptor fieldDescriptor = new SimpleFieldDescriptor(null, formName, 0, null, "string");
        ComplexFieldDescriptor parameter = new ComplexFieldDescriptor(null, null, 0, null);
        parameter.add(fieldDescriptor);

        ExecuteOperationAction action = new ExecuteOperationAction();
        HashMap map = new HashMap();
        String value = "something";
        map.put(formName, new String[] {value});
        action.setParameters(map);

        action.setValues(parameter);

        Assert.assertEquals(value, parameter.get(0).getValue());
    }
}
