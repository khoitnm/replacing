package org.tnmk.replacing.all.util;

import org.apache.commons.io.FilenameUtils;
import org.tnmk.replacing.all.exception.UnexpectedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * version: 1.0.2
 * 2018/04/04
 * @author khoi.tran
 */
public final class FileUtils {
    private FileUtils() {
        //Utils
    }

    public static String getFileExtension(String filePath) {
        return FilenameUtils.getExtension(filePath);
    }

    /**
     *
     * @param filePath
     * @return file base name (removed extension and directories' path)
     */
    public static String getFileBaseName(String filePath) {
        return FilenameUtils.getBaseName(filePath);
    }
    public static boolean isTextFile(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) { return false; }
            FileInputStream in = new FileInputStream(f);
            int size = in.available();
            if (size > 1000) { size = 1000; }
            byte[] data = new byte[size];
            in.read(data);
            in.close();
            String s = new String(data, "ISO-8859-1");
            String s2 = s.replaceAll(
                "[a-zA-Z0-9ßöäü\\.\\*!\"§\\$\\%&/()=\\?@~'#:,;\\" +
                    "+><\\|\\[\\]\\{\\}\\^°²³\\\\ \\n\\r\\t_\\-`´âêîô" +
                    "ÂÊÔÎáéíóàèìòÁÉÍÓÀÈÌÒ©‰¢£¥€±¿»«¼½¾™ª]", "");
            // will delete all text signs

            double d = (double) (s.length() - s2.length()) / (double) (s.length());
            // percentage of text signs in the text
            return d > 0.95;
        } catch (IOException ex) {
            throw new UnexpectedException("Cannot check file is text or binary " + filePath, ex);
        }
    }

    public static boolean isBinaryFileWithProbeContentType(File file) {
        String type;
        try {
            type = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            throw new UnexpectedException("Cannot get ProbeContentType of file " + file, e);
        }
        if (type == null) {
            //type couldn't be determined, assume binary
            return true;
        } else if (type.startsWith("text")) {
            return false;
        } else {
            //type isn't text
            return true;
        }
    }
}
