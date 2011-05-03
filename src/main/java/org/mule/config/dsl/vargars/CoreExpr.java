/*
 * $Id: 20811 2011-05-03 18:11:20Z porcelli $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.dsl.vargars;

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


    public interface GenericExpressionEvaluatorBuilder extends TempModel.ExpressionEvaluatorBuilder {

    }

    public interface GroovyExpressionEvaluatorBuilder extends TempModel.ExpressionEvaluatorBuilder {
        public GroovyExpressionEvaluatorBuilder file(String filePath);

        public GroovyExpressionEvaluatorBuilder file(File file);

        public GroovyExpressionEvaluatorBuilder expr(String filePath);

    }

}
