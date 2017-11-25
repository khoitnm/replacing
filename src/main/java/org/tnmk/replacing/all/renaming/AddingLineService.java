package org.tnmk.replacing.all.renaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;

/**
 * @author khoi.tran on 7/5/17.
 */
@Service
public class AddingLineService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AddingLineService.class);

    @Autowired
    private TraverseFolderService traverseFolderService;

    /**
     * @param lineIndex      -1: add to the last line. 0: add to the first line. At this moment, it doesn't support other line index yet.
     * @param rootFolderPath
     * @param addingContent
     */
    public void addingLine(String rootFolderPath, int lineIndex, String addingContent) {
        File file = new File(rootFolderPath);
        traverseFolderService.traverFile(file, currentFile -> {
            if (currentFile.isFile()) {
                addingContentFile(currentFile, lineIndex, addingContent);
            }
            return currentFile;
        });
    }

    private boolean addingContentFile(File file, int lineIndex, String addingContent) {
        if (!IOUtils.isTextFile(file.getAbsolutePath())) {
            return false;
        }
        String content = IOUtils.loadTextFileInSystem(file.getAbsolutePath());
        if (lineIndex == 0) {
            content = addingContent + "\n" + content;
        } else if (lineIndex < 0) {
            content += '\n' + addingContent;
        }

        IOUtils.writeTextToFile(file.getAbsolutePath(), content);
        LOGGER.info("Replace content of file: \n\tfile: {}", file.getAbsoluteFile());
        return true;
    }
}