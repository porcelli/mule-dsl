/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.twitter;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;

import java.io.File;
import java.io.IOException;

public class TestTwitter {

    private static final String REMOTE_REPO = "src/test/resources/base-git-repo";

    @Before
    public void prepareExternalRepo() {
        try {
            FileUtils.moveDirectory(new File(REMOTE_REPO, "git"), new File(REMOTE_REPO, ".git"));
        } catch (IOException ex) {
        }
    }

    @After
    public void releaseExternalRepo() {
        try {
            FileUtils.moveDirectory(new File(REMOTE_REPO, ".git"), new File(REMOTE_REPO, "git"));
        } catch (IOException ex) {
        }
    }

    @Test
    public void simpleGit() throws InterruptedException, MuleException {
        MuleContext muleContext = Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                TwitterConnector twitterConnector = new TwitterConnector();

//                flow("MyFlow")
//                        .from("file:///Users/porcelli/test")
//                        .process(twitterConnector.cloneRepository(REMOTE_REPO).withBare(false).withBranch("master"))
//                        .send("file:///cloned-repository/")
//                        .process(twitterConnector.add("."))
//                        .process(twitterConnector.commit("here is!", "porcelli", "porcelli@porcelli.com.br"));
            }
        });

        muleContext.start();

        Thread.sleep(10000);

//        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(1);
//
//        FlowConstruct flowConstruct = muleContext.getRegistry().lookupFlowConstructs().iterator().next();
//
//        assertThat(flowConstruct.getName()).isEqualTo("MyFlow");
//        assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);
//
//        MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();
//
//        assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);
//
//        InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;
//
//        assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);
//
//        assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");
//
//        assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");
//
//        assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);
//
//        Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator();
//
//        MessageProcessor executeProcessor = iterator.next();
//
//        assertThat(executeProcessor).isNotNull().isInstanceOf(DefaultJavaComponent.class);
//
//        DefaultJavaComponent execute = (DefaultJavaComponent) executeProcessor;
//
//        assertThat(execute.getObjectType()).isEqualTo(Simple2.class);
//
//        assertThat(execute.getObjectFactory().isSingleton()).isEqualTo(true);
    }

}
