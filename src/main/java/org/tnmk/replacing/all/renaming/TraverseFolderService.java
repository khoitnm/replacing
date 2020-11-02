package org.tnmk.replacing.all.renaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.function.Function;

/**
 * @author khoi.tran on 7/4/17.
 */
@Service
public class TraverseFolderService {
    public static final Logger LOGGER = LoggerFactory.getLogger(TraverseFolderService.class);

    /**
     * @param file this could be a file or a folder. This method will traverse that folder and all of children files, and then apply the action on each of them.
     * @param action do some action on the traversed file.
     *               If renaming or changing file, return the new file.
     *               If deleting file, return null
     *               If don't want to lookup into file, return null
     */
    public void traverseFile(File file, Function<File, File> action) {
        if (file.exists()) {
            File changedFile = action.apply(file);
            if (changedFile == null) {
                return;
            }
            if (changedFile.exists() && changedFile.isDirectory()) {
                File[] children = changedFile.listFiles();
                for (File child : children) {
                    traverseFile(child, action);
                }
            }
        } else {
            return;
        }
    }
}
