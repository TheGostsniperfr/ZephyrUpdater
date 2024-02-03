package com.zephyrupdater.client.updater.VanillaUpdater;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.client.games.gameList.McGameManager;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.OsSpec;
import com.zephyrupdater.common.utils.FileUtils.DownloadableFile;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgoType;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class McManifestParser {
    private static final String VERSIONS_MANIFEST_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    private final JsonObject versionsManifest;
    private final JsonArray versionsArray;
    private final JsonObject versionManifest;
    private List<DownloadableFile> downloadableLibs;
    private List<DownloadableFile> downloadableNatives;
    private DownloadableFile clientFile;
    private List<JsonObject> nativesObjs;
    private final McGameManager mcUpdaterManager;

    public McManifestParser(McGameManager mcUpdaterManager){
        this.mcUpdaterManager = mcUpdaterManager;
        this.downloadableLibs = new ArrayList<>();
        this.downloadableNatives = new ArrayList<>();
        this.nativesObjs = new ArrayList<>();

        versionsManifest = FileUtils.loadJsonFromUrl(VERSIONS_MANIFEST_URL);
        this.versionsArray = this.versionsManifest.get(McMKeys.VERSIONS.getKey()).getAsJsonArray();
        this.versionManifest = getVersionManifest();
        if(this.versionManifest == null){
            System.err.println("Unknown minecraft version: " + this.mcUpdaterManager.getMcVersion());
        }
    }

    public void parseFiles(){
        try{
            System.out.println("Parsing lib files.");
            parseLibs();
            System.out.println("Parsing asset index file.");
            parseAssetIndex();
            System.out.println("Parsing natives.");
            parseNatives();
            System.out.println("Parsing client file.");
            parseClient();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    private JsonObject getVersionManifest() {
        for (JsonElement version : this.versionsArray) {
            JsonObject versionObj = version.getAsJsonObject();
            String versionId = CommonUtil.getValueFromJson(McMKeys.VERSION_ID.getKey(), versionObj, String.class);

            if (versionId.equals(mcUpdaterManager.getMcVersion())) {
                String manifestUrl = CommonUtil.getValueFromJson(
                        McMKeys.VERSION_MANIFEST_URL.getKey(), versionObj, String.class);

                return FileUtils.loadJsonFromUrl(manifestUrl);
            }
        }

        return null;
    }

    private void parseLibs() throws MalformedURLException {
        JsonArray libArray = this.versionManifest.getAsJsonArray(McMKeys.LIBS.getKey());
        for(JsonElement lib : libArray){
            JsonObject libDownloads = lib.getAsJsonObject().getAsJsonObject(McMKeys.LIB_DOWNLOADS.getKey());
            JsonObject nativeObj = libDownloads.getAsJsonObject(McMKeys.LIB_CLASSIFIERS.getKey());
            JsonObject libArtifact = libDownloads.getAsJsonObject(McMKeys.LIB_ARTIFACT.getKey());

            //Check if the lib contain a natives (older manifest)
            if(nativeObj != null) { this.nativesObjs.add(nativeObj); }

            Path filePath = this.mcUpdaterManager.getGameDir()
                    .resolve("libraries/")
                    .resolve(CommonUtil.getValueFromJson(McMKeys.LIB_PATH.getKey(), libArtifact, String.class));
            Long fileSize = CommonUtil.getValueFromJson(McMKeys.LIB_SIZE.getKey(), libArtifact, Long.class);
            String fileHash = CommonUtil.getValueFromJson(McMKeys.LIB_HASH.getKey(), libArtifact, String.class);
            URL fileUrl = new URL(CommonUtil.getValueFromJson(McMKeys.LIB_URL.getKey(), libArtifact, String.class));

            //Check if the lib is a natives (newer manifest)
            String fileName = filePath.getFileName().toString();
            if(fileName.contains("natives") && !isValidNatives(fileName)) { continue; }

            this.downloadableLibs.add(new DownloadableFile(filePath, fileUrl, fileSize, fileHash, HashAlgoType.SHA1));
        }
    }

    private void parseAssetIndex() throws MalformedURLException {
        JsonObject assetIndexObj = this.versionManifest.getAsJsonObject(McMKeys.ASSET_INDEX.getKey());
        URL url = new URL(CommonUtil.getValueFromJson(McMKeys.A_I_URL.getKey(), assetIndexObj, String.class));
        Long size = CommonUtil.getValueFromJson(McMKeys.A_I_SIZE.getKey(), assetIndexObj, Long.class);
        String hash = CommonUtil.getValueFromJson(McMKeys.A_I_HASH.getKey(), assetIndexObj, String.class);
        Path path = Paths.get(this.mcUpdaterManager.getGameDir().resolve("assets/indexes/") + url.getFile());

        this.downloadableLibs.add(new DownloadableFile(path, url, size, hash, HashAlgoType.SHA1));
    }

    private void parseClient() throws MalformedURLException {
        JsonObject downloadsObj = this.versionManifest.getAsJsonObject(McMKeys.DOWNLOADS.getKey());
        JsonObject clientObj = downloadsObj.getAsJsonObject(McMKeys.CLIENT.getKey());
        String urlStr = CommonUtil.getValueFromJson(McMKeys.CLIENT_URL.getKey(), clientObj, String.class);
        URL url = new URL(urlStr);
        Long size = CommonUtil.getValueFromJson(McMKeys.CLIENT_SIZE.getKey(), clientObj, Long.class);
        String hash = CommonUtil.getValueFromJson(McMKeys.CLIENT_HASH.getKey(), clientObj, String.class);
        Path path = this.mcUpdaterManager.getGameDir().resolve(urlStr.substring(urlStr.lastIndexOf("/") + 1));

        this.clientFile = new DownloadableFile(path, url, size, hash, HashAlgoType.SHA1);
    }

    private void parseNatives() throws MalformedURLException {
        for(JsonObject classifiersObj : this.nativesObjs){
            JsonObject nativesObj = null;
            if(mcUpdaterManager.getOsSpec().isOnWindows()){
                nativesObj = classifiersObj.getAsJsonObject(McMKeys.NATIVES_WIN.getKey());
            }else if(mcUpdaterManager.getOsSpec().isOnMAC()){
                nativesObj = classifiersObj.getAsJsonObject(McMKeys.NATIVES_MAC.getKey());
            } else if (mcUpdaterManager.getOsSpec().isOnLinux()) {
                nativesObj = classifiersObj.getAsJsonObject(McMKeys.NATIVES_LINUX.getKey());
            }
            if(nativesObj == null) { return; }

            URL url = new URL(CommonUtil.getValueFromJson(McMKeys.NATIVES_URL.getKey(), nativesObj, String.class));
            Long size = CommonUtil.getValueFromJson(McMKeys.NATIVES_SIZE.getKey(), nativesObj, Long.class);
            String hash = CommonUtil.getValueFromJson(McMKeys.NATIVES_HASH.getKey(), nativesObj, String.class);
            Path path = Paths.get("natives/").resolve(Paths.get(
                            CommonUtil.getValueFromJson(McMKeys.NATIVES_PATH.getKey(), nativesObj, String.class))
                    .getFileName());

            this.downloadableNatives.add(new DownloadableFile(path, url, size, hash, HashAlgoType.SHA1));
        }
    }


    private Boolean isValidNatives(String nativesName){
        if (nativesName.contains("natives-macos") && !mcUpdaterManager.getOsSpec().isOnMAC()
                || nativesName.contains("natives-windows") && !mcUpdaterManager.getOsSpec().isOnWindows()
                || nativesName.contains("natives-linux") && !mcUpdaterManager.getOsSpec().isOnLinux()) {
            return false;
        }

        for(OsSpec.ArchType archType : OsSpec.ArchType.values()){
            if (nativesName.contains(archType.name().toLowerCase())) {
                if (archType != mcUpdaterManager.getOsSpec().getArchType()) {
                    return false;
                }

                break;
            }
        }

        return true;
    }

    public List<DownloadableFile> getDownloadableLibs() {
        return downloadableLibs;
    }

    public List<DownloadableFile> getDownloadableNatives() {
        return downloadableNatives;
    }

    public DownloadableFile getClientFile() {
        return clientFile;
    }
}