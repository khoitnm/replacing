package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.tnmk.replacing.all.checkdependencies.model.Dependency;

import java.util.regex.Pattern;

public class DependencyParser {
  /**
   * @param line org.springframework.boot:spring-boot-starter:jar:2.1.8.RELEASE:compile
   * @return
   */
  public Dependency parse(String line) {
    String[] parts = line.split(Pattern.quote(":"));
    String groupId = parts[0];
    String artifactId = parts[1];
    String packageType = parts[2];
    String version = parts[3];
    return new Dependency(groupId, artifactId, version, packageType);
  }
}
