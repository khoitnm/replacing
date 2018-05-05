package org.tnmk.replacing.all.cloneproject.transform;


import java.util.function.Function;

public class CamelCaseUncapitalizedTransform implements Function<String, String> {
    private CamelCaseTransform camelCaseTransform = new CamelCaseTransform();
    @Override
    public String apply(String wordsString) {
        String className = camelCaseTransform.apply(wordsString);
        return org.apache.commons.lang3.StringUtils.uncapitalize(className);
    }
}