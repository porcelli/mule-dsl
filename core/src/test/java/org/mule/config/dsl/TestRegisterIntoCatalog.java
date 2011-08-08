/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.junit.Test;
import org.mule.api.MuleContext;
import org.mule.api.NamedObject;
import org.mule.api.context.MuleContextAware;
import org.mule.transport.file.FileConnector;

import static org.fest.assertions.Assertions.assertThat;

public class TestRegisterIntoCatalog {

    @Test
    public void simpleRegisterObject() throws Exception {
        final Object x = new Object();
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                register("sometehing", x);
            }
        }).advanced().muleContext();

        Object wrongLookupX = muleContext.getRegistry().lookupObject("Sometehing");
        assertThat(wrongLookupX).isNull();

        Object lookupX = muleContext.getRegistry().lookupObject("sometehing");
        assertThat(lookupX).isNotNull().isEqualTo(x);
    }

    @Test
    public void simpleRegisterConnector() throws Exception {
        final FileConnector connector = new FileConnector(null);
        connector.setRecursive(false);
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                register("connector", connector);
            }
        }).advanced().muleContext();
        Object wrongLookup = muleContext.getRegistry().lookupObject("Connector");
        assertThat(wrongLookup).isNull();

        Object lookupConnector = muleContext.getRegistry().lookupObject("connector");
        assertThat(lookupConnector).isNotNull().isInstanceOf(FileConnector.class).isEqualTo(connector);
        FileConnector fileConnector = (FileConnector) lookupConnector;

        assertThat(fileConnector.getName()).isEqualTo("connector");
    }

    @Test
    public void simpleRegisterNamedConnector() throws Exception {
        final FileConnector connector = new FileConnector(null);
        connector.setName("MYNAMEDCONNECTOR");
        connector.setRecursive(false);
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                register("connector", connector);
            }
        }).advanced().muleContext();
        Object wrongLookup = muleContext.getRegistry().lookupObject("connector");
        assertThat(wrongLookup).isNull();

        Object lookupConnector = muleContext.getRegistry().lookupObject("MYNAMEDCONNECTOR");
        assertThat(lookupConnector).isNotNull().isInstanceOf(FileConnector.class).isEqualTo(connector);
        FileConnector fileConnector = (FileConnector) lookupConnector;

        assertThat(fileConnector.getName()).isEqualTo("MYNAMEDCONNECTOR");
    }

    @Test
    public void simpleRegisterNamedObject() throws Exception {
        final NamedObject namedObject = new NamedObject() {
            private String name = null;

            @Override
            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String getName() {
                return name;
            }
        };

        namedObject.setName("MYNAMEDOBJ");
        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                register("namedObj", namedObject);
            }
        }).advanced().muleContext();
        Object wrongLookup = muleContext.getRegistry().lookupObject("NamedObj");
        assertThat(wrongLookup).isNull();

        Object lookupConnector = muleContext.getRegistry().lookupObject("namedObj");
        assertThat(lookupConnector).isNotNull().isInstanceOf(NamedObject.class).isEqualTo(namedObject);

        assertThat(((NamedObject) lookupConnector).getName()).isEqualTo("MYNAMEDOBJ");
    }

    @Test
    public void simpleRegisterMuleContextAwareObject() throws Exception {
        final MyContextAwareType myAwareObj = new MyContextAwareType();

        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                register("namedXXX", myAwareObj);
            }
        }).advanced().muleContext();
        Object wrongLookup = muleContext.getRegistry().lookupObject("NamedXXX");
        assertThat(wrongLookup).isNull();

        Object lookupConnector = muleContext.getRegistry().lookupObject("namedXXX");
        assertThat(lookupConnector).isNotNull().isInstanceOf(MyContextAwareType.class).isEqualTo(myAwareObj);

        assertThat(((MyContextAwareType) lookupConnector).getContext()).isNotNull().isInstanceOf(MuleContext.class).isEqualTo(muleContext);
    }

    @Test
    public void simpleRegisterMuleContextAwareAndNamedObject() throws Exception {
        final MyCompleteType myCompleteObj = new MyCompleteType();

        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                register("completeXXX", myCompleteObj);
            }
        }).advanced().muleContext();
        Object wrongLookup = muleContext.getRegistry().lookupObject("CompleteXXX");
        assertThat(wrongLookup).isNull();

        Object lookupConnector = muleContext.getRegistry().lookupObject("completeXXX");
        assertThat(lookupConnector).isNotNull().isInstanceOf(MyCompleteType.class).isEqualTo(myCompleteObj);

        assertThat(((MyCompleteType) lookupConnector).getContext()).isNotNull().isInstanceOf(MuleContext.class).isEqualTo(muleContext);
        assertThat(((MyCompleteType) lookupConnector).getName()).isNotNull().isEqualTo("completeXXX");
    }

    @Test
    public void simpleRegisterMuleContextAwareAndNamedObjectWithName() throws Exception {
        final MyCompleteType myCompleteObj = new MyCompleteType();
        myCompleteObj.setName("MY_NAME");

        final MuleContext muleContext = new Mule(new AbstractModule() {
            @Override
            public void configure() {
                register("completeXXX", myCompleteObj);
            }
        }).advanced().muleContext();

        Object wrongLookup = muleContext.getRegistry().lookupObject("CompleteXXX");
        assertThat(wrongLookup).isNull();

        Object lookupConnector = muleContext.getRegistry().lookupObject("completeXXX");
        assertThat(lookupConnector).isNotNull().isInstanceOf(MyCompleteType.class).isEqualTo(myCompleteObj);

        assertThat(((MyCompleteType) lookupConnector).getContext()).isNotNull().isInstanceOf(MuleContext.class).isEqualTo(muleContext);
        assertThat(((MyCompleteType) lookupConnector).getName()).isNotNull().isEqualTo("MY_NAME");
    }

    @Test(expected = RuntimeException.class)
    public void testDuplicateRegister() throws Exception {
        new Mule(new AbstractModule() {
            @Override
            public void configure() {
                register("sometehing", new Object());
                register("sometehing", new Object());
            }
        });
    }


    private class MyContextAwareType implements MuleContextAware {

        private MuleContext context;

        public MuleContext getContext() {
            return context;
        }

        @Override
        public void setMuleContext(MuleContext context) {
            this.context = context;
        }
    }

    private class MyCompleteType implements MuleContextAware, NamedObject {

        private MuleContext context;
        private String name = null;

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        public MuleContext getContext() {
            return context;
        }

        @Override
        public void setMuleContext(MuleContext context) {
            this.context = context;
        }
    }

}