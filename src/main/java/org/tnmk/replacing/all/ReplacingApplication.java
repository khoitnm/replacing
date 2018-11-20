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
	public void run(String... args) throws Exception {
//		cloneFolder();
//		unzipService.unzipRecursive("D:\\OldFree\\HeroesIII\\Mods\\Objects\\Mods\\ItsJustHoMM3CreatureDefs");
//        analyseScoutData();
//		cloneToPublishingService();
//        cloneToStreamService();
        replaceFolder();
//        analyseScoutData();
	}

	private void replaceFolder() {
		String sourcePath = "/home/kevintran/SourceCode/Personal/Skeletons/practice-spring-grpc-oauth";
		this.replacingService.replace(sourcePath, "sample-grpc-client","sample-client");
		this.replacingService.replace(sourcePath, "sample-grpc-authorization-server","sample-authorization-server");
		this.replacingService.replace(sourcePath, "sample-grpc-resource-server","sample-resource-server");
        this.replacingService.replace(sourcePath, "sample-proto","sample-resource-server-proto");
    }

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

	private void cloneFolder(){
//		String sourcePath = "/home/khoitran/SourceCode/Skeletons/practice-spring-jpa/pro01-simple-entity";
		String sourcePath = "/home/kevintran/SourceCode/Personal/Skeletons/practice-spring-oauth-grpc";
		cloneProjectService.simpleCloneToTheSameFolder(sourcePath,"practice-spring-oauth-grpc","practice-spring-grpc-oauth");

	}

	//
	@Deprecated
	private void cloneToPublishingService() {
		String sourcePath = "C:\\SourceCode\\Practice\\spring-security-oauth-master\\spring-security-oauth-fullbackend";
		String destPath = "C:\\SourceCode\\Practice\\spring-security-oauth-master\\spring-security-oauth-fullserver";
		List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;

		Map<String, String> renaming = new HashMap<>();

		//Plural
		renaming.put("spring-security-oauth-fullbackend", "spring-security-oauth-fullserver");
//		renaming.put("contentpresentations", "dams");
//		renaming.put("contentPresentations", "dams");
//		renaming.put("ContentPresentations", "Dams");
//		renaming.put("CONTENT_PRESENTATIONS", "DAMS");
//
//		//Singular
//		renaming.put("content-presentation", "dam");
//		renaming.put("contentpresentation", "dam");
//		renaming.put("contentPresentation", "dam");
//		renaming.put("ContentPresentation", "Dam");
//		renaming.put("CONTENT_PRESENTATION", "DAM");

		this.copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
	}
//
//    private void cloneToStreamService() {
//        String sourcePath = "/SourceCode/MBC/content-presentation-renaming";
//        String destPath = "/SourceCode/MBC/stream-renaming";
//        List<String> excludingPatterns = PATTERN_EXCLUDING_JAVA_PROJECT;
//
//        Map<String, String> renaming = new HashMap<>();
//        renaming.put("content-presentation", "stream");
//        renaming.put("contentpresentation", "stream");
//        renaming.put("contentPresentation", "stream");
//        renaming.put("ContentPresentation", "Stream");
//        renaming.put("CONTENT_PRESENTATION", "STREAM");
//        copyingAndReplacingService.copyingAndReplacing(sourcePath, destPath, excludingPatterns, renaming);
//    }
}