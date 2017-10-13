package org.tnmk.replacing.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tnmk.replacing.all.service.CopyingAndReplacingService;
import org.tnmk.replacing.all.service.RenameService;

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

    @Autowired
    private RenameService renameService;

    public static void main(String[] args) {
        SpringApplication.run(ReplacingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        cloneToCampaignService();
//        cloneToPublishingService();
//        cloneToStreamService();
        renameService();
    }

    private void renameService() {
        String sourcePath = "/Users/khoi.tran/Pictures/Photo/Khoi-Tien/Album";
//        String destPath = "/SourceCode/MBC/dam-service";
//        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

        Map<String, String> renaming = new HashMap<>();
        renaming.put("_!", "");
        renaming.put("!", "");
        renaming.put("_.", ".");
        renameService.rename(sourcePath, renaming);
    }

    private void cloneToCampaignService() {
        String sourcePath = "/SourceCode/MBC/content-presentation-service";
        String destPath = "/SourceCode/MBC/dam-service";
        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

        Map<String, String> renaming = new HashMap<>();
        //Plural
        renaming.put("content-presentations", "dams");
        renaming.put("contentpresentations", "dams");
        renaming.put("contentPresentations", "dams");
        renaming.put("ContentPresentations", "Dams");
        renaming.put("CONTENT_PRESENTATIONS", "DAMS");

        //Singular
        renaming.put("content-presentation", "dam");
        renaming.put("contentpresentation", "dam");
        renaming.put("contentPresentation", "dam");
        renaming.put("ContentPresentation", "Dam");
        renaming.put("CONTENT_PRESENTATION", "DAM");
        copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
    }
//
//    private void cloneToPublishingService() {
//        String sourcePath = "/SourceCode/MBC/content-presentation-service";
//        String destPath = "/SourceCode/MBC/publishing-service";
//        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;
//
//        Map<String, String> renaming = new HashMap<>();
//        renaming.put("content-presentation", "publishing");
//        renaming.put("contentpresentation", "publishing");
//        renaming.put("contentPresentation", "publishing");
//        renaming.put("ContentPresentation", "Publishing");
//        renaming.put("CONTENT_PRESENTATION", "PUBLISHING");
//        copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
//    }
//
//    private void cloneToStreamService() {
//        String sourcePath = "/SourceCode/MBC/content-presentation-service";
//        String destPath = "/SourceCode/MBC/stream-service";
//        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;
//
//        Map<String, String> renaming = new HashMap<>();
//        renaming.put("content-presentation", "stream");
//        renaming.put("contentpresentation", "stream");
//        renaming.put("contentPresentation", "stream");
//        renaming.put("ContentPresentation", "Stream");
//        renaming.put("CONTENT_PRESENTATION", "STREAM");
//        copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
//    }
}