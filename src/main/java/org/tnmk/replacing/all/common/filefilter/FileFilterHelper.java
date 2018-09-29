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
            for (String excludedPattern : excludingPatterns) {
                if (file.getAbsolutePath().matches(excludedPattern)) {
                    return false;
                }
            }
            return true;
        };
        return excludingFileFilter;
    }
}
