/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.internal;

import org.mule.api.MuleContext;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.dsl.ConfigurationException;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.module.scripting.component.ScriptComponent;
import org.mule.module.scripting.component.Scriptable;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;
import static org.mule.config.dsl.util.Preconditions.checkNotNull;

/**
 * Internal class that builds a {@link ScriptComponentBuilder} based on given parameters.
 *
 * @author porcelli
 * @see org.mule.config.dsl.PipelineBuilder#executeScript(String, String)
 * @see org.mule.config.dsl.PipelineBuilder#executeScript(String, org.mule.config.dsl.util.FileRefBuilder)
 * @see org.mule.config.dsl.PipelineBuilder#executeScript(String, org.mule.config.dsl.util.ClasspathBuilder)
 * @see org.mule.config.dsl.PipelineBuilder#executeScript(org.mule.config.dsl.ScriptLanguage, String)
 * @see org.mule.config.dsl.PipelineBuilder#executeScript(org.mule.config.dsl.ScriptLanguage, org.mule.config.dsl.util.FileRefBuilder)
 * @see org.mule.config.dsl.PipelineBuilder#executeScript(org.mule.config.dsl.ScriptLanguage, org.mule.config.dsl.util.ClasspathBuilder)
 */
public class ScriptComponentBuilder implements DSLBuilder<ScriptComponent> {

    private final String language;
    private final String script;

    /**
     * @param language the script language
     * @param script   the script iself
     */
    public ScriptComponentBuilder(final String language, final String script) throws IllegalArgumentException {
        checkNotEmpty(language, "language");
        checkNotEmpty(script, "script");

        this.language = language;
        this.script = script;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScriptComponent build(final MuleContext muleContext, final PropertyPlaceholder placeholder) throws NullPointerException, ConfigurationException, IllegalStateException {
        checkNotNull(muleContext, "muleContext");
        checkNotNull(placeholder, "placeholder");

        Scriptable scriptable = new Scriptable();
        scriptable.setMuleContext(muleContext);
        scriptable.setScriptEngineName(language);
        scriptable.setScriptText(script);
        try {
            scriptable.initialise();
        } catch (InitialisationException e) {
            throw new RuntimeException(e);
        }

        ScriptComponent component = new ScriptComponent();
        component.setMuleContext(muleContext);
        component.setScript(scriptable);

        return component;
    }


}
