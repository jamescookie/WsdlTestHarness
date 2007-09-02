package com.aol.wsdl;

import org.apache.axis.wsdl.symbolTable.ElementDecl;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.apache.axis.wsdl.symbolTable.TypeEntry;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.namespace.QName;
import java.util.Vector;

public class FieldDescriptorCreatorTest {

    @Test
    public void should() throws Exception {
        String[] objects = "whatever[0,100]".split("[\\[,\\]]");
        Assert.assertEquals(3, objects.length);
        Assert.assertEquals("0", objects[1]);
        Assert.assertEquals("100", objects[2]);
    }
    
    @Test
    public void shouldConstructCorrectFieldDescriptorsForParameters() throws Exception {
        Parameter parameter1 = new Parameter();
        parameter1.setName("param1");
        parameter1.setQName(new QName("param1"));
        TypeEntry typeEntry = new TypeEntry(new QName("")) {};
        Vector vector = new Vector();
        vector.add(new ElementDecl(null, new QName("something>string")));
        typeEntry.setContainedElements(vector);
        parameter1.setType(typeEntry);

    }
}
