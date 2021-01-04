package org.tnmk.replacing.all.checkdependencies.report;

import org.apache.commons.collections4.CollectionUtils;
import org.tnmk.replacing.all.checkdependencies.model.AccessibleDependency;
import org.tnmk.replacing.all.checkdependencies.model.Dependency;
import org.tnmk.replacing.all.checkdependencies.model.DependencyOnRepo;

import java.util.List;
import java.util.stream.Collectors;

public class MissingDependenciesReport {
  public String reportMissingDependencies(List<AccessibleDependency> missingDependencies) {
    if (CollectionUtils.isEmpty(missingDependencies)) {
      return "There's no missing dependencies. All good!";
    }
    String missingDependenciesString = missingDependencies.stream()
        .map(dependency -> reportDependency(dependency))
        .distinct()
        .sorted()
        .collect(Collectors.joining("\n\n"));
    return missingDependenciesString;
  }

  private String reportDependency(AccessibleDependency accessibleDependency) {
    DependencyOnRepo dependencyOnRepo = accessibleDependency.getDependencyOnRepo();
    Dependency dependency = dependencyOnRepo.getDependency();
    String message = String.format("https://snyk.io/vuln/search?q=%s&type=any \n", dependency.getGroupId())
        + String.format("https://nvd.nist.gov/vuln/search/results?form_type=Basic&results_type=overview&query=%s&search_type=all \n",
        extractArtifactWords(dependency))
        + dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + dependency.getVersion();
    return message;
  }

  private String extractArtifactWords(Dependency dependency) {
    String[] words = dependency.getArtifactId().split("\\-");
    return String.join("%20", words);
  }
}
