package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.junit.jupiter.api.Test;
import org.tnmk.replacing.all.checkdependencies.model.AccessibleDependency;
import org.tnmk.replacing.all.checkdependencies.report.MissingDependenciesReport;

import java.util.List;

public class DependenciesCheckerByDependenciesTreeLoggingTest {
  public static final String REPO_HOST = "https://repo.pointclickcare.com/artifactory/public";

  DependenciesCheckerByDependenciesTreeLog dependenciesCheckerByDependenciesTreeLog =
      new DependenciesCheckerByDependenciesTreeLog(
          new DependenciesTreeLogging(),
          new DependencyLinesFilter(),
          new DependencyParser(),
          new DependencyUrlCreator());
  MissingDependenciesReport missingDependenciesReport = new MissingDependenciesReport();

  @Test
  public void testAnalyzeLinksFile() {
        String projectPath = "C:\\dev\\workspace\\pcc\\secure-conversations-service";
//    String projectPath = "C:\\dev\\workspace\\personal\\replacing\\";
    String excludedGroupdIdPrefix = "com.pointclickcare";
    List<AccessibleDependency> dependencies = dependenciesCheckerByDependenciesTreeLog.missingDependencies(
        REPO_HOST, projectPath, excludedGroupdIdPrefix);
    String missingDependencies = missingDependenciesReport.reportMissingDependencies(dependencies);
    System.out.println(missingDependencies);
  }
}
