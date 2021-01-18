package org.tnmk.replacing.all.checkdependencies.bydependencytreelog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
public class DependenciesTreeLoggingTest {
  DependenciesTreeLogging dependenciesTreeLogging = new DependenciesTreeLogging();

  @Disabled // Only run with manual trigger
  @Test
  public void logDependenciesTree() {
    File dependenciesTreeLogFile = dependenciesTreeLogging.reportDependenciesTree("C:\\dev\\workspace\\personal\\replacing\\");
    Assertions.assertTrue(dependenciesTreeLogFile.exists());
  }
}
