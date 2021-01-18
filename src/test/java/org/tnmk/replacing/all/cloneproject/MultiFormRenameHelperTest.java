package org.tnmk.replacing.all.cloneproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tnmk.replacing.all.common.multiformname.MultiFormRenameHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MultiFormRenameHelperTest {
  @Test
  public void shouldExcludePaths() {
    String oldSingularName = "ori-00-simple-Folder";
    String newSinglarName = "target-01-simpleFolder";
    Map<String, String> renaming = MultiFormRenameHelper.createMultiFormRenameMap(oldSingularName, newSinglarName);
    List<String> expectRenamingKeys = Arrays.asList(
        "ori-00-simple-folder",
        "ORI_00_SIMPLE_FOLDER");
    for (String expectRenamingKey : expectRenamingKeys) {
      Assertions.assertTrue(renaming.containsKey(expectRenamingKey), "Key '"+expectRenamingKey + "' doesn't exist as expected");
    }
  }
}
