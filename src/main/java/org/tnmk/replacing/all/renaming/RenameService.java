package org.tnmk.replacing.all.renaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.common.multiformname.MultiFormRenameHelper;
import org.tnmk.replacing.all.exception.UnexpectedException;
import org.tnmk.replacing.all.util.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author khoi.tran on 7/5/17.
 * This service just rename the file, it doesn't replace text inside files' content.
 * If you want to replace content inside files, please use {@link ReplacingService}
 */
@Service
public class RenameService {
    public static final Logger LOGGER = LoggerFactory.getLogger(RenameService.class);
    private final TraverseFolderService traverseFolderService;
    @Autowired
    public RenameService(TraverseFolderService traverseFolderService) {
        this.traverseFolderService = traverseFolderService;
    }

    public void rename(String rootPath, String originalSingularName, String newSingularName) {
        Map<String, String> renameMap = MultiFormRenameHelper.createMultiFormRenameMap(originalSingularName, newSingularName);
        rename(rootPath, renameMap);
    }

    public void rename(String rootPath, Map<String, String> renameMap) {
        File file = new File(rootPath);
        for (String sourceText : renameMap.keySet()) {
            this.traverseFolderService.traverFile(file, currentFile -> {
                String destText = renameMap.get(sourceText);
                File renamedFile = renameFileIfMatch(currentFile, sourceText, destText);
                return renamedFile;
            });
        }
    }

    private File renameFileIfMatch(File file, String sourceText, String destText) {
        String encoding = StandardCharsets.UTF_8.name();
        String parentFolderPath = file.getParent();
        String currentFileName = file.getName();
        String encodedCurrentFileName = StringUtils.encode(currentFileName, encoding);
        String encodedSourceText = StringUtils.encode(sourceText, encoding);
//
//        String currentFileNameEncode = StringUtils.charsetEncode(currentFileName, "ISO-8859-1");
//        String sourceTextEncode = StringUtils.charsetEncode(sourceText, "ISO-8859-1");
        if (encodedCurrentFileName.contains(encodedSourceText)) {
            String newFileName = encodedCurrentFileName.replace(sourceText, destText);
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
}