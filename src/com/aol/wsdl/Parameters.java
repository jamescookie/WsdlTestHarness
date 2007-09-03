package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;

import java.io.IOException;

public interface Parameters {
    void serialize(SerializationContext context) throws IOException;

    boolean isValid();
}
