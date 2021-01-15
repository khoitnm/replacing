package org.tnmk.replacing.all.cloneproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.common.multiformname.MultiFormRenameHelper;
import org.tnmk.replacing.all.renaming.CopyingAndReplacingService;
import org.tnmk.replacing.all.renaming.ReplacingService;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CloneProjectService {
    public static final List<String> PATTERN_EXCLUDING_JAVA_PROJECT = Arrays.asList(
        // Note: We need to escape '/' with an '\', hence "\/" means '/'
        // But in string, you cannot write '\', you have to write "\\" instead.
        // Hence "\\/" means '/', hence [\\\\\\/] means '\' or '/'
        ".*\\.iml",
        ".*\\.class",
        ".*[\\\\\\/]\\.git[\\\\\\/]?",
        ".*[\\\\\\/]\\.idea[\\\\\\/]?",
        ".*[\\\\\\/]\\.gradle[\\\\\\/]?",
        ".*[\\\\\\/]build[\\\\\\/]?",
        ".*[\\\\\\/]node_modules[\\\\\\/]?"
    );

    @Autowired
    private CopyingAndReplacingService copyingAndReplacingService;

    @Autowired
    private ReplacingService replacingService;

    /**
     * This method is a little bit different from {@link #simpleCloneToTheSameParentFolder(String, String)}.
     * This will clone the project and rename every single phase which has the old project name by the new project name.
     * For example:
     * <li>the old name: java-service-template</li>
     * <li>the new name: the-new-project.</li>
     * <li>so the word `java-service-template-xxx` WILL BE RENAMED to `the-new-project-xxx` even though it's not 100% word match.</li>
     * @param sourcePath
     * @param newSingularName
     */
    public void cloneAndRenameInSameParentFolder(String sourcePath, String newSingularName){
        String oldSingularName = getFolderName(sourcePath);
        String destPath = simpleCloneToTheSameParentFolder(sourcePath, newSingularName);
        replacingService.replace(destPath, oldSingularName,newSingularName);
    }
    /**
     * Note: It only rename the exactly phases (with different upper/lower cases situations).
     * For example:
     * java-service-template will be renamed to the-new-project
     * JavaServiceTemplate to TheNewProject
     * ...
     * View more at {@link MultiFormRenameHelper#createMultiFormRenameMap(String, String)} to see all supporting word forms.
     * <p>
     * However, the phases java-service-template-xxx will not be renamed to the-new-project-xxx.
     * If you want some additional renaming phases, use {@link ReplacingService#replace(String, Map)} on the new cloned project.
     * Or can just simply use {@link #cloneAndRenameInSameParentFolder(String, String)}
     *
     * @param sourcePath
     * @param newSingularName words should be separated by spaces or hyphen. Each word could be lowercase, uppercase, capitalized,
     *                        For example: "the-name-02"
     *
     * @return destination path
     */
    public String simpleCloneToTheSameParentFolder(String sourcePath, String newSingularName) {
        String oldSingularName = getFolderName(sourcePath);
        return simpleCloneToTheSameParentFolder(sourcePath, oldSingularName, newSingularName);
    }

    private String getFolderName(String path){
        int lastPathIndex = path.lastIndexOf('/');
        lastPathIndex = Math.max(lastPathIndex, path.lastIndexOf('\\'));
        if (lastPathIndex <= 0) {
            throw new RuntimeException("The sourcePath " + path + " must have at least one path indication character (e.g. '/' or '\\'");
        }

        String folderName = path.substring(lastPathIndex);
        if (folderName.startsWith("\\") || folderName.startsWith("/")) {
            folderName = folderName.substring(1);
        }
        return folderName;
    }

    /**
     * @param sourcePath
     * @param oldSingularName words should be separated by spaces or hyphen. Each word could be lowercase, uppercase, capitalized.
     *                        For example: "the-name-01"
     * @param newSingularName words should be separated by spaces or hyphen. Each word could be lowercase, uppercase, capitalized,
     *                        For example: "the-name-02"
     * @return destination path
     */
    public String simpleCloneToTheSameParentFolder(String sourcePath, String oldSingularName, String newSingularName) {
        Map<String, String> renaming = MultiFormRenameHelper.createMultiFormRenameMap(oldSingularName, newSingularName);

        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;
        String destPath = sourcePath;//Clone to the same folder.
        File parentFolder = IOUtils.createParentFolderIfNecessary(destPath);
        String destFolderName = renaming.get(oldSingularName);
        destPath = parentFolder.getAbsolutePath() + "/" + destFolderName;

        this.copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
        return destPath;
    }


}
