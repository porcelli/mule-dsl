/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * Interface that holds an already set up message processor definition.
 * <p/>
 * <b>Note:</b>  cloud connectors relies on this interface to be executed by dsl.
 *
 * @author porcelli
 * @see org.mule.api.processor.MessageProcessor
 *
 */
public interface MessageProcessorDefinition extends Definition {

}
