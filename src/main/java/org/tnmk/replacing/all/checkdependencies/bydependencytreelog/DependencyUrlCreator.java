package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.tnmk.replacing.all.checkdependencies.model.Dependency;
import org.tnmk.replacing.all.checkdependencies.model.DependencyOnRepo;

public class DependencyUrlCreator {
  /**
   * @param repoHost
   * @param dependency
   * @return host/groupId/artifactId/version/artifactId-version.packageType
   */
  public DependencyOnRepo toDependencyWithUrl(String repoHost, Dependency dependency) {
    String fileName = String.format("%s-%s.%s", dependency.getArtifactId(), dependency.getVersion(), dependency.getPackageType());
    String groupPath = dependency.getGroupId().replace(".", "/");
    String url = String.format("%s/%s/%s/%s/%s", repoHost, groupPath, dependency.getArtifactId(), dependency.getVersion(), fileName);
    return new DependencyOnRepo(dependency, url);
  }
}
