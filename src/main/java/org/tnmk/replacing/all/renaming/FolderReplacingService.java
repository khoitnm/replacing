package org.tnmk.replacing.all.renaming;

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
public class FolderReplacingService {
    public static final Logger LOGGER = LoggerFactory.getLogger(FolderReplacingService.class);

    @Autowired
    private ReplacingService replacingService;

    public void renamingProject() {
        String projectRootPath = "/SourceCode/MBC/stream-renaming";
        Map<String, String> renaming = new HashMap<>();
        renaming.put("campaign", "stream");
        renaming.put("CAMPAIGN", "STREAM");
        renaming.put("Campaign", "Stream");

        this.replacingService.rename(projectRootPath, renaming);
    }
}
