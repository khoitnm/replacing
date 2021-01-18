package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.tnmk.replacing.all.checkdependencies.model.AccessibleDependency;
import org.tnmk.replacing.all.checkdependencies.report.MissingDependenciesReport;
import org.tnmk.replacing.all.common.TestType;

import java.util.List;

@Tag(TestType.APPLICATION_TRIGGER)
public class DependenciesCheckerByDependenciesTreeLoggingApplication {
  public static final String REPO_HOST = "https://repo.pointclickcare.com/artifactory/public";

  DependenciesCheckerByDependenciesTreeLog dependenciesCheckerByDependenciesTreeLog =
      new DependenciesCheckerByDependenciesTreeLog(
          new DependenciesTreeLogging(),
          new DependencyLinesFilter(),
          new DependencyParser(),
          new DependencyUrlCreator());
  MissingDependenciesReport missingDependenciesReport = new MissingDependenciesReport();

  @Disabled // Only run with manual trigger
  @Test
  public void testAnalyzeLinksFile() {
    String projectPath = "C:\\dev\\workspace\\pcc\\secure-conversations-service";
    String excludedGroupdIdPrefix = "com.pointclickcare";
    List<AccessibleDependency> dependencies = dependenciesCheckerByDependenciesTreeLog.missingDependencies(
        REPO_HOST, projectPath, excludedGroupdIdPrefix);
    String missingDependencies = missingDependenciesReport.reportMissingDependencies(dependencies);
    System.out.println(missingDependencies);
  }
}
