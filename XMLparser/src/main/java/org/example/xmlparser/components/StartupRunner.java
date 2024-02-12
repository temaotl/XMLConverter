package org.example.xmlparser.components;

import org.example.xmlparser.loader.FileDownloader;
import org.example.xmlparser.loader.ZipFileExtractor;
import org.example.xmlparser.xmlParser.XmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {



    @Autowired
    XmlParserService xmlParserService;

    @Value("${zip.url}")
    private String zipUrl;

    @Value("${zip.output.directory}")
    private String outputDirectory;

    @Value("${zip.temp.file}")
    private String tempZipFile;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Downloading ZIP file...");
        FileDownloader.downloadFile(zipUrl, tempZipFile);

        System.out.println("Extracting ZIP file...");
      String fileName =  ZipFileExtractor.unzip(tempZipFile, outputDirectory);

        System.out.println("ZIP file was downloaded and extracted successfully.");

        System.out.println("Saving to DB");
        xmlParserService.parseXmlFile(fileName);


    }
}

