/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TestTimePeriod {

    @Test
    public void testConvertMillisToMillis() {
        assertThat(TimePeriod.MILLIS.toMillis(0L)).isEqualTo(0L);
        assertThat(TimePeriod.MILLIS.toMillis(1L)).isEqualTo(1L);
        assertThat(TimePeriod.MILLIS.toMillis(20L)).isEqualTo(20L);
    }

    @Test
    public void testConvertSecondsToMillis() {
        assertThat(TimePeriod.SECONDS.toMillis(0L)).isEqualTo(0L);
        assertThat(TimePeriod.SECONDS.toMillis(1L)).isEqualTo(1000L);
        assertThat(TimePeriod.SECONDS.toMillis(20L)).isEqualTo(20000L);
    }

    @Test
    public void testConvertMinutesToMillis() {
        assertThat(TimePeriod.MINUTES.toMillis(0L)).isEqualTo(0L);
        assertThat(TimePeriod.MINUTES.toMillis(1L)).isEqualTo(60000L);
        assertThat(TimePeriod.MINUTES.toMillis(20L)).isEqualTo(1200000L);
    }

    @Test
    public void testConvertHourToMillis() {
        assertThat(TimePeriod.HOURS.toMillis(0L)).isEqualTo(0L);
        assertThat(TimePeriod.HOURS.toMillis(1L)).isEqualTo(3600000L);
        assertThat(TimePeriod.HOURS.toMillis(20L)).isEqualTo(72000000L);
    }

    @Test
    public void testConvertDayToMillis() {
        assertThat(TimePeriod.DAYS.toMillis(0L)).isEqualTo(0L);
        assertThat(TimePeriod.DAYS.toMillis(1L)).isEqualTo(86400000L);
        assertThat(TimePeriod.DAYS.toMillis(20L)).isEqualTo(1728000000L);
    }

}
