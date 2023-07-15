package org.tnmk.replacing.all.copy_specific_files;


import org.springframework.lang.Nullable;

import java.util.List;

public class CopyFolder {
    private String relativeTargetFolder;


    /**
     * If this value is not empty, then copy all files in {@link #copyAllFilesFromRelativeSourceFolder} to the target folder {@link #relativeTargetFolder}.
     * After that, continue copying specific files in {@link #copySpecificRelativeSourceFiles} to {@link #relativeTargetFolder}
     */
    @Nullable
    private String copyAllFilesFromRelativeSourceFolder;


    @Nullable
    private List<String> copySpecificRelativeSourceFiles;

    public String getRelativeTargetFolder() {
        return relativeTargetFolder;
    }

    public void setRelativeTargetFolder(String relativeTargetFolder) {
        this.relativeTargetFolder = relativeTargetFolder;
    }

    @Nullable
    public String getCopyAllFilesFromRelativeSourceFolder() {
        return copyAllFilesFromRelativeSourceFolder;
    }

    public void setCopyAllFilesFromRelativeSourceFolder(@Nullable String copyAllFilesFromRelativeSourceFolder) {
        this.copyAllFilesFromRelativeSourceFolder = copyAllFilesFromRelativeSourceFolder;
    }

    @Nullable
    public List<String> getCopySpecificRelativeSourceFiles() {
        return copySpecificRelativeSourceFiles;
    }

    public void setCopySpecificRelativeSourceFiles(@Nullable List<String> copySpecificRelativeSourceFiles) {
        this.copySpecificRelativeSourceFiles = copySpecificRelativeSourceFiles;
    }
}
