package org.tnmk.rename.all.util;

import org.tnmk.rename.all.exception.UnexpectedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author khoi.tran on 7/5/17.
 */
public final class IOUtils {

    private IOUtils() {
        //Hide the public constructor.
    }

    /**
     * @param path a relative path in classpath. E.g. "images/email/logo.png"
     *             From Class, the path is relative to the package of the class unless you include a leading slash.
     *             So if you don't want to use the current package, include a slash like this: "/SomeTextFile.txt"
     * @return
     */
    public static String loadTextFileInClassPath(final String path) {
        try {
            final InputStream inputStream = validateExistInputStreamFromClassPath(path);
            return org.apache.commons.io.IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (final IOException e) {
            final String msg = String.format("Cannot load String from '%s'", path);
            throw new UnexpectedException(msg, e);
        }
    }

    /**
     * @param path a relative path in classpath. E.g. "images/email/logo.png"
     *             From Class, the path is relative to the package of the class unless you include a leading slash.
     *             So if you don't want to use the current package, include a slash like this: "/SomeTextFile.txt"
     * @return
     */
    public static byte[] loadBinaryFileInClassPath(final String path) {
        try {
            final InputStream inputStream = validateExistInputStreamFromClassPath(path);
            return org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (final IOException e) {
            final String msg = String.format("Cannot load String from '%s'", path);
            throw new UnexpectedException(msg, e);
        }
    }

    private static InputStream validateExistInputStreamFromClassPath(final String path) {
        final InputStream inputStream = loadInputStreamFileInClassPath(path);
        if (inputStream == null) {
            throw new UnexpectedException(String.format("Not found file from '%s'", path));
        }
        return inputStream;
    }

    /**
     * @param path view more in {@link #loadBinaryFileInClassPath(String)}
     * @return
     */
    public static InputStream loadInputStreamFileInClassPath(final String path) {
        return IOUtils.class.getResourceAsStream(path);
    }

    public static void writeTextToFile(String absolutePath, String content) {
        try {
            Files.write(Paths.get(absolutePath), content.getBytes());
        } catch (IOException e) {
            final String msg = String.format("Cannot write data to '%s'", absolutePath);
            throw new UnexpectedException(msg, e);
        }
    }

    public static byte[] readBytesFromSystemFile(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            final String msg = String.format("Cannot read bytes from '%s'", path);
            throw new UnexpectedException(msg, e);
        }
    }

    public static String loadTextFileInSystem(final String path) {
        byte[] bytes = readBytesFromSystemFile(path);
        return new String(bytes);
    }

    public static boolean isBinaryFileWithProbeContentType(File file) {
        String type;
        try {
            type = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            throw new UnexpectedException("Cannot get ProbeContentType of file " + file);
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
}
