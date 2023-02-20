package org.tnmk.replacing.all.cloneproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CloneProjectServiceTest {
    CloneProjectService cloneProjectService = new CloneProjectService();


    @ParameterizedTest
    @CsvSource({
        // path         , expected folder, expected parent folder
        "C:/abc         ,abc            ,",
        "C:/abc/def     ,def            ,",
        "/aaa           ,'aaa'             ,",
        "/aaa/bbb       ,'bbb'          ,",
    })
    public void getFolderName_success(String path, String expectedFolder, String expectedParentFolder) {
        String actualFolder = cloneProjectService.getFolderName(path);
        Assertions.assertEquals(expectedFolder, actualFolder);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "/",
        "abc",
        "abc/",
        "/abc/",
        "C:/",
        "C:/something/"
    })
    public void getFolderName_IllegalArgument(String path) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cloneProjectService.getFolderName(path);
        });
    }
}
