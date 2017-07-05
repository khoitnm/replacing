This project helps us to replace both file/folder name and file content (with file text only, not apply for binary file)
Look up in the ``ProjectRenamingService``

```
        String projectRootPath = "/SourceCode/MBC/campaign-service";
        Map<String, String> renaming = new HashMap<>();
        renaming.put("campaign", "stream");
        renaming.put("CAMPAIGN", "STREAM");
        renaming.put("Campaign", "Stream");
```