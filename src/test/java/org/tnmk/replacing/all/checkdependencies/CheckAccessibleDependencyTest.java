package org.tnmk.replacing.all.checkdependencies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.tnmk.replacing.all.util.ListUtils;
import org.tnmk.replacing.all.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CheckAccessibleDependencyTest {
  public static final String REPO_HOST = "https://repo.pointclickcare.com/artifactory/public";

  @Test
  public void testAnalyzeOneDependencyLink() {
    Dependency dependency = CheckAccessibleDependency.isAccessibleDependency(
        REPO_HOST,
        "https://repo.pointclickcare.com/artifactory/public/org/flywaydb/flyway-core/7.1.1/flyway-core-7.1.1.pom");
    System.out.println(dependency);
    Assertions.assertEquals("org.flywaydb", dependency.getGroupId());
    Assertions.assertEquals("flyway-core", dependency.getArtifactId());
    Assertions.assertEquals("7.1.1", dependency.getVersion());
  }
}
