package com.zephyrupdater.common.FileUtils;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Path;

public class ArchiveExtractor {

    public static void extractZipArchive(Path source, Path destination) {
        try (InputStream inputStream = new FileInputStream(source.toFile());
             ArchiveInputStream archiveInputStream = new ZipArchiveInputStream(inputStream)) {

            extractFiles(archiveInputStream, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractTarGzArchive(Path source, Path destination) {
        try (InputStream inputStream = new FileInputStream(source.toFile());
             ArchiveInputStream archiveInputStream = new TarArchiveInputStream(new GzipCompressorInputStream(inputStream))) {

            extractFiles(archiveInputStream, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extractFiles(ArchiveInputStream ais, Path destination) throws IOException {
        ArchiveEntry entry;
        while ((entry = ais.getNextEntry()) != null) {
            File destFile = destination.resolve(entry.getName()).toFile();

            if (entry.isDirectory()) {
                destFile.mkdirs();
            } else {
                try (OutputStream outputStream = new FileOutputStream(destFile)) {
                    IOUtils.copy(ais, outputStream);
                }
            }
        }
    }
}
