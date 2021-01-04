package org.tnmk.replacing.all.checkdependencies.byinstalllog;

import org.tnmk.replacing.all.checkdependencies.bydependencytreelog.DependenciesCheckerByDependenciesTreeLog;
import org.tnmk.replacing.all.checkdependencies.helper.CheckAccessibleLink;
import org.tnmk.replacing.all.checkdependencies.model.AccessibleDependency;
import org.tnmk.replacing.all.checkdependencies.model.DependencyOnRepo;
import org.tnmk.replacing.all.util.IOUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use {@link DependenciesCheckerByDependenciesTreeLog} instead.
 */
@Deprecated
public class DependenciesCheckerByInstallLogFile {
  public static final String PREFIX_IN_LINES_WITH_LINK = "[INFO] Downloading: ";

  /**
   * @param repoHost
   * @param filePath the absolute path to the Mvn log file which usually is the result of `mvn install -DkipTests -l ${filePath}`
   * @return
   */
  public static List<AccessibleDependency> missingDependencies(String repoHost, String filePath) {
    List<String> allLines = IOUtils.loadTextLinesInSystemFile(filePath);
    List<String> links = filterLinesWithDependencyLink(allLines);
    List<AccessibleDependency> dependencies = links.stream().parallel()
        .map(link -> isAccessibleDependency(repoHost, link))
        .collect(Collectors.toList());
    List<AccessibleDependency> missingDependencies = dependencies.stream()
        .filter(dependency -> !dependency.isAccessible())
        .collect(Collectors.toList());
    return missingDependencies;
  }

  /**
   * @param linesInFile the lines will be like this:
   * [INFO] Scanning for projects...
   * [INFO] Downloading: https://some.repo.path/org/springframework/boot/spring-boot-dependencies/2.4.1/spring-boot-dependencies-2.4.1.pom
   * [INFO] Downloaded: https://some.repo.path/org/springframework/boot/spring-boot-dependencies/2.4.1/spring-boot-dependencies-2.4.1.pom (106 KB at 183.3 KB/sec)
   * [INFO] Downloading: https://some.repo.path/com/datastax/oss/java-driver-bom/4.9.0/java-driver-bom-4.9.0.pom
   * [INFO] Downloaded: https://some.repo.path/com/datastax/oss/java-driver-bom/4.9.0/java-driver-bom-4.9.0.pom (5 KB at 40.8 KB/sec)
   * [INFO] Downloading: https://some.repo.path/io/dropwizard/metrics/metrics-bom/4.1.16/metrics-bom-4.1.16.pom
   * [INFO] Downloaded: https://some.repo.path/io/dropwizard/metrics/metrics-bom/4.1.16/metrics-bom-4.1.16.pom (6 KB at 57.7 KB/sec)
   *
   * @return
   */
  public static List<String> filterLinesWithDependencyLink(List<String> linesInFile) {
    List<String> filteredLines = linesInFile.stream().parallel()
        .filter(line -> line.startsWith(PREFIX_IN_LINES_WITH_LINK))
        .map(line -> line.replace(PREFIX_IN_LINES_WITH_LINK, ""))
        .collect(Collectors.toList());
    return filteredLines;
  }

  public static AccessibleDependency isAccessibleDependency(String repositoryHost, String urlLink) {
    DependencyOnRepo dependencyOnRepo = DependencyParser.parseDependency(repositoryHost, urlLink);
    boolean isAccessible = CheckAccessibleLink.isAccessibleURL(urlLink);
    return new AccessibleDependency(dependencyOnRepo, isAccessible);
  }
}
