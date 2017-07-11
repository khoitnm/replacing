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
        cloneToCampaignService();
//        cloneToPublishingService();
//        cloneToStreamService();
    }

    private void cloneToCampaignService() {
        String sourcePath = "/SourceCode/MBC/content-presentation-service";
        String destPath = "/SourceCode/MBC/campaign-service";
        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

        Map<String, String> renaming = new HashMap<>();
        //Plural
        renaming.put("content-presentations", "campaigns");
        renaming.put("contentpresentations", "campaigns");
        renaming.put("contentPresentations", "campaigns");
        renaming.put("ContentPresentations", "Campaigns");
        renaming.put("CONTENT_PRESENTATIONS", "CAMPAIGNS");

        //Singular
        renaming.put("content-presentation", "campaign");
        renaming.put("contentpresentation", "campaign");
        renaming.put("contentPresentation", "campaign");
        renaming.put("ContentPresentation", "Campaign");
        renaming.put("CONTENT_PRESENTATION", "CAMPAIGN");
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