package org.tnmk.replacing.all.common.multiformname.nametransformer;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LowerCaseWithHyphenTransformer implements Function<String, String> {

    @Override
    public String apply(String wordsString) {
        List<String> wordsList = SplitWordsHelper.splitWordsBySpaceOrHyphenOrUnderscore(wordsString);
        String className = wordsList.stream().map(
                String::toLowerCase
        ).collect(Collectors.joining("-"));
        return className;
    }
}