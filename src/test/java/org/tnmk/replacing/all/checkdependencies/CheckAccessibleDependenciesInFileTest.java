package org.tnmk.replacing.all.checkdependencies;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 1) Remove your {USER_HOME}/.m2/repository folder (or just `./org` and `./com` folders inside repository)
 * 2) Build your project with pubic repository (default maven repository) so that it can download all dependencies by this following command,
 * and the download links will be stored into the mvn_logs.txt file:
 * ```
 * mvn clean install -Papi -l mvn_logs.txt
 * ```
 * 3) Run this test to analyze that mvn_logs.txt file, it will automatically print the report which dependencies are missing.
 */
public class CheckAccessibleDependenciesInFileTest {
  public static final String REPO_HOST = "https://repo.pointclickcare.com/artifactory/public";

  @Test
  public void testAnalyzeLinksFile() {
    List<Dependency> dependencies = CheckAccessibleDependenciesFromFile.missingDependencies(REPO_HOST,
        "C:\\dev\\workspace\\pcc\\secure-conversations-service\\sc-api"
            + "\\mvn_logs.txt");
    String missingDependencies = dependencies.stream()
        .map(
            dependency -> dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + dependency.getVersion()
            //            +"\n"+dependency.getLink()
        )
        .distinct()
        .sorted()
        .collect(Collectors.joining("\n"));
    System.out.println(missingDependencies);
  }
}
