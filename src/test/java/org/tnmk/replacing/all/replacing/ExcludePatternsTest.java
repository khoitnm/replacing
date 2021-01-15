package org.tnmk.replacing.all.replacing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.tnmk.replacing.all.ReplacingApplication;
import org.tnmk.replacing.all.common.filefilter.FileFilterHelper;

public class ExcludePatternsTest {
  @ParameterizedTest
  @ValueSource(strings = {
      "\\node_modules",  "\\node_modules\\","\\node_modules/",
      "/node_modules","/node_modules/","/node_modules\\",
      "C:\\folder01\\node_modules", "C:\\folder01\\node_modules\\", "C:/folder01/node_modules", "C:/folder01/node_modules/",
      "C:\\folder01\\.gradle", "C:\\folder01\\.idea","C:\\folder01\\.git","C:\\folder01\\build",
      "C:\\folder01\\target\\abc.class",
  })
  public void shouldExcludePaths(String path) {
//    System.out.println(Pattern.matches(".*build[\\\\\\/]?", "build\\"));
    boolean shouldExclude = FileFilterHelper.matchAnyPattern(path, ReplacingApplication.PATTERN_EXCLUDING_JAVA_PROJECT);
    Assertions.assertTrue(shouldExclude);
  }
}
