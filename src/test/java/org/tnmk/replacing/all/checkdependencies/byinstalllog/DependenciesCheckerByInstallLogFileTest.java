package org.tnmk.replacing.all.checkdependencies.byinstalllog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.tnmk.replacing.all.checkdependencies.model.AccessibleDependency;
import org.tnmk.replacing.all.checkdependencies.report.MissingDependenciesReport;

import java.util.List;

/**
 * 1) Remove your {USER_HOME}/.m2/repository folder (or just `./org` and `./com` folders inside repository)
 * 2) Build your project with pubic repository (default maven repository) so that it can download all dependencies by this following command,
 * and the download links will be stored into the mvn_logs.txt file:
 * ```
 * mvn clean install -DskipTests -Papi -l mvn_logs.txt
 * ```
 * 3) Run this test to analyze that mvn_logs.txt file, it will automatically print the report which dependencies are missing.
 */
public class DependenciesCheckerByInstallLogFileTest {
  public static final String REPO_HOST = "https://repo.pointclickcare.com/artifactory/public";
  private final MissingDependenciesReport missingDependenciesReport = new MissingDependenciesReport();

  @Disabled // Only run with manual trigger
  @Test
  public void testAnalyzeLinksFile() {
    List<AccessibleDependency> dependencies = DependenciesCheckerByInstallLogFile.missingDependencies(REPO_HOST,
        "C:\\dev\\workspace\\pcc\\secure-conversations-service\\sc-api"
            + "\\mvn_logs.txt");
    String missingDependencies = missingDependenciesReport.reportMissingDependencies(dependencies);
    System.out.println(missingDependencies);
  }
}
