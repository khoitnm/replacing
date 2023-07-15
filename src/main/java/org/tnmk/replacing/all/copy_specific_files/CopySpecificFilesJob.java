package org.tnmk.replacing.all.copy_specific_files;

import org.apache.commons.compress.utils.FileNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.util.FileUtils;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CopySpecificFilesJob {
    private static final Logger logger = LoggerFactory.getLogger(CopySpecificFilesJob.class);
    private final CopySpecificFilesProperties copySpecificFilesProperties;

    public CopySpecificFilesJob(CopySpecificFilesProperties copySpecificFilesProperties) {
        this.copySpecificFilesProperties = copySpecificFilesProperties;
    }

    public void run() throws IOException {
        File targetFolder = IOUtils.createFolderIfNecessary(copySpecificFilesProperties.getTargetFolder());
        int i = 0;
        for (String relativeSourceFilesPath : copySpecificFilesProperties.getRelativeFilesPaths()) {
            String absoluteSourcePath = FileUtils.getFilePath(copySpecificFilesProperties.getSourceFolder(), relativeSourceFilesPath);
            String fileExtension = FileNameUtils.getExtension(absoluteSourcePath);

            String targetFileName = "" + i + "." + fileExtension;
            String absoluteTargetPath = FileUtils.getFilePath(copySpecificFilesProperties.getTargetFolder(), targetFileName);

            copyFile(absoluteSourcePath, absoluteTargetPath);
            i++;
        }
    }

    private void copyFile(String absoluteSourcePath, String absoluteTargetPath) throws IOException {
        Path sourcePath = Paths.get(absoluteSourcePath);
        Path targetPath = Paths.get(absoluteTargetPath);
        Files.copy(sourcePath, targetPath);

        logger.debug("Copied file {}", absoluteTargetPath);
    }
}
