package org.tnmk.replacing.all.checkdependencies;

import org.tnmk.replacing.all.util.IOUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CheckAccessibleDependenciesFromFile {
  public static final String PREFIX_IN_LINES_WITH_LINK = "[INFO] Downloading: ";

  public static List<Dependency> missingDependencies(String repoHost, String fileName) {
    List<String> allLines = IOUtils.loadTextLinesInSystemFile(fileName);
    List<String> links = filterLinesWithDependencyLink(allLines);
    List<Dependency> dependencies = links.stream().parallel().map(
        link -> CheckAccessibleDependency.isAccessibleDependency(repoHost, link)
    ).collect(Collectors.toList());
    List<Dependency> missingDependencies = dependencies.stream().filter(dependency -> !dependency.isAccessible()).collect(Collectors.toList());
    return missingDependencies;
  }

  public static List<String> filterLinesWithDependencyLink(List<String> linesInFile) {
    List<String> filteredLines = linesInFile.stream().parallel()
        .filter(line -> line.startsWith(PREFIX_IN_LINES_WITH_LINK))
        .map(line -> line.replace(PREFIX_IN_LINES_WITH_LINK, ""))
        .collect(Collectors.toList());
    return filteredLines;
  }
}
