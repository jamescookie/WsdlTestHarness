package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import java.io.IOException;
import java.util.ArrayList;

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

}
