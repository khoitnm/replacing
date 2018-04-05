package org.tnmk.replacing.all.util;

import com.github.junrar.extract.ExtractArchive;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;
import org.tnmk.replacing.all.exception.FileIOException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ZipUtils {
    private ZipUtils() {
        //Utils
    }

    private static final List<String> compressedFileExtensions = Arrays.asList("zip", "rar", "7z", "tar", "gzip");

    public static boolean isCompressedFileName(String fileName) {
        String fileExtension = FileUtils.getFileExtension(fileName);
        return compressedFileExtensions.contains(fileExtension.toLowerCase());
    }

    /**
     * @param path relative or absolute path, for a file or a directory.
     */
    public static ZipInputStream loadZipInputStream(String path) {
        InputStream inputStream = IOUtils.loadInputStreamSystemFile(path);
        return new ZipInputStream(inputStream);
    }

    /**
     * http://www.baeldung.com/java-compress-and-uncompress
     * Note: We don't use library zip4j because it doesn't support *.rar or *.7z.
     * Note2: After recheck, this method also could not support *.rar or *.7z
     *
     * @param sourceZipFilePath relative or absolute path of the zip file.
     * @return return the targetFolder. If cannot extract, return null.
     */
    public static String unzip(String sourceZipFilePath, String targetFolderPath, boolean createWrapperFolder) {
        String finalTargetFolderPath = targetFolderPath;
        if (!targetFolderPath.endsWith("/") && !targetFolderPath.endsWith("\\")) {
            finalTargetFolderPath += "/";
        }
        String zipFileBaseName = FileUtils.getFileBaseName(sourceZipFilePath);
        if (createWrapperFolder) {
            finalTargetFolderPath += zipFileBaseName;
        }
        File folder = IOUtils.createFolderIfNecessary(finalTargetFolderPath);
        if (folder == null) {
            finalTargetFolderPath += "_" + DateTimeUtils.formatLocalDateTimeForFilePath();
            IOUtils.createFolderIfNecessary(finalTargetFolderPath);
        }

        String extension = FileUtils.getFileExtension(sourceZipFilePath);
        if ("zip".equalsIgnoreCase(extension)) {
            extractZipFile(sourceZipFilePath, finalTargetFolderPath);
        } else if ("rar".equalsIgnoreCase(extension)) {
            extractRarFile(sourceZipFilePath, finalTargetFolderPath);
        } else if ("7z".equalsIgnoreCase(extension)) {
            extract7zFile(sourceZipFilePath, finalTargetFolderPath);
        } else {
            //TODO
            return null;
        }
        return finalTargetFolderPath;
    }

    private static void extractZipFile(String sourceZipFilePath, String finalTargetFolderPath) {
        extractFile(sourceZipFilePath, finalTargetFolderPath, ArchiveFormat.ZIP, null);
    }
    private static void extract7zFile(String sourceZipFilePath, String finalTargetFolderPath) {
        extractFile(sourceZipFilePath, finalTargetFolderPath, ArchiveFormat.SEVEN_Z, null);
    }


    private static void extractFile(String sourceZipFilePath, String finalTargetFolderPath, ArchiveFormat archiveFormat, CompressionType compression) {
        Archiver archiver;
        if (compression != null){
            archiver = ArchiverFactory.createArchiver(archiveFormat, compression);
        }else{
            archiver = ArchiverFactory.createArchiver(archiveFormat);
        }
        try {
            archiver.extract(new File(sourceZipFilePath), new File(finalTargetFolderPath));
        } catch (IOException e) {
            throw new FileIOException(String.format("Error when unzip file '%s' to '%s'", sourceZipFilePath, finalTargetFolderPath), e);
        }
    }
    @Deprecated
    public static void extract7zFileWithCommonCompress(String sourceZipFilePath, String finalTargetFolderPath) {
        SevenZFile sevenZFile = null;
        try {
            sevenZFile = new SevenZFile(new File(sourceZipFilePath));
            SevenZArchiveEntry entry;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                File entryDestination = new File(finalTargetFolderPath, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    File curFile = new File(new File(finalTargetFolderPath), entry.getName());
                    IOUtils.createFolderIfNecessary(finalTargetFolderPath);
                    FileOutputStream out = new FileOutputStream(curFile);
                    byte[] content = new byte[(int) entry.getSize()];
                    sevenZFile.read(content, 0, content.length);
                    out.write(content);
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new FileIOException(String.format("Error when unzip file '%s' to '%s'", sourceZipFilePath, finalTargetFolderPath), e);
        }
    }

    private static String extractRarFile(String sourceZipFilePath, String finalTargetFolderPath) {
        ExtractArchive extractArchive = new ExtractArchive();
        extractArchive.extractArchive(new File(sourceZipFilePath), new File(finalTargetFolderPath));
        return finalTargetFolderPath;
    }

    @Deprecated
    public static String extractZipFileOldWay(String sourceZipFilePath, String finalTargetFolderPath) {
        try {
            byte[] buffer = new byte[1024];

            ZipInputStream zipInputStream = loadZipInputStream(sourceZipFilePath);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry == null) {
                return null;
            }
            while (zipEntry != null) {
                File entryDestination = new File(finalTargetFolderPath, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    FileOutputStream fileOutputStream = new FileOutputStream(entryDestination);
                    int len;
                    while ((len = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.close();
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            return finalTargetFolderPath;
        } catch (IOException ex) {
            throw new FileIOException(String.format("Error when unzip file '%s' to '%s'", sourceZipFilePath, finalTargetFolderPath), ex);
        }
    }
}
