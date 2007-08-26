package com.aol.wsdl;

import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.message.RPCParam;

import java.io.IOException;

class MyRPCParam extends RPCParam {
    private final String xml;

    public MyRPCParam(String xml) {
        super("", null);
        this.xml = xml;
    }

    protected void outputImpl(SerializationContext context) throws Exception {
        context.writeString(xml);
    }

    public void serialize(SerializationContext context) throws IOException {
        context.writeString(xml);
    }

    public String toString() {
        return xml;
    }
}
