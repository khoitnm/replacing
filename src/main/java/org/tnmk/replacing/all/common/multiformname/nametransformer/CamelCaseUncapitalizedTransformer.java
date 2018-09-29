package org.tnmk.replacing.all.common.multiformname.nametransformer;


import java.util.function.Function;

public class CamelCaseUncapitalizedTransformer implements Function<String, String> {
    private CamelCaseTransformer camelCaseTransformer = new CamelCaseTransformer();
    @Override
    public String apply(String wordsString) {
        String className = camelCaseTransformer.apply(wordsString);
        return org.apache.commons.lang3.StringUtils.uncapitalize(className);
    }
}