package org.tnmk.replacing.all.checkdependencies.byinstalllog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tnmk.replacing.all.checkdependencies.model.AccessibleDependency;
import org.tnmk.replacing.all.checkdependencies.model.Dependency;
import org.tnmk.replacing.all.checkdependencies.model.DependencyOnRepo;

public class DependenciesCheckerByInstallLogFile_OneLinkTest {
  public static final String REPO_HOST = "https://repo.pointclickcare.com/artifactory/public";

  @Test
  public void testAnalyzeOneDependencyLink() {
    AccessibleDependency accessibleDependency = DependenciesCheckerByInstallLogFile.isAccessibleDependency(
        REPO_HOST,
        "https://repo.pointclickcare.com/artifactory/public/org/flywaydb/flyway-core/7.1.1/flyway-core-7.1.1.pom");
    System.out.println(accessibleDependency);
    DependencyOnRepo dependencyOnRepo = accessibleDependency.getDependencyOnRepo();
    Dependency dependency = dependencyOnRepo.getDependency();
    Assertions.assertEquals("org.flywaydb", dependency.getGroupId());
    Assertions.assertEquals("flyway-core", dependency.getArtifactId());
    Assertions.assertEquals("7.1.1", dependency.getVersion());
  }
}
