package org.tnmk.replacing.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tnmk.replacing.all.cloneproject.CloneProjectService;
import org.tnmk.replacing.all.renaming.AddingLineService;
import org.tnmk.replacing.all.renaming.CopyingAndReplacingService;
import org.tnmk.replacing.all.renaming.RenameService;
import org.tnmk.replacing.all.renaming.ReplacingService;
import org.tnmk.replacing.all.scoutdata.ScoutDataProcessingService;
import org.tnmk.replacing.all.unzip.UnzipService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author khoi.tran on 7/4/17.
 */
@SpringBootApplication
public class ReplacingApplication implements CommandLineRunner {
  public static final List<String> PATTERN_EXCLUDING_JAVA_PROJECT = Arrays.asList(
      ".*[\\/]\\.git",
      ".*[\\/]\\.idea",
      ".*[\\/]\\.gradle",
      ".*\\.class");

  @Autowired
  private ScoutDataProcessingService scoutDataProcessingService;
  @Autowired
  private CopyingAndReplacingService copyingAndReplacingService;

  @Autowired
  private CloneProjectService cloneProjectService;

  @Autowired
  private AddingLineService addingLineService;

  @Autowired
  private RenameService renameService;

  @Autowired
  private ReplacingService replacingService;

  @Autowired
  private UnzipService unzipService;

  public static void main(String[] args) {
    SpringApplication.run(ReplacingApplication.class, args);
  }

  @Override
  public void run(String... args) throws RuntimeException {
    cloneProject();
    //        renameFileFolderAndContentInsideAFolder();
  }

  /**
   * Note: It only rename the exactly phases (with different upper/lower cases situations).
   * For example:
   * java-service-template will be renamed to the-new-project
   * JavaServiceTemplate to TheNewProject
   * ...
   * View more at {@link org.tnmk.replacing.all.common.multiformname.MultiFormRenameHelper#createMultiFormRenameMap(String, String)} to see all supporting word forms.
   *
   * However, the phases java-service-template-xxx will not be renamed to the-new-project-xxx.
   * If you want some additional renaming phases, use renameFileFolderAndContentInsideAFolder() on the new cloned project.
   */
  private void cloneProject() {
    String sourcePath = "C:\\dev\\workspace\\personal\\practice-nodejs-basic\\pro-04-express-api\\pro-04d-async";
    cloneProjectService.cloneAndRenameInSameParentFolder(sourcePath, "documentation");
  }

  private void renameFileFolderAndContentInsideAFolder() {
    String sourcePath = "D:\\SourceCode\\practice-spring-grpc\\pro-09-stream-download-zip-files";
    this.replacingService.replace(sourcePath, "stream-download-file", "stream-download-zip-files");
    //		this.replacingService.replace(sourcePath, "download-file-server","stream-download-file-server");

    //this.replacingService.replace(sourcePath, "sample-grpc-tls-client","sample-grpc-tls-server");
  }

  /**
   * Analysing scout data in game CM01/02
   */
  private void analyseScoutData() {
    String sourcePath = "D:\\Programming\\SourceCode\\Skeletons\\practice-spring-aws\\pro02-customize-download-s3-by-aws-java-sdk";
    String destPath = "D:\\Programming\\SourceCode\\Skeletons\\practice-spring-aws\\pro03-customize-upload-s3-by-aws-java-sdk";
    List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

    Map<String, String> renaming = new HashMap<>();
    renaming.put(", ", " ");
    renaming.put(";", ",");
    renaming.put(" % ", "%, ");
    renaming.put("Scout Rating", "Scout,Rating");

    this.copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
    this.addingLineService.addingLine(destPath, 0, "Improve rate,2");

    this.scoutDataProcessingService.processCsvToXlsx(destPath);
  }
}