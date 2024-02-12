package org.example.xmlparser.loader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileExtractor {

    /**
     * Unzips a single file from a ZIP archive.
     *
     * @param zipFilePath the path to the ZIP file.
     * @param destDirPath the destination directory to extract the file to.
     * @return The path to the extracted file. Note: In this implementation, it assumes there's only one entry.
     *         In a real-world scenario, this might return a List<String> of extracted file paths,
     *         and you would need to loop through all entries in the ZIP.
     * @throws IOException If an I/O error occurs.
     */
    public static String unzip(String zipFilePath, String destDirPath) throws IOException {

        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();

            String filePath = destDirPath + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                extractFile(zipIn, filePath);
            } else {

                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            return filePath;
        }
    }

    /**
     * Extracts a file from the ZIP input stream.
     *
     * @param zipIn The ZIP input stream.
     * @param filePath The path to extract the file to.
     * @throws IOException If an I/O error occurs during file extraction.
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        // i use standard buffersize but it can be change
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}

