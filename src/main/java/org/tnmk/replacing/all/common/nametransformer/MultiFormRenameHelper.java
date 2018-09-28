package org.tnmk.replacing.all.common.nametransformer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MultiFormRenameHelper {

    public static Map<String, String> createMultiFormRenameMap(String oldSingularName, String newSingularName){
        Map<String, String> renaming = new HashMap<>();

        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CamelCaseTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CamelCaseUncapitalizedTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CapitalizeTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new LowerCaseTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new LowerCaseWithHyphenTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new UpperCaseWithUnderscoreTransformer());

        return renaming;
    }

    private static void replaceMapWithTransform(Map<String, String> renaming, String oldName, String newName, Function<String, String> transform){
        renaming.put(transform.apply(oldName), transform.apply(newName));
    }
}
