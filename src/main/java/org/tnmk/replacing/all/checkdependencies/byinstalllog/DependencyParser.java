package org.tnmk.replacing.all.checkdependencies.byinstalllog;

import org.tnmk.replacing.all.checkdependencies.model.Dependency;
import org.tnmk.replacing.all.checkdependencies.model.DependencyOnRepo;
import org.tnmk.replacing.all.util.FileUtils;

public class DependencyParser {
  public static DependencyOnRepo parseDependency(String repositoryHost, String urlLink) {
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
      if (i < parts.length - 4) {
        groupSB.append(".");
      }
    }
    String group = groupSB.toString();
    String packageType = parsePackageType(fileName);
    Dependency dependency = new Dependency(group, artifactId, version, packageType);
    DependencyOnRepo dependencyOnRepo = new DependencyOnRepo(dependency, urlLink);
    //    dependency.setLink(urlLink);
    //    dependency.setFileName(fileName);
    return dependencyOnRepo;
  }

  private static String parsePackageType(String fileName) {
    return FileUtils.getFileExtension(fileName);
  }
}
