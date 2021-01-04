package org.tnmk.replacing.all.checkdependencies.helper;

import org.tnmk.replacing.all.exception.UnexpectedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandHelper {
  public static void executeCommand(String command, String projectAbsolutePath) {
    Runtime rt = Runtime.getRuntime();
    File projectFolder = new File(projectAbsolutePath);
    try {
      Process pr = rt.exec(command, null, projectFolder);
      startThreadToShowProcessOutput(pr);
      pr.waitFor(); // Wait for the process to finish
    } catch (IOException | InterruptedException e) {
      throw new UnexpectedException(String.format("Cannot execute '%s': %s", command, e.getMessage()), e);
    }
  }

  private static void startThreadToShowProcessOutput(Process pr) {
    new Thread(() -> {
      BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line = null;

      try {
        while ((line = input.readLine()) != null)
          System.out.println(line);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }
}
