/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.module.git;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleException;
import org.mule.config.dsl.AbstractModule;
import org.mule.config.dsl.Mule;

import java.io.File;
import java.io.IOException;

import static org.mule.config.dsl.expression.CoreExpr.string;

public class TestGit {

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
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {

                GitConnector gitConnector = new GitConnector();
                gitConnector.setDirectory("/cloned-repository");

                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .process(gitConnector.cloneRepository(REMOTE_REPO).withBare(false).withBranch("master"))
                        .send("file:///cloned-repository/")
                        .process(gitConnector.add("."))
                        .process(gitConnector.commit(string("something here"), "porcelli", "porcelli@porcelli.com.br"));
            }
        });
    }

}
