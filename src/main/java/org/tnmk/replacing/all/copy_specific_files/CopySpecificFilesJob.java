package org.tnmk.replacing.all.copy_specific_files;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.util.FileUtils;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
public class CopySpecificFilesJob {
    private static final Logger logger = LoggerFactory.getLogger(CopySpecificFilesJob.class);
    private final CopySpecificFilesProperties copySpecificFilesProperties;

    public CopySpecificFilesJob(CopySpecificFilesProperties copySpecificFilesProperties) {
        this.copySpecificFilesProperties = copySpecificFilesProperties;
    }

    public void run() throws IOException {
        String absSrcRootFolder = FileUtils.normalizeDirectoryPath(copySpecificFilesProperties.getSourceFolder());
        String absTargetRootFolder = FileUtils.normalizeDirectoryPath(copySpecificFilesProperties.getTargetFolder());

        IOUtils.createFolderIfNecessary(absTargetRootFolder);

        int i = 0;
        for (CopyFolder copySubFolder : copySpecificFilesProperties.getCopySubFolders()) {
            String relativeTargetSubFolder = copySubFolder.getRelativeTargetFolder();
            String absTargetSubFolder = FileUtils.combineFolderPath(absTargetRootFolder, relativeTargetSubFolder);
            IOUtils.createFolderIfNecessary(absTargetSubFolder);

            String copyAllFilesFromSourceFolder = copySubFolder.getCopyAllFilesFromRelativeSourceFolder();
            if (StringUtils.isNotBlank(copyAllFilesFromSourceFolder)) {
                String absSrcSubFolder = FileUtils.combineFolderPath(absSrcRootFolder, copyAllFilesFromSourceFolder);
                i = copyAllFiles(absSrcSubFolder, absTargetSubFolder, i);
            }

            for (String relativeSourceFile : copySubFolder.getCopySpecificRelativeSourceFiles()) {
                if (StringUtils.isBlank(relativeSourceFile)) {
                    continue;
                }
                String absoluteSourceFilePath = FileUtils.getFilePath(absSrcRootFolder, relativeSourceFile);
                copyFileToFolder(absoluteSourceFilePath, absTargetSubFolder, i);
                i++;
            }
        }
    }

    private int copyAllFiles(String absSrcSubFolder, String absTargetFolder, int startingFileIndex) throws IOException {
        File subFolder = new File(absSrcSubFolder);
        if (!subFolder.exists()) {
            throw new IOException("Source folder doesn't exist: " + absTargetFolder);
        }
        int fileIndex = startingFileIndex;
        File[] files = subFolder.listFiles();
        for (File file : files) {
            copyFileToFolder(file.getAbsolutePath(), absTargetFolder, fileIndex);
            fileIndex++;
        }
        return fileIndex;
    }

    private void copyFileToFolder(String absSrcFilePath, String absTargetFolder, int fileIndex) throws IOException {
        String fileExtension = FileNameUtils.getExtension(absSrcFilePath);

        String targetFileName = "" + fileIndex + "." + fileExtension;
        String absTargetFilePath = FileUtils.getFilePath(absTargetFolder, targetFileName);

        copyFile(absSrcFilePath, absTargetFilePath);
    }

    private void copyFile(String absSrcFilePath, String absTargetFilePath) throws IOException {
        Path sourcePath = Paths.get(absSrcFilePath);
        Path targetPath = Paths.get(absTargetFilePath);
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        logger.debug("Copied: "
            + "\n\tsourceFile: " + absSrcFilePath
            + "\n\ttargetFile: " + absTargetFilePath);
    }
}
