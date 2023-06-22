package org.tnmk.replacing.all.unzip;

import com.github.junrar.exception.RarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.renaming.TraverseFolderService;
import org.tnmk.replacing.all.util.IOUtils;
import org.tnmk.replacing.all.util.ZipUtils;

import java.io.File;
import java.io.IOException;

@Service
public class UnzipService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UnzipService.class);

    @Autowired
    private TraverseFolderService traverseFolderService;

    public void unzipRecursive(String rootPath) {
        File file = new File(rootPath);
        this.traverseFolderService.traverseFile(file, currentFile -> {
            String absPath = currentFile.getAbsolutePath();
            File parentFolder = IOUtils.createParentFolderIfNecessary(absPath);
            if (ZipUtils.isCompressedFileName(currentFile.getName())) {
                try {
                    String target = ZipUtils.unzip(currentFile.getAbsolutePath(), parentFolder.getAbsolutePath(), true);
                    LOGGER.info("Unzip {} to {}", currentFile.getAbsolutePath(), target);
                } catch (RarException | IOException e) {
                    throw new IllegalStateException("Cannot extract zip file: " + currentFile.getAbsolutePath());
                }
            }
            return currentFile;
        });
    }
}
