package org.tnmk.replacing.all.cloneproject.transform;


import org.tnmk.replacing.all.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CapitalizeTransform implements Function<String, String> {

    @Override
    public String apply(String wordsString) {
        List<String> wordsList = SplitWordsHelper.splitWords(wordsString);
        String className = wordsList.stream().collect(Collectors.joining());
        return org.apache.commons.lang3.StringUtils.capitalize(className);
    }
}