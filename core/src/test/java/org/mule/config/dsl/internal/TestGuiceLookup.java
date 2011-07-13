/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

public class TestGuiceLookup {

//    @Test
//    public void testLookup() throws Exception {
//        Injector injector = Guice.createInjector(new MySimpleModule1());
//
//        MuleContextLookup mylookup = new MuleContextLookup(injector, Simple2.class);
//
//        Object returnValue = mylookup.getInstance(null);
//
//        assertThat(returnValue).isNotNull().isInstanceOf(Simple2.class).isInstanceOf(Simple.class);
//
//        assertThat(mylookup.isSingleton()).isEqualTo(false);
//    }
//
//
//    @Test
//    public void testLookupInterface() throws Exception {
//        Injector injector = Guice.createInjector(new MySimpleModule1());
//
//        MuleContextLookup mylookup = new MuleContextLookup(injector, Simple.class);
//
//        Object returnValue = mylookup.getInstance(null);
//
//        assertThat(returnValue).isNotNull().isInstanceOf(Simple2.class).isInstanceOf(Simple.class);
//
//        assertThat(mylookup.isSingleton()).isEqualTo(false);
//    }
//
//    @Test
//    public void testLookup2() throws Exception {
//        Injector injector = Guice.createInjector(new MySimpleModule2());
//
//        MuleContextLookup mylookup = new MuleContextLookup(injector, Simple2.class);
//
//        Object returnValue = mylookup.getInstance(null);
//
//        assertThat(returnValue).isNotNull().isInstanceOf(Simple2.class).isInstanceOf(Simple.class);
//
//        assertThat(mylookup.isSingleton()).isEqualTo(false);
//    }
//
//    @Test(expected = ConfigurationException.class)
//    public void testLookup2Interface() throws Exception {
//        Injector injector = Guice.createInjector(new MySimpleModule2());
//
//        MuleContextLookup mylookup = new MuleContextLookup(injector, Simple.class);
//
//        mylookup.getInstance(null);
//    }
//
//    @Test(expected = ConfigurationException.class)
//    public void testLookup3() throws Exception {
//        Injector injector = Guice.createInjector(new MySimpleModule1());
//
//        MuleContextLookup mylookup = new MuleContextLookup(injector, Complex2.class);
//
//        mylookup.getInstance(null);
//    }
//
//    @Test(expected = ConfigurationException.class)
//    public void testLookup4() throws Exception {
//        Injector injector = Guice.createInjector(new MySimpleModule2());
//
//        MuleContextLookup mylookup = new MuleContextLookup(injector, Complex2.class);
//
//        mylookup.getInstance(null);
//    }
//
//    private static class MySimpleModule1 extends AbstractModule {
//
//        @Override
//        protected void configure() {
//            bind(Simple.class).to(Simple2.class);
//        }
//    }
//
//    private static class MySimpleModule2 extends AbstractModule {
//
//        @Override
//        protected void configure() {
//        }
//    }
//
//
//    public static interface Simple {
//        void execute(String string);
//    }
//
//    public static class Simple2 implements Simple {
//        public void execute(String string) {
//            System.out.println("SIMPLE 2! : " + string);
//        }
//    }
//
//    public static class Complex2 implements Simple {
//
//        public Complex2(String value, String value2) {
//
//        }
//
//        public void execute(String string) {
//            System.out.println("SIMPLE 2! : " + string);
//        }
//    }
//
//    public static class Simple3 implements Simple {
//        public void execute(String string) {
//            System.out.println("SIMPLE 3! : " + string);
//        }
//    }
}
