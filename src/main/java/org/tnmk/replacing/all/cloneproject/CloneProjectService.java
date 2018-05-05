package org.tnmk.replacing.all.cloneproject;

import org.apache.commons.vfs2.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.cloneproject.transform.*;
import org.tnmk.replacing.all.renaming.CopyingAndReplacingService;
import org.tnmk.replacing.all.util.FileUtils;
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
        destPath = parentFolder.getAbsolutePath()+"/"+new LowerCaseWithHyphenTransform().apply(newSingularName);

        Map<String, String> renaming = new HashMap<>();

        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CamelCaseTransform());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CamelCaseUncapitalizedTransform());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new CapitalizeTransform());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new LowerCaseTransform());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new LowerCaseWithHyphenTransform());
        replaceMapWithTransform(renaming, oldSingularName, newSingularName, new UpperCaseWithUnderscoreTransform());

        this.copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
    }


    private void replaceMapWithTransform(Map<String, String> renaming, String oldName, String newName, Function<String, String> transform){
        renaming.put(transform.apply(oldName), transform.apply(newName));
    }


}
