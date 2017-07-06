package org.tnmk.replacing.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tnmk.replacing.all.service.CopyingAndReplacingService;
import org.tnmk.replacing.all.service.FolderReplacingService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author khoi.tran on 7/4/17.
 */
@SpringBootApplication
public class ReplacingApplication implements CommandLineRunner {
    public static final List<String> PATTERN_EXCLUDING_JAVA_PROJECT = Arrays.asList(
        ".*[\\/]\\.git",
        ".*[\\/]\\.idea",
        ".*[\\/]\\.gradle",
        ".*\\.class");
    @Autowired
    private CopyingAndReplacingService copyingAndReplacingService;

    public static void main(String[] args) {
        SpringApplication.run(ReplacingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        cloneToPublishingService();
        cloneToStreamService();
    }

    private void cloneToPublishingService() {
        String sourcePath = "/SourceCode/MBC/campaign-service";
        String destPath = "/SourceCode/MBC/publishing-service";
        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

        Map<String, String> renaming = new HashMap<>();
        renaming.put("campaign", "publishing");
        renaming.put("CAMPAIGN", "PUBLISHING");
        renaming.put("Campaign", "Publishing");
        copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
    }

    private void cloneToStreamService() {
        String sourcePath = "/SourceCode/MBC/campaign-service";
        String destPath = "/SourceCode/MBC/stream-service";
        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

        Map<String, String> renaming = new HashMap<>();
        renaming.put("campaign", "stream");
        renaming.put("CAMPAIGN", "STREAM");
        renaming.put("Campaign", "Stream");
        copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
    }
}