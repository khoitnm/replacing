package org.tnmk.replacing.all.common.multiformname.nametransformer;

import org.tnmk.replacing.all.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitWordsHelper {
    /**
     * At first, this method will split words by space.
     * After that, it will continue split words by hyphen "-".
     * @param phase
     * @return
     */
    public static List<String> splitWordsBySpaceOrHyphen(String phase){
        String[] splitWordsArray = StringUtils.splitToWords(phase);
        List<String> splitWords = Arrays.asList(splitWordsArray);
        splitWords = splitWordsByCharacter(splitWords, "-");
//        splitWords = splitWordsByCharacter(splitWords, "_");
        return splitWords;
    }

    private static List<String> splitWordsByCharacter(List<String> words, String delimiter){
        List<String> result = new ArrayList<>();
        for (String word : words) {
            String[] splitedWords = word.split(delimiter);
            result.addAll(Arrays.asList(splitedWords));
        }
        return result;
    }
}
