package org.tnmk.replacing.all.cloneproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.common.multiformname.MultiFormRenameHelper;
import org.tnmk.replacing.all.common.multiformname.nametransformer.LowerCaseWithHyphenTransformer;
import org.tnmk.replacing.all.renaming.CopyingAndReplacingService;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CloneProjectService {
    public static final List<String> PATTERN_EXCLUDING_JAVA_PROJECT = Arrays.asList(
            ".*[\\/]\\.git",
            ".*[\\/]\\.idea",
            ".*[\\/]\\.gradle",
            ".*\\.class");
    @Autowired
    private CopyingAndReplacingService copyingAndReplacingService;


    /**
     * @param sourcePath
     * @param newSingularName words should be separated by spaces or hyphen. Each word could be lowercase, uppercase, capitalized,
     *                        For example: "the-name-02"
     */
    public void simpleCloneToTheSameParentFolder(String sourcePath, String newSingularName) {
        int lastPathIndex = sourcePath.lastIndexOf('/');
        lastPathIndex = Math.max(lastPathIndex, sourcePath.lastIndexOf('\\'));
        if (lastPathIndex <= 0) {
            throw new RuntimeException("The sourcePath " + sourcePath + " must have at least one path indication character (e.g. '/' or '\\'");
        }

        String oldSingularName = sourcePath.substring(lastPathIndex);
        if (oldSingularName.startsWith("\\") || oldSingularName.startsWith("/")) {
            oldSingularName = oldSingularName.substring(1);
        }
        simpleCloneToTheSameParentFolder(sourcePath, oldSingularName, newSingularName);
    }

    /**
     * @param sourcePath
     * @param oldSingularName words should be separated by spaces or hyphen. Each word could be lowercase, uppercase, capitalized.
     *                        For example: "the-name-01"
     * @param newSingularName words should be separated by spaces or hyphen. Each word could be lowercase, uppercase, capitalized,
     *                        For example: "the-name-02"
     */
    public void simpleCloneToTheSameParentFolder(String sourcePath, String oldSingularName, String newSingularName) {
        Map<String, String> renaming = MultiFormRenameHelper.createMultiFormRenameMap(oldSingularName, newSingularName);

        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;
        String destPath = sourcePath;//Clone to the same folder.
        File parentFolder = IOUtils.createParentFolderIfNecessary(destPath);
        String destFolderName = renaming.get(oldSingularName);
        destPath = parentFolder.getAbsolutePath() + "/" + destFolderName;

        this.copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
    }


}
