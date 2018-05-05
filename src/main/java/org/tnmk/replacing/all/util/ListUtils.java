package org.tnmk.replacing.all.util;

import java.util.Collection;

public final class ListUtils {

    /**
     * @param values
     * @param lowHigh if you want to find lowest value, use -1. If you want to find highest value, use 1.
     * @param <T>
     * @return
     */
    public static <T extends Comparable> ValuePosition<T> findTop(Collection<T> values, int lowHigh) {
        int index = 0;
        int indexHighest = -1;
        T highest = null;
        for (T value : values) {
            if (indexHighest < 0 || highest == null || value == null || (highest.compareTo(value) * lowHigh < 0)) {
                highest = value;
                indexHighest = index;
            }
            index++;
        }
        return new ValuePosition<T>(index, highest);
    }

    public static class ValuePosition<T> {
        private final int index;
        private final T value;

        public ValuePosition(int index, T value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return this.index;
        }

        public T getValue() {
            return this.value;
        }
    }
}
