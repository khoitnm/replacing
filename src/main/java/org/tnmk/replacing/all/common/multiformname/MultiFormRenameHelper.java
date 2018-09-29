package org.tnmk.replacing.all.common.multiformname;

import org.tnmk.replacing.all.common.multiformname.nametransformer.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MultiFormRenameHelper {

    /**
     * @param oldSingularName words should be separated by spaces or hyphen. Each word could be lowercase, uppercase, capitalized.
     *                        For example: "the-name-01"
     * @param newSingularName words should be separated by spaces or hyphen. Each word could be lowercase, uppercase, capitalized,
     *                        For example: "the-new-name-02"
     * @return the rename map with multi form:
     * <li>CamelCase: TheName01 -> TheNewName02</li>
     * <li>CamelCaseUncapitalize: theName01 -> theNewName02</li>
     * <li>Capitalize: Thename01 -> Thenewname02</li>
     * <li>Lowercase: thename01 -> thenewname02</li>
     * <li>LowercaseWithHyphen: the-name-01 -> the-new-name-02</li>
     * <li>UppercaseWithUnderscoe: THE_NAME_01 -> THE_NEW_NAME_02</li>
     *
     */
    public static Map<String, String> createMultiFormRenameMap(String oldSingularName, String newSingularName) {
        Map<String, String> renaming = new HashMap<>();

        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CamelCaseTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CamelCaseUncapitalizedTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CapitalizeTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new LowerCaseTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new LowerCaseWithHyphenTransformer());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new UpperCaseWithUnderscoreTransformer());

        return renaming;
    }

    private static void replaceMapWithTransform(Map<String, String> renaming, String oldName, String newName, Function<String, String> transform) {
        renaming.put(transform.apply(oldName), transform.apply(newName));
    }
}
