package org.tnmk.replacing.all.copy_specific_files;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "copy-specific-files")
public class CopySpecificFilesProperties {
    private String sourceFolder;
    private String targetFolder;

    private List<CopyFolder> copySubFolders;

    public List<CopyFolder> getCopySubFolders() {
        return copySubFolders;
    }

    public void setCopySubFolders(List<CopyFolder> copySubFolders) {
        this.copySubFolders = copySubFolders;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }
}
