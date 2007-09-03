package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import java.io.IOException;
import java.util.Map;

public interface Parameters {
    void serialize(SerializationContext context) throws IOException;

    boolean isValid();

    void setValues(Map<String, String[]> map);
}
