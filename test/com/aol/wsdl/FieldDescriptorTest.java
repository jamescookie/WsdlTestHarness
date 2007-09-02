package com.aol.wsdl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldDescriptorTest {
    private FieldDescriptor descriptor;

    @Before
    public void setup() throws Exception {
        descriptor = new FieldDescriptor(true, null, null, null, 0, "string");
    }

    @Test
    public void shouldBeValid() throws Exception {
        checkValidType(descriptor, "string", "hello");
        checkValidType(descriptor, "char", "a");
        checkValidType(descriptor, "int", String.valueOf(Integer.MIN_VALUE));
        checkValidType(descriptor, "int", String.valueOf(Integer.MAX_VALUE));
        checkValidType(descriptor, "short", String.valueOf(Short.MIN_VALUE));
        checkValidType(descriptor, "short", String.valueOf(Short.MAX_VALUE));
        checkValidType(descriptor, "long", String.valueOf(Long.MIN_VALUE));
        checkValidType(descriptor, "long", String.valueOf(Long.MAX_VALUE));
        checkValidType(descriptor, "double", String.valueOf(Double.MIN_VALUE));
        checkValidType(descriptor, "double", String.valueOf(Double.MAX_VALUE));
        checkValidType(descriptor, "float", String.valueOf(Float.MIN_VALUE));
        checkValidType(descriptor, "float", String.valueOf(Float.MAX_VALUE));
        checkValidType(descriptor, "boolean", String.valueOf(Boolean.TRUE));
        checkValidType(descriptor, "boolean", String.valueOf(Boolean.FALSE));
    }

    @Test
    public void shouldBeInvalid() throws Exception {
        checkInvalidType(descriptor, "char", "hello");
        checkInvalidType(descriptor, "int", "hello");
        checkInvalidType(descriptor, "int", String.valueOf(Long.MAX_VALUE));
        checkInvalidType(descriptor, "short", "hello");
        checkInvalidType(descriptor, "short", String.valueOf(Integer.MAX_VALUE));
        checkInvalidType(descriptor, "long", "hello");
        checkInvalidType(descriptor, "long", String.valueOf(Double.MAX_VALUE));
        checkInvalidType(descriptor, "double", "hello");
        checkInvalidType(descriptor, "float", "hello");
    }

    @Test
    public void shouldCheckForComplexTypes() throws Exception {
        descriptor = new FieldDescriptor(false, null, null, null, 0, null);
        FieldDescriptor fd1 = new FieldDescriptor(true, null, null, null, 0, "string");
        checkValidType(fd1, "string", "hello");
        descriptor.add(fd1);
        Assert.assertTrue(descriptor.isValid());
    }

    private void checkValidType(FieldDescriptor descriptor, String javaTypeName, String value) throws ClassNotFoundException {
        descriptor.setJavaType(javaTypeName);
        descriptor.setValue(value);
        Assert.assertTrue(descriptor.isValid());
    }

    private void checkInvalidType(FieldDescriptor descriptor, String javaTypeName, String value) throws ClassNotFoundException {
        descriptor.setJavaType(javaTypeName);
        descriptor.setValue(value);
        Assert.assertFalse(descriptor.isValid());
    }


}
