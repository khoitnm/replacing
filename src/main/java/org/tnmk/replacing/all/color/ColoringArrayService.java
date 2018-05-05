package org.tnmk.replacing.all.color;

import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.util.ListUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ColoringArrayService {

    /**
     * @param highestColor the color of the highest value
     * @param lowestColor  the color of the lowest value
     * @param numbers
     * @return
     */
    public Map<Double, Color> toColors(Color highestColor, Color lowestColor, List<Double> numbers) {
        Map<Double, Color> valueColors = new HashMap<>();
        float[] highestHsb = ColorUtils.rgbToHsb(highestColor.getRed(), highestColor.getBlue(), highestColor.getGreen());
        float[] lowestHsb = ColorUtils.rgbToHsb(lowestColor.getRed(), lowestColor.getBlue(), lowestColor.getGreen());
        float[] rangeHsb = new float[highestHsb.length];
        for (int i = 0; i < rangeHsb.length; i++) {
            rangeHsb[i] = highestHsb[i] - lowestHsb[i];
        }
        ListUtils.ValuePosition<Double> highest = ListUtils.findTop(numbers, 1);
        ListUtils.ValuePosition<Double> lowest = ListUtils.findTop(numbers, -1);
        double range = highest.getValue() - lowest.getValue();
        //Don't reuse method toColor because we want to optimize performance (reused rangeHsb value)
        for (Double number : numbers) {
            double percentageInRange = (number - lowest.getValue()) / range;
            float[] correspondingHsb = new float[lowestHsb.length];
            for (int i = 0; i < correspondingHsb.length; i++) {
                correspondingHsb[i] = lowestHsb[i] + rangeHsb[i] * (float) percentageInRange;
            }
            Color correspondingColor = ColorUtils.hsbToRbg(correspondingHsb);
            valueColors.put(number, correspondingColor);
        }
        return valueColors;
    }

    public Color toColor(Color lowestColor, Color highestColor, Double lowestValue, Double highestValue, Double number) {
        float[] highestHsb = ColorUtils.rgbToHsb(highestColor.getRed(), highestColor.getGreen(), highestColor.getBlue());
        float[] lowestHsb = ColorUtils.rgbToHsb(lowestColor.getRed(), lowestColor.getGreen(), lowestColor.getBlue());
        float[] rangeHsb = new float[highestHsb.length];
        for (int i = 0; i < rangeHsb.length; i++) {
            rangeHsb[i] = highestHsb[i] - lowestHsb[i];
        }
        double range = highestValue - lowestValue;

        double percentageInRange = (number - lowestValue) / range;
        float[] correspondingHsb = new float[lowestHsb.length];
        for (int i = 0; i < correspondingHsb.length; i++) {
            correspondingHsb[i] = lowestHsb[i] + rangeHsb[i] * (float) percentageInRange;
        }
        Color correspondingColor = ColorUtils.hsbToRbg(correspondingHsb);
        return correspondingColor;
    }

}
