package org.tnmk.replacing.all.renameproject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.replacing.all.common.BaseSpringTest;
import org.tnmk.replacing.all.common.TestType;
import org.tnmk.replacing.all.renaming.ReplacingService;

@Disabled
@Tag(TestType.APPLICATION_TRIGGER)
public class RenameProjectApplication extends BaseSpringTest {

  @Autowired
    ReplacingService replacingService;

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
  public void renameFileFolderAndContentInsideAFolder() {
      String sourcePath = "C:\\Projects\\Personal\\personal-blog";

      this.replacingService.replace(sourcePath, "blog-editor-webapp", "blog-admin-webapp");
  }
}
