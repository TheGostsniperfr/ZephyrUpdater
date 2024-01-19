package com.zephyrupdater.client.Updater.JavaUpdater;

import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.common.OSType;
import com.zephyrupdater.common.FileUtils.ArchiveExtractor;
import com.zephyrupdater.common.ZUFile.ZUFileManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class JavaUpdater {
    private static final Path JAVA_DIR = MainClient.clientFilePath.resolve("java");

    public static void javaUpdate(){
        if (!Files.exists(JAVA_DIR)) {
            try {
                Files.createDirectories(JAVA_DIR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(!JavaUpdater.isJavaInstalled()) {
            JavaUpdater.downloadJava();
            if (!JavaUpdater.isJavaInstalled()) {
                throw new RuntimeException("Failed to install java.");
            }
        }
    }
    private static void downloadJava() {
        try {
            URL javaUrl = new URL(getJavaUrlByOS());
            InputStream inputStream = javaUrl.openStream();
            Path destinationFile = JAVA_DIR.resolve(Paths.get(javaUrl.getPath()).getFileName().toString());

            //TODO Remove if
            if(!Files.exists(destinationFile)) {
                System.out.println("Downloading java archive.");
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("Extraction of java archive");

            if (MainClient.CLIENT_OS == OSType.WINDOWS) {
                ArchiveExtractor.extractZipArchive(destinationFile, JAVA_DIR);
            } else {
                ArchiveExtractor.extractTarGzArchive(destinationFile, JAVA_DIR);
            }

            File extractedJavaFolder = ZUFileManager.findFolderStartingByWith(JAVA_DIR, "jdk");
            System.out.println("Sub folder: " + extractedJavaFolder.toString());
            if(extractedJavaFolder == null){
                System.err.println("Error to find sub folder starting with `jdk`");
                return;
            }

            FileUtils.copyDirectory(extractedJavaFolder, new File(JAVA_DIR.toUri()));
            FileUtils.deleteDirectory(extractedJavaFolder);

            //Files.deleteIfExists(destinationFile); //TODO REMOVE COMMENT
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getJavaUrlByOS(){
        switch (MainClient.CLIENT_OS){
            case WINDOWS: //Windows x64 Compressed Archive
                return "https://download.oracle.com/java/17/archive/jdk-17.0.9_windows-x64_bin.zip";
            case LINUX: //Linux x64 Compressed Archive
                return "https://download.oracle.com/java/17/archive/jdk-17.0.9_linux-x64_bin.tar.gz";
            case MAC: //macOS Arm 64 Compressed Archive
                return "https://download.oracle.com/java/17/archive/jdk-17.0.9_macos-aarch64_bin.tar.gz";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Boolean isJavaInstalled(){
        Path javaBinDir = JAVA_DIR.resolve("bin");
        if(!Files.exists(javaBinDir)){
            return false;
        }
        return System.getProperty("java.version") != null && System.getProperty("java.home") != null;
    }


}
