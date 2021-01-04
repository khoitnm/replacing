package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.tnmk.replacing.all.checkdependencies.helper.CommandHelper;
import org.tnmk.replacing.all.util.FileUtils;

import java.io.File;

public class DependenciesTreeLogging {
  public static final String OUTPUT_LOG_FILENAME = "dependency_tree.log";

  public File reportDependenciesTree(String projectAbsolutePath) {
    String normalizedProjectPath = FileUtils.normalizePath(projectAbsolutePath);
    String cmd = "cmd /c mvn dependency:tree -l " + OUTPUT_LOG_FILENAME;
    CommandHelper.executeCommand(cmd, normalizedProjectPath);
    return new File(normalizedProjectPath + "/" + OUTPUT_LOG_FILENAME);
  }
}
