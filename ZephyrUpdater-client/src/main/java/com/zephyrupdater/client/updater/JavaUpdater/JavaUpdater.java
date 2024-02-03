package com.zephyrupdater.client.updater.JavaUpdater;

import com.zephyrupdater.client.games.GameManagerCore;
import com.zephyrupdater.client.updater.UpdateProgressSteps;
import com.zephyrupdater.common.ZUFile.ZUFileManager;
import com.zephyrupdater.common.utils.FileUtils.ArchiveExtractor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class JavaUpdater {
    private static final String JAVA_BASE_URL = "https://download.oracle.com/java/{gVersion}/archive/jdk-{version}_{osType}";
    private final String javaVersion;
    private final Path javaDir;
    private final GameManagerCore game;


    public JavaUpdater(GameManagerCore game, String javaVersion){
        this.game = game;
        this.javaVersion = javaVersion;
        this.javaDir = game.getGameDir().resolve("java");

        if (!Files.exists(javaDir)) {
            try {
                Files.createDirectories(javaDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(!this.isJavaInstalled()) {
            this.downloadJava();
            if (!this.isJavaInstalled()) {
                throw new RuntimeException("Failed to install java.");
            }
        }
    }

    private void downloadJava() {
        try {
            URL javaUrl = getJavaUrl(javaVersion);
            InputStream inputStream = javaUrl.openStream();
            Path destinationFile = this.javaDir.resolve(Paths.get(javaUrl.getPath()).getFileName().toString());

            //TODO Remove if
            if(!Files.exists(destinationFile)) {
                this.game.setUpdateProgressSteps(UpdateProgressSteps.DL_JAVA);
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            this.game.setUpdateProgressSteps(UpdateProgressSteps.INSTALL_JAVA);
            if (this.game.getOsSpec().isOnWindows()) {
                ArchiveExtractor.extractZipArchive(destinationFile, this.javaDir);
            } else {
                ArchiveExtractor.extractTarGzArchive(destinationFile, this.javaDir);
            }

            File extractedJavaFolder = ZUFileManager.findFolderStartingByWith(this.javaDir, "jdk");
            if(extractedJavaFolder == null){
                System.err.println("Error to find sub folder starting with `jdk`");
                return;
            }

            FileUtils.copyDirectory(extractedJavaFolder, new File(this.javaDir.toUri()));
            FileUtils.deleteDirectory(extractedJavaFolder);

            //Files.deleteIfExists(destinationFile); //TODO REMOVE COMMENT
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean isJavaInstalled(){
        Path javaBinDir = this.javaDir.resolve("bin");
        if(!Files.exists(javaBinDir)){
            return false;
        }
        return System.getProperty("java.version") != null && System.getProperty("java.home") != null;
    }

    private URL getJavaUrl(String javaVersion){
        String gVersion = (javaVersion.split("\\.")[0]);

        try {
            return new URL(JAVA_BASE_URL
                    .replace("{gVersion}", gVersion)
                    .replace("{version}", (javaVersion))
                    .replace("{osType}", getOSUrlComplement())
            );

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getOSUrlComplement(){
        switch (this.game.getOsSpec().getOsType()){
            case WINDOWS:
                return "windows-x64_bin.zip";
            case LINUX:
                return "linux-x64_bin.tar.gz";
            case MAC:
                return "macos-aarch64_bin.tar.gz";
            default:
                throw new RuntimeException("Unsupported OS type: " + this.game.getOsSpec().getOsType().name());
        }
    }
}
