package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tnmk.replacing.all.checkdependencies.model.Dependency;
import org.tnmk.replacing.all.checkdependencies.model.DependencyOnRepo;

public class DependencyUrlCreatorTest {
  public static final String REPO_HOST = "https://repo.pointclickcare.com/artifactory/public";

  DependencyUrlCreator dependencyUrlCreator = new DependencyUrlCreator();

  @Test
  public void testAnalyzeLinksFile() {
    Dependency dependency = new Dependency(
        "org.springframework.boot",
        "spring-boot-starter-logging",
        "2.4.1",
        "jar");
    DependencyOnRepo dependencyOnRepo = dependencyUrlCreator.toDependencyWithUrl(REPO_HOST, dependency);
    Assertions.assertEquals(
        REPO_HOST + "/org/springframework/boot/spring-boot-starter-logging/2.4.1/spring-boot-starter-logging-2.4.1.jar",
        dependencyOnRepo.getLink());
  }
}
