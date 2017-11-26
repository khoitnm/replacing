package org.tnmk.replacing.all.util;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Version: 1.0.1
 *
 * @author khoi.tran on 8/17/16.
 */
public class DateTimeUtils {
    public static final String PATTERN_FILEPATH = "yyyy-MM-dd_HH-mm-ss";

    public static Instant toInstant(LocalDate localDate) {
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    public static LocalDateTime toLocalDateTimePatternISO(String dateString) {
        LocalDateTime localDateTime;
        if (StringUtils.isNotBlank(dateString)) {
            localDateTime = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(dateString));
        } else {
            localDateTime = null;
        }
        return localDateTime;
    }

    public static String formatLocalDate(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTimeForFilePath() {
        return formatLocalDateTime(LocalDateTime.now(), PATTERN_FILEPATH);
    }
}
