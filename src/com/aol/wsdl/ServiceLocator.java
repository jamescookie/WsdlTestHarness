package com.aol.wsdl;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.ElementDeserializer;
import org.apache.axis.message.RPCElement;
import org.apache.axis.message.RPCParam;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.wsdl.gen.Parser;
import org.apache.axis.wsdl.symbolTable.BindingEntry;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.apache.axis.wsdl.symbolTable.Parameters;
import org.apache.axis.wsdl.symbolTable.ServiceEntry;
import org.apache.axis.wsdl.symbolTable.SymTabEntry;
import org.apache.axis.wsdl.symbolTable.SymbolTable;
import org.apache.axis.wsdl.symbolTable.TypeEntry;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.w3c.dom.Element;

import javax.wsdl.Binding;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ServiceLocator {
    private org.apache.axis.client.Service service;
    private Port port;
    private BindingEntry bindingEntry;
    private Parser wsdlParser = null;

    public ServiceLocator(String url) {
        init(url);
    }

    private void init(String url) {
        try {
            wsdlParser = new Parser();
            wsdlParser.run(url);
            SymbolTable symbolTable = wsdlParser.getSymbolTable();
            Service service = selectService(null, null);

            port = selectPort(service.getPorts(), null);
            Binding binding = port.getBinding();

            bindingEntry = symbolTable.getBindingEntry(binding.getQName());
            this.service = new org.apache.axis.client.Service(wsdlParser, service.getQName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Iterator getBindingEntryIterator() {
        return bindingEntry.getParameters().keySet().iterator();
    }

    public List<String> getOperationNames() {
        List<String> names = new ArrayList<String>();
        try {
            Operation operation;
            Iterator bindingEntryIterator = getBindingEntryIterator();
            while (bindingEntryIterator.hasNext()) {
                operation = (Operation) bindingEntryIterator.next();
                names.add(operation.getName());
            }
            return names;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getURL() {
        try {
            Iterator bindingEntryIterator = getBindingEntryIterator();
            if (bindingEntryIterator.hasNext()) {
                Operation operation = (Operation) bindingEntryIterator.next();
                Call call = (Call) service.createCall(
                        QName.valueOf(port.getName()),
                        QName.valueOf(operation.getName()));
                return call.getTargetEndpointAddress();
            }
            return "Something with no operations!";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String invoke(String operationName, FieldDescriptor fieldDescriptors) throws Exception {
        Operation operation = findOperation(operationName);
        String portName = port.getName();
        Call call = (Call) service.createCall(QName.valueOf(portName), QName.valueOf(operation.getName()));
        call.setTimeout(15 * 1000);
        call.setProperty(ElementDeserializer.DESERIALIZE_CURRENT_ELEMENT, Boolean.TRUE);

        StringWriter stringWriter = new StringWriter();
        SerializationContext context = new SerializationContext(stringWriter, null);
        context.setSendDecl(false);

        for (FieldDescriptor fieldDescriptor : fieldDescriptors) {
            fieldDescriptor.serialize(context);
        }

        RPCParam rpcParam = new MyRPCParam(stringWriter.toString());
        QName operationQName = call.getOperationName();
        RPCElement body = new RPCElement(operationQName.getNamespaceURI(), operationQName.getLocalPart(), new Object[]{rpcParam});

        return invoke(body, call);
    }

    private String invoke(RPCElement body, Call call) throws AxisFault, JDOMException {
        MessageContext msgContext = call.getMessageContext();
        SOAPEnvelope reqEnv =
                new SOAPEnvelope(msgContext.getSOAPConstants(),
                        msgContext.getSchemaVersion());
        Message reqMsg = new Message(reqEnv);

        try {
            body.setEncodingStyle(call.getEncodingStyle());

            call.setRequestMessage(reqMsg);

            reqEnv.addBodyElement(body);
            reqEnv.setMessageType(Message.REQUEST);

            call.invoke();
        } catch (Exception e) {
            throw AxisFault.makeFault(e);
        }


        Message responseMessage = msgContext.getResponseMessage();
        Element documentElement = responseMessage.getSOAPPart().getDocumentElement();
        org.jdom.Element jelement = new DOMBuilder().build(documentElement);

        return XMLPrettyPrinter.prettyPrint(jelement, 0);
    }

    private Operation findOperation(String operationName) {
        Operation operation = null;
        Iterator bindingEntryIterator = getBindingEntryIterator();
        while (bindingEntryIterator.hasNext()) {
            operation = (Operation) bindingEntryIterator.next();
            if (operation.getName().equals(operationName)) {
                break;
            } else {
                operation = null;
            }
        }
        return operation;
    }

    public FieldDescriptor createFieldDescriptor(String operationName) throws ClassNotFoundException {
        FieldDescriptor returnParameters = new FieldDescriptor();
        Operation operation = findOperation(operationName);
        Parameters parameters = (Parameters) bindingEntry.getParameters().get(operation);
        Vector list = parameters.list;
        for (Object param : list) {
            Parameter parameter = (Parameter) param;
            TypeEntry paramType = parameter.getType();
            if (paramType.isBaseType()) {
                returnParameters.add(FieldDescriptorCreator.createSimpleType(parameter, ""));
            } else {
                returnParameters.add(FieldDescriptorCreator.createComplexType(parameter, "", paramType, parameter.getName(), paramType.getQName()));
            }
        }
        return returnParameters;
    }

    private Port selectPort(Map ports, String portName) throws Exception {
        for (Object o : ports.keySet()) {
            String name = (String) o;

            if ((portName == null) || (portName.length() == 0)) {
                Port port = (Port) ports.get(name);
                List list = port.getExtensibilityElements();

                for (int i = 0; (list != null) && (i < list.size()); i++) {
                    Object obj = list.get(i);
                    if (obj instanceof SOAPAddress) {
                        return port;
                    }
                }
            } else if ((name != null) && name.equals(portName)) {
                return (Port) ports.get(name);
            }
        }
        return null;
    }

    private Service selectService(String serviceNS, String serviceName)
            throws Exception {
        QName serviceQName = (((serviceNS != null)
                && (serviceName != null))
                ? new QName(serviceNS, serviceName)
                : null);
        ServiceEntry serviceEntry = (ServiceEntry) getSymTabEntry(serviceQName,
                ServiceEntry.class);
        return serviceEntry.getService();
    }

    private SymTabEntry getSymTabEntry(QName qname, Class cls) {
        HashMap map = wsdlParser.getSymbolTable().getHashMap();

        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Vector v = (Vector) entry.getValue();

            if ((qname == null) || qname.equals(qname)) {
                for (int i = 0; i < v.size(); ++i) {
                    SymTabEntry symTabEntry = (SymTabEntry) v.elementAt(i);

                    if (cls.isInstance(symTabEntry)) {
                        return symTabEntry;
                    }
                }
            }
        }
        return null;
    }

}
