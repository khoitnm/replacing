package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.tnmk.replacing.all.checkdependencies.helper.CheckAccessibleLink;
import org.tnmk.replacing.all.checkdependencies.model.AccessibleDependency;
import org.tnmk.replacing.all.checkdependencies.model.DependencyOnRepo;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class DependenciesCheckerByDependenciesTreeLog {
  private final DependenciesTreeLogging dependenciesTreeLogging;
  private final DependencyLinesFilter dependencyLinesFilter;
  private final DependencyParser dependencyParser;
  private final DependencyUrlCreator dependencyUrlCreator;

  public DependenciesCheckerByDependenciesTreeLog(
      DependenciesTreeLogging dependenciesTreeLogging,
      DependencyLinesFilter dependencyLinesFilter, DependencyParser dependencyParser,
      DependencyUrlCreator dependencyUrlCreator) {
    this.dependenciesTreeLogging = dependenciesTreeLogging;
    this.dependencyLinesFilter = dependencyLinesFilter;
    this.dependencyParser = dependencyParser;
    this.dependencyUrlCreator = dependencyUrlCreator;
  }

  public List<AccessibleDependency> missingDependencies(String repoHost, String projectAbsolutePath) {
    File dependenciesTreeLogFile = dependenciesTreeLogging.reportDependenciesTree(projectAbsolutePath);
    List<String> lines = IOUtils.loadTextLinesInSystemFile(dependenciesTreeLogFile.getAbsolutePath());
    List<String> dependenciesInfo = dependencyLinesFilter.extractLinesWithDependencies(lines);
    List<AccessibleDependency> accessibleDependencies = dependenciesInfo.parallelStream()
        .map(line -> dependencyParser.parse(line))
        .map(dependency -> dependencyUrlCreator.toDependencyWithUrl(repoHost, dependency))
        .map(dependencyOnRepo -> checkAccessibleDependency(dependencyOnRepo))
        .collect(Collectors.toList());
    List<AccessibleDependency> missingDependencies = accessibleDependencies.stream()
        .filter(accessibleDependency -> !accessibleDependency.isAccessible())
        .collect(Collectors.toList());
    return missingDependencies;
  }

  private AccessibleDependency checkAccessibleDependency(DependencyOnRepo dependencyOnRepo) {
    boolean isAccessible = CheckAccessibleLink.isAccessibleURL(dependencyOnRepo.getLink());
    return new AccessibleDependency(dependencyOnRepo, isAccessible);
  }
}
