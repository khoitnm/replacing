package org.tnmk.replacing.all.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.exception.IOException;
import org.tnmk.replacing.all.exception.UnexpectedException;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * @author khoi.tran on 7/6/17.
 */
@Service
public class CopyingService {

    public void copySubItems(String sourceDirPath, String destDirPath, List<String> excludingPatterns) {
        File sourceDir = new File(sourceDirPath);
        if (!sourceDir.exists()) {
            throw new IOException("The sourceDir doesn't exist " + sourceDir.getAbsolutePath());
        }

        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            try {
                FileUtils.forceMkdir(destDir);
            } catch (java.io.IOException e) {
                throw new IOException(
                    String.format("Cannot create folder %s: %s", destDir.getAbsolutePath(), e.getMessage()), e);
            }
        }
        try {
            FileFilter excludingFileFilter = file -> {
                for (String excludedPattern : excludingPatterns) {
                    if (file.getAbsolutePath().matches(excludedPattern)) {
                        return false;
                    }
                }
                return true;
            };
            FileUtils.copyDirectory(sourceDir, destDir, excludingFileFilter);
        } catch (java.io.IOException e) {
            throw new IOException(
                String.format("Error copying %s to %s: %s",
                    sourceDir.getAbsolutePath(), destDir.getAbsolutePath(), e.getMessage()), e);
        }

    }
}
