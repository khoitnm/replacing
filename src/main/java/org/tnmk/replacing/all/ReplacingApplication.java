package org.tnmk.replacing.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tnmk.replacing.all.excel.ScoutDataProcessingService;
import org.tnmk.replacing.all.service.AddingLineService;
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
    private ScoutDataProcessingService scoutDataProcessingService;
    @Autowired
    private CopyingAndReplacingService copyingAndReplacingService;

    @Autowired
    private AddingLineService addingLineService;
    @Autowired
    private RenameService renameService;

    public static void main(String[] args) {
        SpringApplication.run(ReplacingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        analyseScoutData();
//        cloneToPublishingService();
//        cloneToStreamService();
//        renameService();
        analyseScoutData();
    }

    private void renameService() {
        String sourcePath = "D:\\Photos\\KhoiTien_100_tam_album-20171103T081956Z-001\\KhoiTien_100_tam_album";
//        String destPath = "/SourceCode/MBC/dam-service";
//        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

        Map<String, String> renaming = new HashMap<>();
        renaming.put("KhôiTiên", "KhoiTien");
//        renaming.put("!", "");
//        renaming.put("_.", ".");
        this.renameService.rename(sourcePath, renaming);
    }

    private void analyseScoutData() {
        String sourcePath = "D:\\Program Files (x86)\\Championship Manager 01-02\\search\\All";
        String destPath = "D:\\Program Files (x86)\\Championship Manager 01-02\\search\\All-analysis";
        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

        Map<String, String> renaming = new HashMap<>();
        renaming.put(", ", " ");
        renaming.put(";", ",");
        renaming.put(" % ", "%, ");
        renaming.put("Scout Rating", "Scout,Rating");

        this.copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
        this.addingLineService.addingLine(destPath, 0, "Improve rate,2");

        this.scoutDataProcessingService.processCsvToXlsx(destPath);
    }
//
//    private void cloneToPublishingService() {
//        String sourcePath = "/SourceCode/MBC/content-presentation-service";
//        String destPath = "/SourceCode/MBC/publishing-service";
//        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;
//
//        Map<String, String> renaming = new HashMap<>();

//        //Plural
//        renaming.put("content-presentations", "dams");
//        renaming.put("contentpresentations", "dams");
//        renaming.put("contentPresentations", "dams");
//        renaming.put("ContentPresentations", "Dams");
//        renaming.put("CONTENT_PRESENTATIONS", "DAMS");
//
//        //Singular
//        renaming.put("content-presentation", "dam");
//        renaming.put("contentpresentation", "dam");
//        renaming.put("contentPresentation", "dam");
//        renaming.put("ContentPresentation", "Dam");
//        renaming.put("CONTENT_PRESENTATION", "DAM");

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