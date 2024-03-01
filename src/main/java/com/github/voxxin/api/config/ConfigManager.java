package com.github.voxxin.api.config;

import com.github.voxxin.MultiConfigAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String modId;

    private final File configDirectory;

    public ConfigManager(String modId, File configDirectory) {
        this.modId = modId;
        this.configDirectory = configDirectory;
        this.readOptions();
        // ConfigManager capesConfig = new ConfigManager(MODID, path_to_capes_config);
    }

    public ConfigManager(String modId) {
        this(modId, FabricLoader.getInstance().getConfigDir().resolve(modId).toFile());
    }

    public File getConfigDirectory() {
        return this.configDirectory;
    }

    private void readOptions() {
        try {
            FileReader reader = new FileReader(this.configDirectory);
            JsonElement element = gson.fromJson(reader, JsonElement.class);
            System.out.println(element);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
