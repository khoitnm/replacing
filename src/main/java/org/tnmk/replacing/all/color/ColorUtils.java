package org.tnmk.replacing.all.color;

public final class ColorUtils {
    private ColorUtils(){
        //Utils
    }

    public int toRgb(int red, int blue, int green){
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        return rgb;
    }
}
