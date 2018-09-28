package org.tnmk.replacing.all.cloneproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.common.nametransformer.*;
import org.tnmk.replacing.all.renaming.CopyingAndReplacingService;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
     *
     * @param sourcePath
     * @param oldSingularName words should be separated by spaces. Each word could be lowercase, uppercase, capitalized,
     * @param newSingularName words should be separated by spaces. Each word could be lowercase, uppercase, capitalized,
     */
    public void simpleCloneToTheSameFolder(String sourcePath, String oldSingularName, String newSingularName) {
        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;
        String destPath = sourcePath;//Clone to the same folder.
        File parentFolder = IOUtils.createParentFolderIfNecessary(destPath);
        destPath = parentFolder.getAbsolutePath()+"/"+new LowerCaseWithHyphenTransformer().apply(newSingularName);

        Map<String, String> renaming = MultiFormRenameHelper.createMultiFormRenameMap(oldSingularName, newSingularName);
        this.copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
    }




}
