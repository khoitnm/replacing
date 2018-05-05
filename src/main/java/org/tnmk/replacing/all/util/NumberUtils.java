package org.tnmk.replacing.all.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Version 1.0.2:
 * - Rename NumberUtil to NumberUtils.
 */
public final class NumberUtils {
    private NumberUtils() {
    }

    public static Integer toIntegerIgnoreNull(Object num) {
        if (num == null) {
            return null;
        }
        if (num instanceof String && StringUtils.isBlank((String) num)) {
            return null;
        }
        return toInteger(num);
    }

    public static int toInteger(Object num) {
        if (num instanceof Integer) {
            return (Integer) num;
        } else if (num instanceof Number) {
            return ((Number) num).intValue();
        } else {
            return Integer.valueOf(num.toString());
        }
    }

    public static Long toLongIfPossible(Object num) {
        if (num == null) {
            return null;
        }
        if (num instanceof Long) {
            return (Long) num;
        } else if (num instanceof Number) {
            return ((Number) num).longValue();
        } else {
            Long result;
            try {
                result = Long.valueOf(num.toString());
            } catch (NumberFormatException ex) {
                result = null;
            }
            return result;
        }
    }

    public static Double toDoubleIfPossible(Object num) {
        if (num == null) {
            return null;
        }
        if (num instanceof Double) {
            return (Double) num;
        } else if (num instanceof Number) {
            return ((Number) num).doubleValue();
        } else {
            Double result;
            try {
                result = Double.valueOf(num.toString());
            } catch (NumberFormatException ex) {
                result = null;
            }
            return result;
        }
    }

    public static Integer toIntegerIfPossible(Object num) {
        if (num instanceof Integer) {
            return (Integer) num;
        } else if (num instanceof Number) {
            return ((Number) num).intValue();
        } else {
            Integer result;
            try {
                result = Integer.valueOf(num.toString());
            } catch (NumberFormatException ex) {
                result = null;
            }
            return result;
        }
    }

    public static long toLong(Object num) {
        if (num instanceof Long) {
            return (Long) num;
        } else if (num instanceof Number) {
            return ((Number) num).longValue();
        } else {
            return Long.valueOf(num.toString());
        }
    }

    public static short toShort(Object num) {
        if (num instanceof Short) {
            return (Short) num;
        } else if (num instanceof Number) {
            return ((Number) num).shortValue();
        } else {
            return Short.valueOf(num.toString());
        }
    }

}
