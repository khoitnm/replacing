package org.tnmk.replacing.all.checkdependencies;

import org.tnmk.replacing.all.util.IOUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CheckAccessibleDependenciesFromMvnLogFile {
  public static final String PREFIX_IN_LINES_WITH_LINK = "[INFO] Downloading: ";

  /**
   * @param repoHost
   * @param filePath the absolute path to the Mvn log file which usually is the result of `mvn install -DkipTests -l ${filePath}`
   * @return
   */
  public static List<CheckedDependency> missingDependencies(String repoHost, String filePath) {
    List<String> allLines = IOUtils.loadTextLinesInSystemFile(filePath);
    List<String> links = filterLinesWithDependencyLink(allLines);
    List<CheckedDependency> dependencies = links.stream().parallel()
        .map(link -> isAccessibleDependency(repoHost, link))
        .collect(Collectors.toList());
    List<CheckedDependency> missingDependencies = dependencies.stream()
        .filter(dependency -> !dependency.isAccessible())
        .collect(Collectors.toList());
    return missingDependencies;
  }

  public static List<String> filterLinesWithDependencyLink(List<String> linesInFile) {
    List<String> filteredLines = linesInFile.stream().parallel()
        .filter(line -> line.startsWith(PREFIX_IN_LINES_WITH_LINK))
        .map(line -> line.replace(PREFIX_IN_LINES_WITH_LINK, ""))
        .collect(Collectors.toList());
    return filteredLines;
  }

  public static CheckedDependency isAccessibleDependency(String repositoryHost, String urlLink) {
    Dependency dependency = DependencyParser.parseDependency(repositoryHost, urlLink);
    boolean isAccessible = CheckAccessibleLink.isAccessibleURL(urlLink);
    return new CheckedDependency(dependency, isAccessible);
  }
}
