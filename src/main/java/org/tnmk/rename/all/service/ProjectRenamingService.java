package org.tnmk.rename.all.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 7/5/17.
 */
@Service
public class ProjectRenamingService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectRenamingService.class);

    @Autowired
    private RenamingService renamingService;

    public void renamingProject() {
        String projectRootPath = "/SourceCode/MBC/stream-service";
        Map<String, String> renaming = new HashMap<>();
        renaming.put("campaign", "stream");
        renaming.put("CAMPAIGN", "STREAM");
        renaming.put("Campaign", "Stream");

        renamingService.rename(projectRootPath, renaming);
    }
}
