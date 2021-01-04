package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.replacing.all.checkdependencies.helper.CheckAccessibleLink;
import org.tnmk.replacing.all.checkdependencies.model.AccessibleDependency;
import org.tnmk.replacing.all.checkdependencies.model.Dependency;
import org.tnmk.replacing.all.checkdependencies.model.DependencyOnRepo;
import org.tnmk.replacing.all.util.IOUtils;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

public class DependenciesCheckerByDependenciesTreeLog {
  private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
    logger.info("Exporting dependencies tree to a log file ...");
    File dependenciesTreeLogFile = dependenciesTreeLogging.reportDependenciesTree(projectAbsolutePath);

    logger.info("Extracting dependencies tree in log file '{}' ...", dependenciesTreeLogFile.getAbsolutePath());
    List<String> lines = IOUtils.loadTextLinesInSystemFile(dependenciesTreeLogFile.getAbsolutePath());
    List<String> dependenciesInfo = dependencyLinesFilter.extractLinesWithDependencies(lines);
    List<Dependency> dependencies = dependenciesInfo.parallelStream()
        .map(line -> dependencyParser.parse(line))
        .collect(Collectors.toList());

    logger.info("Found {} dependencies. Checking dependencies exist in repo '{}' ...", dependencies.size(), repoHost);
    List<AccessibleDependency> accessibleDependencies = dependencies.parallelStream()
        .map(dependency -> dependencyUrlCreator.toDependencyWithUrl(repoHost, dependency))
        .map(dependencyOnRepo -> checkAccessibleDependency(dependencyOnRepo))
        .collect(Collectors.toList());

    logger.info("Checking process completed! Filtering missing dependencies ...", repoHost);
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
