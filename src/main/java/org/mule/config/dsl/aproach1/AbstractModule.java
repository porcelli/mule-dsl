/*
 * $Id: 20811 2011-03-30 15:49:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.aproach1;

import java.io.File;

public abstract class AbstractModule extends com.google.inject.AbstractModule {

    public void usePropertyPlaceholder(String fileRef) {

    }

    public void usePropertyPlaceholder(File s) {

    }

    public FlowBuilder newFlow() {
        return null;
    }

    public FlowBuilder newFlow(String name) {
        return null;
    }

    public TransformerBuilder defineTransformer() {
        return null;
    }

    public TransformerBuilder defineTransformer(String name) {
        return null;
    }

    public <T extends Object> Class<T> ref(Class<T> clazz, String ref) {
        return null;
    }

    public FlowInboundListenBuilder listen(String s) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public FlowInboundListenBuilder listen(Protocol http, String s) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public ConnectorBuilder newConnector(String name) {
        return null;
    }

    public ConnectorBuilder newConnector() {
        return null;
    }

    public interface ConnectorBuilder {
        <T extends SMTPConnector> T extend(Class<T> smtpClass);
    }

    public interface SMTPConnector extends ConnectorBuilder {
        SMTPConnector user(String s);

        SMTPConnector password(String s);

        SMTPConnector host(String s);

        SMTPConnector from(String s);

        SMTPConnector subject(String s);
    }

    public interface TransformerBuilder {
        <T extends TransformerBuilder> T extend(Class<T> clazz);
    }

    public interface MessageProcessorTransformer extends TransformerBuilder {

        MessageProcessorTransformer addProperty(String s, String s1);

        MessageProcessorTransformer removeProperty(String s);
    }

    public interface FlowBuilder {

        FlowInboundBuilder from(String stringEndpoint);

        ExtendedFlowInboundListenBuilder listen(Protocol protocol, String stringEndpoint);

        ExtendedFlowInboundListenBuilder listen(String stringEndpoint);

        ExtendedFlowInboundPollBuilder poll(Protocol protocol, String stringEndpoint);

        ExtendedFlowInboundPollBuilder poll(String stringEndpoint);

        PipelineBuilder from(FlowInboundBuilder... list);

    }

    public enum Protocol {
        VM, HTTP
    }

    public interface HTTP extends FlowInboundBuilder {

    }

    public interface ExtendedFlowInboundListenBuilder extends FlowInboundListenBuilder, PipelineBuilder {
    }

    public interface ExtendedFlowInboundPollBuilder extends FlowInboundPollBuilder, PipelineBuilder {
    }

    public interface FlowInboundBuilder {
        <T extends ListenerBuilderType> T using(Class<T> builer);
    }

    public interface PipelineBuilder {
        ExecutorBuilder execute(Class<?> clazz);

        PipelineBuilder transformWith(Class<? extends Transformer> clazz);

        PipelineBuilder send(Protocol protocol, String xx);

        PipelineBuilder send(String xx);

        <T extends ConnectorBuilder> T send(T x);
    }

    public interface ExecutorBuilder extends PipelineBuilder {
        PipelineBuilder asSingleton();
    }

    public interface ListenerBuilderType extends PipelineBuilder {

    }

    public interface CXF extends ListenerBuilderType, FlowInboundListenBuilder {
        ExtendedFlowInboundListenBuilder withClass(Class<?> clazz);
    }


    public interface FlowInboundListenBuilder extends FlowInboundBuilder {
        FlowInboundListenBuilder onPort(int port);

        FlowInboundListenBuilder onPath(String path);

        <T extends ConnectorBuilder> T send(T x);

        PipelineBuilder transformWith(Class<? extends Transformer> clazz);
    }

    public interface FlowInboundPollBuilder extends FlowInboundBuilder {
        FlowInboundListenBuilder onInterval();
    }

    public interface Transformer {
    }
}
