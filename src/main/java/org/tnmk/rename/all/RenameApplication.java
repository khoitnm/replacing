package org.tnmk.rename.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tnmk.rename.all.service.ProjectRenamingService;

/**
 * @author khoi.tran on 7/4/17.
 */
@SpringBootApplication
public class RenameApplication implements CommandLineRunner {

    @Autowired
    private ProjectRenamingService projectNamingService;

    public static void main(String[] args) {
        SpringApplication.run(RenameApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        projectNamingService.renamingProject();
    }
}