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
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractConnector;
import org.mule.transport.file.ExpressionFilenameParser;
import org.mule.transport.file.FileConnector;

import static org.fest.assertions.Assertions.assertThat;

public class TestGlobalConnector {

    @Test
    public void registerGlobalConnector() throws Exception {
        final ExpressionFilenameParser parserExpr = new ExpressionFilenameParser();

        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                final FileConnector fileConn = connector("connector").with(FileConnector.class);
                fileConn.setRecursive(true);
                fileConn.setFilenameParser(parserExpr);
                fileConn.setPollingFrequency(10L);
            }
        }).advanced().muleContext();

        Object wrongLookup = muleContext.getRegistry().lookupObject("Connector");
        assertThat(wrongLookup).isNull();

        Object lookupConnector = muleContext.getRegistry().lookupObject("connector");
        assertThat(lookupConnector).isNotNull().isInstanceOf(FileConnector.class);
        FileConnector fileConnector = (FileConnector) lookupConnector;

        assertThat(fileConnector.getName()).isEqualTo("connector");
        assertThat(fileConnector.getPollingFrequency()).isEqualTo(10L);
        assertThat(fileConnector.isRecursive()).isEqualTo(true);
        assertThat(fileConnector.getFilenameParser()).isEqualTo(parserExpr);
    }

    @Test(expected = RuntimeException.class)
    public void failRegisterAbstractClass() throws Exception {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector("connector").with(AbstractConnector.class);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void failRegisterInterfaceClass() throws Exception {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector("connector").with(Connector.class);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void failRegisterNull() throws Exception {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector("connector").with(null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void failRegisterEmptyName() throws Exception {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector("").with(FileConnector.class);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void failRegisterNullName() throws Exception {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                connector(null).with(FileConnector.class);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void failSetNameOnGlobalConnector() throws Exception {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                final FileConnector fileConn = connector("connector").with(FileConnector.class);
                fileConn.setName("NewName");
            }
        });
    }
}