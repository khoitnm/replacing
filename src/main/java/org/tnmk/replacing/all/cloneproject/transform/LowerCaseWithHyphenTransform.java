package org.tnmk.replacing.all.cloneproject.transform;


import org.tnmk.replacing.all.util.StringUtils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LowerCaseWithHyphenTransform implements Function<String, String> {

    @Override
    public String apply(String wordsString) {
        String[] wordsList = StringUtils.splitToWords(wordsString);
        String className = Arrays.stream(wordsList).map(
                String::toLowerCase
        ).collect(Collectors.joining("-"));
        return className;
    }
}