/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.expression;

import org.mule.config.dsl.ExpressionEvaluatorBuilder;

import java.io.File;

public final class CoreExpr {

    private CoreExpr() {
    }

    public static GenericExpressionEvaluatorBuilder xpath(String expr) {
        return null;
    }

    public static GenericExpressionEvaluatorBuilder bean(String expr) {
        return null;
    }

    public static GroovyExpressionEvaluatorBuilder groovy() {
        return null;
    }


    public interface GenericExpressionEvaluatorBuilder extends ExpressionEvaluatorBuilder {

    }

    public interface GroovyExpressionEvaluatorBuilder extends ExpressionEvaluatorBuilder {
        public ExpressionEvaluatorBuilder file(String filePath);

        public ExpressionEvaluatorBuilder file(File file);

        public ExpressionEvaluatorBuilder classpath(String classpath);

        public ExpressionEvaluatorBuilder expr(String filePath);

    }

}
