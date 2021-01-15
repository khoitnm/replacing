package org.tnmk.replacing.all.common.filefilter;

import java.io.FileFilter;
import java.util.List;

public class FileFilterHelper {

    /**
     *
     * @param excludingPatterns
     * Example:
     * <code>
     *      ".*[\\/]\\.git",
     * 		".*[\\/]\\.idea",
     * 		".*[\\/]\\.gradle",
     * 		".*\\.class"
     * </code>
     * @return
     */
    public static FileFilter constructExcludeFileFilter(List<String> excludingPatterns){
        FileFilter excludingFileFilter = file -> {
            boolean shouldExclude = matchAnyPattern(file.getAbsolutePath(), excludingPatterns);
            boolean shouldInclude = !shouldExclude;
            return shouldInclude;
        };
        return excludingFileFilter;
    }

    public static boolean matchAnyPattern(String string, List<String> patterns){
        for (String pattern : patterns) {
            if (string.matches(pattern)) {
                return true;
            }
        }
        return false;
    }
}
