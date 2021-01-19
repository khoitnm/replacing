package org.tnmk.replacing.all.cloneproject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.replacing.all.common.BaseSpringTest;
import org.tnmk.replacing.all.common.TestType;

@Disabled
@Tag(TestType.APPLICATION_TRIGGER)
public class CloneProjectApplication extends BaseSpringTest {

  @Autowired
  CloneProjectService cloneProjectService;

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
  @Test
  public void test_cloneProject() {
    String sourcePath = "C:\\dev\\workspace\\personal\\replacing";
    cloneProjectService.cloneAndRenameInSameParentFolder(sourcePath, "dependencies-accessibility");
  }
}
