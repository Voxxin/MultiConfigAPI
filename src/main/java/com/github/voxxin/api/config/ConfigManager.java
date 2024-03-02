package com.github.voxxin.api.config;

import com.github.voxxin.api.config.option.AbstractOption;
import com.github.voxxin.api.config.option.ConfigOption;
import com.github.voxxin.api.config.option.premade.SliderConfigOption;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String modId;
    private final File configDirectory;
    private String configName = "config";

    private final ArrayList<ConfigOption> options = new ArrayList<>();

    public ConfigManager(@NotNull String modId, @NotNull File configDirectory, @Nullable String configName) {
        this.modId = modId;
        this.configDirectory = configDirectory;
        if (configName != null && !configName.isEmpty()) this.configName = configName;
    }

    public ConfigManager(@NotNull String modId, @Nullable String configName) {
        this(modId, FabricLoader.getInstance().getConfigDir().resolve(modId).toFile(), configName);
    }

    public ConfigManager(@NotNull String modId) {
        this(modId, null);
    }

    public File getConfigDirectory() {
        return this.configDirectory;
    }

    public String getConfigName() {
        return configName;
    }

    public void runOptions() {
        this.configDirectory.mkdirs();
        File[] configFiles = this.configDirectory.listFiles();

        if (configFiles != null && configFiles.length > 0) readOptions(configFiles);

        this.saveOptions();
    }

    /**
     * addOption is the base of our config.
     * It sets the values to be initiated, or updated later on.
     */
    public void addOption(@NotNull ConfigOption option) {
        options.add(option);
    }

    /**
     * Saves the options file
     * @throws RuntimeException if a file can't be written, it will crash
     */
    public void saveOptions() {
        JsonObject buildConfig = new JsonObject();

        for (ConfigOption copyOfOption : (ArrayList<ConfigOption>)options.clone()) {
            JsonArray buildingCategory = new JsonArray();
            JsonObject jsonOption = new JsonObject();

            for (AbstractOption option : copyOfOption.getOptions()) {
                switch (option.type()) {
                    case BOOLEAN -> jsonOption.addProperty(option.getTranslationKey(), option.getAsBoolean().getValue());
                    //case CYCLE -> {
                    //    jsonOption.addProperty("values", option.getAsCycle().getOptions());
                    //}
                    case FLOAT -> jsonOption.addProperty(option.getTranslationKey(), option.getAsFloat().getValue());
                    case SLIDER -> {
                        JsonObject sliderObj = new JsonObject();

                        sliderObj.addProperty(option.getAsSlider().getMax().getTranslationKey(), option.getAsSlider().getMaxValue());
                        sliderObj.addProperty(option.getAsSlider().getMin().getTranslationKey(), option.getAsSlider().getMinValue());
                        sliderObj.addProperty(option.getAsSlider().get().getTranslationKey(), option.getAsSlider().getValue());

                        jsonOption.add(option.getTranslationKey(), sliderObj);
                    }
                    case STRING -> jsonOption.addProperty(option.getTranslationKey(), option.getAsString().getValue());
                }
            }

            buildingCategory.add(jsonOption);
            buildConfig.add(copyOfOption.getTranslationKey(), buildingCategory);
        }

        String modConfigJson = GSON.toJson(buildConfig);
        File modConfigFile = new File(this.configDirectory + "/"+ this.configName + ".json");

        try {
            FileWriter writer = new FileWriter(modConfigFile);
            writer.write(modConfigJson);
            writer.close();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private void readOptions(File[] configFiles) {
        int optionIndex = 0;
        for (File file : configFiles) {
            try {
                FileReader reader = new FileReader(file);
                JsonObject configFile = GSON.fromJson(reader, JsonElement.class).getAsJsonObject();
                for (int i = 0; i < configFile.keySet().size(); i++) {
                    String key = configFile.keySet().toArray()[i].toString();
                    JsonArray category = configFile.get(key).getAsJsonArray();
                    for (JsonElement optionElement : category) {
                        for (ConfigOption configOption : this.options) {
                            for (AbstractOption option : configOption.getOptions()) {
                                JsonObject optionObject = optionElement.getAsJsonObject();
                                for (String optionTranslatableKey : optionObject.keySet()) {
                                    JsonElement element = optionObject.get(optionTranslatableKey);
                                    if (element.isJsonPrimitive()) {
                                        JsonPrimitive primitive = element.getAsJsonPrimitive();
                                        switch (option.type()) {
                                            case BOOLEAN -> option.getAsBoolean().setValue(primitive.getAsBoolean());
                                            case STRING -> option.getAsString().setValue(primitive.getAsString());
                                            case FLOAT -> option.getAsFloat().setValue(primitive.getAsFloat());
                                        }
                                    } else if (element.isJsonObject()) {
//                                        SliderConfigOption sliderOption = option.getAsSlider();
//                                        sliderOption.setValue(optionObject.get("value").getAsFloat());
//                                        sliderOption.setMaxValue(optionObject.get("max").getAsFloat());
//                                        sliderOption.setMinValue(optionObject.get("min").getAsFloat());
                                    } else if (element.isJsonArray()) {
                                        // TODO: 3/1/2024 find out if this works
                                        List<JsonElement> valuesJsonArray = optionObject.get("values").getAsJsonArray().asList();
                                        for (int indexOfValue = 0; valuesJsonArray.size() > indexOfValue; indexOfValue++) {
                                            if (valuesJsonArray.get(indexOfValue).getAsString().equals(optionObject.get("value").getAsString())) {
                                                option.getAsCycle().setIndex(indexOfValue);
                                            }
                                        }
                                    }
                                }
                            }

                            options.set(optionIndex, configOption);
                            optionIndex++;
                        }
                    }
                }
            } catch (FileNotFoundException ingored) {}
        }
    }
}
