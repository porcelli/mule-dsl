/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.expression;

import org.mule.api.MuleContext;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.dsl.PropertyPlaceholder;
import org.mule.module.scripting.component.Scriptable;
import org.mule.module.scripting.filter.ScriptFilter;

import static org.mule.config.dsl.util.Preconditions.checkNotEmpty;

/**
 * Holds script related expression evaluators definitions and expose them as static methods.
 *
 * @author porcelli
 */
public final class ScriptingExpr {

    private ScriptingExpr() {
    }

    /**
     * Returns a new groovy evaluator expression based on given parameter.
     *
     * @param expr the groovy expression
     * @return the groovy expression evaluator
     * @throws IllegalArgumentException if {@code expr} param is null or empty
     */
    public static GroovyExpressionEvaluatorDefinition groovy(final String expr) throws IllegalArgumentException {
        checkNotEmpty(expr, "expr");
        return new GroovyExpressionEvaluatorDefinition(expr);
    }

    /**
     * Groovy expression evaluator definition.
     *
     * @author porcelli
     * @see org.mule.config.dsl.ExpressionEvaluatorDefinition
     */
    public static class GroovyExpressionEvaluatorDefinition extends BaseEvaluatorDefinition {
        private static final String EVALUATOR = "groovy";

        /**
         * Constructor
         *
         * @param expression the string expression
         */
        private GroovyExpressionEvaluatorDefinition(final String expression) {
            super(EVALUATOR, expression, null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ScriptFilter getFilter(final MuleContext muleContext, final PropertyPlaceholder placeholder) {

            Scriptable script = new Scriptable();
            script.setMuleContext(muleContext);
            script.setScriptEngineName(getEvaluator());
            script.setScriptText(getExpression(placeholder));
            try {
                script.initialise();
            } catch (InitialisationException e) {
                throw new RuntimeException(e);
            }

            ScriptFilter filter = new ScriptFilter();
            filter.setScript(script);

            return filter;
        }
    }


}
