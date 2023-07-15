package org.tnmk.replacing.all.copy_specific_files;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.replacing.all.common.BaseSpringTest;

import java.io.IOException;

@Disabled
class CopySpecificFilesJobApplication extends BaseSpringTest {

    @Autowired
    private CopySpecificFilesJob copySpecificFilesJob;

    @Disabled
    @Test
    void run() throws IOException {
        copySpecificFilesJob.run();
    }
}
