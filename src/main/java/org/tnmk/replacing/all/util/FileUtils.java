package org.tnmk.replacing.all.util;

import org.apache.commons.io.FilenameUtils;

/**
 * version: 1.0.1
 * Rename: FileUtil -> FileUtils
 *
 * @author khoi.tran on 4/28/17.
 */
public class FileUtils {
    public static String getFileExtension(String filePath) {
        return FilenameUtils.getExtension(filePath);
    }
}
