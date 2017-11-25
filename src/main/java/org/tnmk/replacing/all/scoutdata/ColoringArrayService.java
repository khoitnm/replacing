package org.tnmk.replacing.all.scoutdata;

import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.color.ColorUtils;

import java.awt.*;
import java.util.List;


@Service
public class ColoringArrayService {

    /**
     * @param highestColor the color of the highest value
     * @param lowestColor  the color of the lowest value
     * @param numbers
     * @return
     */
    public List<Color> toColors(Color highestColor, Color lowestColor, List<? extends Number> numbers) {
        float[] highestHsb = ColorUtils.rbgToHsb(highestColor.getRed(), highestColor.getBlue(), highestColor.getGreen());
        float[] lowestHsb = ColorUtils.rbgToHsb(lowestColor.getRed(), lowestColor.getBlue(), lowestColor.getGreen());
        for (Number number : numbers) {
            
        }
    }
}
