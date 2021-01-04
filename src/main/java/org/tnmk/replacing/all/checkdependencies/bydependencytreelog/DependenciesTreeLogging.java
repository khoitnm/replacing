package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.replacing.all.checkdependencies.helper.CommandHelper;
import org.tnmk.replacing.all.util.FileUtils;

import java.io.File;
import java.lang.invoke.MethodHandles;

public class DependenciesTreeLogging {

  public static final String OUTPUT_LOG_FILENAME = "dependency_tree.log";

  public File reportDependenciesTree(String projectAbsolutePath) {
    String normalizedProjectPath = FileUtils.normalizePath(projectAbsolutePath);
    String cmd = "cmd /c mvn dependency:tree -l " + OUTPUT_LOG_FILENAME;
    CommandHelper.executeCommand(cmd, normalizedProjectPath);
    return new File(normalizedProjectPath + "/" + OUTPUT_LOG_FILENAME);
  }
}
