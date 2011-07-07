/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl.twitter.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.twitter.StatusesUpdateMessageProcessorDefinition;
import org.mule.ibeans.twitter.config.StatusesUpdateMessageProcessor;

import static org.mule.config.dsl.expression.CoreExpr.string;

public class StatusesUpdateMessageProcessorBuilder implements StatusesUpdateMessageProcessorDefinition, Builder<StatusesUpdateMessageProcessor> {

    private final IBeansTwitterReference reference;

    private String status = null;
    private ExpressionEvaluatorBuilder statusExp = null;

    private String replyId = null;
    private Double latitude = null;
    private Double longitude = null;
    private String placeId = null;
    private Boolean displayCoordinates = null;
    private Boolean trimUser = null;

    private ExpressionEvaluatorBuilder replyIdExp = null;
    private ExpressionEvaluatorBuilder latitudeExp = null;
    private ExpressionEvaluatorBuilder longitudeExp = null;
    private ExpressionEvaluatorBuilder placeIdExp = null;
    private ExpressionEvaluatorBuilder displayCoordinatesExp = null;
    private ExpressionEvaluatorBuilder trimUserExp = null;

    public StatusesUpdateMessageProcessorBuilder(IBeansTwitterReference reference, String status) {
        this.reference = reference;
        this.status = status;
    }

    public StatusesUpdateMessageProcessorBuilder(IBeansTwitterReference reference, ExpressionEvaluatorBuilder statusExp) {
        this.reference = reference;
        this.statusExp = statusExp;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withReplyId(String replyId) {
        this.replyId = replyId;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withReplyId(ExpressionEvaluatorBuilder replyIdExp) {
        this.replyIdExp = replyIdExp;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withLatitude(ExpressionEvaluatorBuilder latitudeExp) {
        this.latitudeExp = latitudeExp;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withLongitude(ExpressionEvaluatorBuilder longitudeExp) {
        this.longitudeExp = longitudeExp;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withPlaceId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withPlaceId(ExpressionEvaluatorBuilder placeIdExp) {
        this.placeIdExp = placeIdExp;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withDisplayCoordinates(boolean displayCoordinates) {
        this.displayCoordinates = displayCoordinates;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withDisplayCoordinates(ExpressionEvaluatorBuilder displayCoordinatesExp) {
        this.displayCoordinatesExp = displayCoordinatesExp;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withTrimUser(boolean trimUser) {
        this.trimUser = trimUser;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessorDefinition withTrimUser(ExpressionEvaluatorBuilder trimUserExp) {
        this.trimUserExp = trimUserExp;
        return this;
    }

    @Override
    public StatusesUpdateMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        StatusesUpdateMessageProcessor mp = new StatusesUpdateMessageProcessor();

        mp.setMuleContext(muleContext);
        mp.setObject(reference.getObject(muleContext));

        if (statusExp != null) {
            mp.setStatus(statusExp.toString(placeholder));
        } else {
            mp.setStatus(status);
        }

        if (replyIdExp != null) {
            mp.setReplyId(replyIdExp.toString(placeholder));
        } else {
            mp.setReplyId(replyId);
        }

        if (latitudeExp != null) {
            mp.setLatituide(latitudeExp.toString(placeholder));
        } else {
            mp.setLatituide(latitude);
        }

        if (longitudeExp != null) {
            mp.setLongitude(longitudeExp.toString(placeholder));
        } else {
            mp.setLongitude(longitude);
        }

        if (placeIdExp != null) {
            mp.setPlaceId(placeIdExp.toString(placeholder));
        } else {
            mp.setPlaceId(placeId);
        }

        if (displayCoordinatesExp != null) {
            mp.setDisplayCoordinates(displayCoordinatesExp.toString(placeholder));
        } else if (displayCoordinates != null) {
            mp.setDisplayCoordinates(string(placeId.toString()));
        } else {
            mp.setDisplayCoordinates(null);
        }

        if (trimUserExp != null) {
            mp.setTrimUser(trimUserExp.toString(placeholder));
        } else if (trimUser != null) {
            mp.setTrimUser(string(trimUser.toString()));
        } else {
            mp.setTrimUser(null);
        }

        return mp;
    }
}
