/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * Enum that defines a set of periods of time recognized by the system, that is
 * <code>MILLIS</code>, <code>SECONDS</code>, <code>MINUTES</code>,
 * <code>HOURS</code>, and <code>DAYS</code>.
 * <p/>
 * This enum also provides a public method that converts a value from that period to <code>MILLIS</code>.
 *
 * @author porcelli
 */
public enum TimePeriod {

    MILLIS(null, 1), SECONDS(MILLIS, 1000L), MINUTES(SECONDS, 60L), HOURS(MINUTES, 60L), DAYS(HOURS, 24L);

    private final long convertRateToMillis;

    /**
     * @param parent              the parent time period
     * @param convertRateToMillis the convert rate that should be applied
     *                            to the parent rate to convert it to millis.
     *                            in case that parent is null, uses just it.
     */
    private TimePeriod(TimePeriod parent, long convertRateToMillis) {
        if (parent == null) {
            this.convertRateToMillis = convertRateToMillis;
        } else {
            this.convertRateToMillis = parent.getConvertRateToMillis() * convertRateToMillis;
        }
    }

    /**
     * Returns the convert rate to millis.
     *
     * @return the convert rate to millis
     */
    public long getConvertRateToMillis() {
        return convertRateToMillis;
    }

    /**
     * Converts the given {@code duration} to millis.
     *
     * @param duration the duration to be converted to millis
     * @return the durations in millis
     */
    public long toMillis(long duration) {
        return duration * getConvertRateToMillis();
    }
}