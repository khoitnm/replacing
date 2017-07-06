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

    @Autowired
    private CopyingAndReplacingService copyingAndReplacingService;

    public static void main(String[] args) {
        SpringApplication.run(ReplacingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String sourcePath = "/SourceCode/MBC/campaign-service";
        String destPath = "/SourceCode/MBC/publishing-service";
        List<String> excludingPatterns = Arrays.asList(".*[\\/]\\.git", ".*[\\/]\\.idea", ".*[\\/]\\.gradle");

        Map<String, String> renaming = new HashMap<>();
        renaming.put("campaign", "publishing");
        renaming.put("CAMPAIGN", "PUBLISHING");
        renaming.put("Campaign", "Publishing");
        copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
    }
}