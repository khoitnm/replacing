package org.tnmk.replacing.all.checkdependencies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckAccessibleDependencyTest {
  public static final String REPO_HOST = "https://repo.pointclickcare.com/artifactory/public";

  @Test
  public void testAnalyzeOneDependencyLink() {
    CheckedDependency checkedDependency = CheckAccessibleDependenciesFromMvnLogFile.isAccessibleDependency(
        REPO_HOST,
        "https://repo.pointclickcare.com/artifactory/public/org/flywaydb/flyway-core/7.1.1/flyway-core-7.1.1.pom");
    System.out.println(checkedDependency);
    Dependency dependency = checkedDependency.getDependency();
    Assertions.assertEquals("org.flywaydb", dependency.getGroupId());
    Assertions.assertEquals("flyway-core", dependency.getArtifactId());
    Assertions.assertEquals("7.1.1", dependency.getVersion());
  }
}
