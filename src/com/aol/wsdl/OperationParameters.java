package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class OperationParameters extends ArrayList<FieldDescriptor> implements Parameters {

    public void serialize(SerializationContext context) throws IOException {
        for (FieldDescriptor descriptor : this) {
            descriptor.serialize(context);
        }
    }

    public boolean isValid() {
        boolean retValue = true;
        for (FieldDescriptor descriptor : this) {
            if (!descriptor.isValid()) {
                retValue = false;
            }
        }
        return retValue;
    }

    public void setValues(Map<String, String[]> requestParameters) {
        for (FieldDescriptor descriptor : this) {
            descriptor.setValues(requestParameters);
        }
    }

}
