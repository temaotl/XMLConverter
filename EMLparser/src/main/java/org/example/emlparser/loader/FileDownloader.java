package org.example.emlparser.loader;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileDownloader {
    /**
     * Downloads a file from a URL and saves it to a specified path.
     *
     * @param fileURL The URL of the file to download.
     * @param outputFileName The path where the downloaded file will be saved.
     * @throws IOException If an I/O error occurs when reading from the URI or writing to the file.
     * @throws InterruptedException If the operation is interrupted while waiting to process the response.
     */
    public static void downloadFile(String fileURL, String outputFileName) throws IOException, InterruptedException {
        Path outputPath = Paths.get(outputFileName);

        Files.createDirectories(outputPath.getParent());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileURL))
                .build();

        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(outputPath));

        System.out.println("File downloaded to " + response.body());
    }
}
