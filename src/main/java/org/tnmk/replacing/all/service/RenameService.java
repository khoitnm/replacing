package org.tnmk.replacing.all.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.exception.UnexpectedException;

import java.io.File;
import java.util.Map;

/**
 * @author khoi.tran on 7/5/17.
 */
@Service
public class RenameService {
    public static final Logger LOGGER = LoggerFactory.getLogger(RenameService.class);

    @Autowired
    private TraverseFolderService traverseFolderService;

    public void rename(String rootPath, Map<String, String> renameMap) {
        File file = new File(rootPath);
        for (String sourceText : renameMap.keySet()) {
            traverseFolderService.traverFile(file, currentFile -> {
                String destText = renameMap.get(sourceText);
                File renamedFile = renameFileIfMatch(currentFile, sourceText, destText);
                return renamedFile;
            });
        }
    }

    private File renameFileIfMatch(File file, String sourceText, String destText) {
        String parentFolderPath = file.getParent();
        String currentFileName = file.getName();
        if (currentFileName.contains(sourceText)) {
            String newFileName = currentFileName.replace(sourceText, destText);
            String newFilePath = parentFolderPath + "/" + newFileName;
            File newFile = new File(newFilePath);
            boolean renameSuccess = file.renameTo(newFile);
            LOGGER.info("Rename: \n\tfrom: {} \n\tto: {}", file.getAbsoluteFile(), newFile.getAbsoluteFile());
            if (!renameSuccess) {
                throw new UnexpectedException(
                    String.format("Cannot rename from %s to %s", file.getAbsolutePath(), newFilePath));
            }
            return newFile;
        } else {
            return file;
        }
    }
}