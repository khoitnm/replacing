package org.tnmk.replacing.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tnmk.replacing.all.service.FolderReplacingService;

/**
 * @author khoi.tran on 7/4/17.
 */
@SpringBootApplication
public class ReplacingApplication implements CommandLineRunner {

    @Autowired
    private FolderReplacingService projectNamingService;

    public static void main(String[] args) {
        SpringApplication.run(ReplacingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        projectNamingService.renamingProject();
    }
}