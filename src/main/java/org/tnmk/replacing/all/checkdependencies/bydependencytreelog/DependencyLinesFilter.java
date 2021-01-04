package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DependencyLinesFilter {
  public static final List<String> DEPENDENCY_PREFIX_PATTERNS = Arrays.asList("+- ", "\\- ");

  /**
   * @param allLines this is the result of `mvn dependency:tree` command line. The content will be like this:
   * <pre>
   * [INFO] Scanning for projects...
   * [INFO]
   * [INFO] ------------------------------------------------------------------------
   * [INFO] Building tnmk.replace.all 1.0.0-SNAPSHOT
   * [INFO] ------------------------------------------------------------------------
   * [INFO]
   * [INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ tnmk.replace.all ---
   * [INFO] org.tnmk.replace:tnmk.replace.all:pom:1.0.0-SNAPSHOT
   * [INFO] +- org.springframework.boot:spring-boot-starter:jar:2.1.8.RELEASE:compile
   * [INFO] |  +- org.springframework.boot:spring-boot:jar:2.1.8.RELEASE:compile
   * [INFO] |  |  \- org.springframework:spring-context:jar:5.1.9.RELEASE:compile
   * [INFO] |  |     +- org.springframework:spring-aop:jar:5.1.9.RELEASE:compile
   * [INFO] |  |     +- org.springframework:spring-beans:jar:5.1.9.RELEASE:compile
   * [INFO] |  |     \- org.springframework:spring-expression:jar:5.1.9.RELEASE:compile
   * [INFO] |  +- org.springframework.boot:spring-boot-autoconfigure:jar:2.1.8.RELEASE:compile
   * </pre>
   * @return
   */
  public List<String> extractLinesWithDependencies(List<String> allLines) {
    return allLines.stream()
        .map(line -> extractDependencyInfo(line))
        .filter(line -> StringUtils.isNotBlank(line))
        .collect(Collectors.toList());
  }

  private static String extractDependencyInfo(String line) {
    for (String dependencyPrefix : DEPENDENCY_PREFIX_PATTERNS) {
      String[] linesParts = line.split(Pattern.quote(dependencyPrefix));
      if (linesParts.length == 2) {
        String extractedDependencyInfo = linesParts[1];
        return extractedDependencyInfo;
      }
    }
    return null;
  }
}
