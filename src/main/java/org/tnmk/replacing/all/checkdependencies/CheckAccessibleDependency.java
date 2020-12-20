package org.tnmk.replacing.all.checkdependencies;

import org.springframework.stereotype.Component;

public class CheckAccessibleDependency
{
  public static Dependency isAccessibleDependency(String repositoryHost, String urlLink)
  {
    Dependency dependency = analyzeDependency(repositoryHost, urlLink);
    dependency.setAccessible(CheckAccessibleLink.isAccessibleURL(urlLink));
    return dependency;
  }

  public static Dependency analyzeDependency(String repositoryHost, String urlLink)
  {
    String dependencyRelativePath = urlLink.replaceFirst(repositoryHost, "");
    if (dependencyRelativePath.startsWith("/")) {
      dependencyRelativePath = dependencyRelativePath.replaceFirst("/", "");
    }
    String[] parts = dependencyRelativePath.split("/");
    String fileName = parts[parts.length - 1];
    String version = parts[parts.length - 2];
    String artifactId = parts[parts.length - 3];
    StringBuilder groupSB = new StringBuilder();
    for (int i = 0; i < parts.length - 3; i++) {
      groupSB.append(parts[i]);
      if (i < parts.length - 4)
      {
        groupSB.append(".");
      }
    }
    String group = groupSB.toString();
    Dependency dependency = new Dependency(group, artifactId, version);
    dependency.setLink(urlLink);
    dependency.setFileName(fileName);
    return dependency;
  }
}
