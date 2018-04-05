package org.tnmk.replacing.all.renaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.replacing.all.exception.UnexpectedException;
import org.tnmk.replacing.all.util.FileUtils;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;
import java.util.Map;

/**
 * @author khoi.tran on 7/5/17.
 */
@Service
public class ReplacingService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReplacingService.class);

    @Autowired
    private TraverseFolderService traverseFolderService;

    public void rename(String rootPath, Map<String, String> renameMap) {
        File file = new File(rootPath);
        for (String sourceText : renameMap.keySet()) {
            this.traverseFolderService.traverFile(file, currentFile -> {
                String destText = renameMap.get(sourceText);
                File renamedFile = renameFileIfMatch(currentFile, sourceText, destText);
                if (renamedFile.isFile()) {
                    replaceContentFile(renamedFile, sourceText, destText);
                }
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
                        String.format("Cannot addingLine from %s to %s", file.getAbsolutePath(), newFilePath));
            }
            return newFile;
        } else {
            return file;
        }
    }

    private boolean replaceContentFile(File file, String sourceText, String destText) {
        if (!FileUtils.isTextFile(file.getAbsolutePath())) {
            return false;
        }
        String content = IOUtils.loadTextFileInSystem(file.getAbsolutePath());
        if (content.contains(sourceText)) {
            content = StringUtils.replace(content, sourceText, destText);
//            content = content.replaceAll(sourceText, destText);
            IOUtils.writeTextToFile(file.getAbsolutePath(), content);
            LOGGER.info("Replace content of file: \n\tfile: {}", file.getAbsoluteFile());
            return true;
        } else {
            return false;
        }
    }
}