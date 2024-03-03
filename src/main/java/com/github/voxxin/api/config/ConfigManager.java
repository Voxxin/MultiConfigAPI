package com.github.voxxin.api.config;

import com.github.voxxin.MultiConfigAPI;
import com.github.voxxin.api.config.option.AbstractOption;
import com.github.voxxin.api.config.option.ConfigOption;
import com.github.voxxin.api.config.option.enums.OptionTypes;
import com.github.voxxin.api.config.option.premade.CycleConfigOption;
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
        return this.configName;
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
        this.options.add(option);
    }

    /**
     * Saves the options file
     * @throws RuntimeException if a file can't be written, it will crash
     */
    public void saveOptions() {
        JsonObject buildConfig = new JsonObject();

        for (ConfigOption copyOfOption : (ArrayList<ConfigOption>)this.options.clone()) {
            JsonArray buildingCategory = new JsonArray();
            JsonObject jsonOption = new JsonObject();

            buildingCategory.add(this.saveOptions(copyOfOption, jsonOption));
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

    private JsonObject saveOptions(ConfigOption copyOfOption, JsonObject jsonOption) {
        for (AbstractOption option : copyOfOption.getOptions()) {
            switch (option.type()) {
                case BOOLEAN -> jsonOption.addProperty(option.getTranslationKey(), option.getAsBoolean().getValue());
                case CYCLE -> {
                    JsonObject cycleObject = new JsonObject();

                    cycleObject.addProperty("selected", option.getAsCycle().getCurrentIndex());
                    cycleObject.remove("items");
                    cycleObject.add("items", GSON.toJsonTree(option.getAsCycle().getElements()));

                    jsonOption.add(option.getTranslationKey(), cycleObject);
                }
                case FLOAT -> jsonOption.addProperty(option.getTranslationKey(), option.getAsFloat().getValue());
                case SLIDER -> {
                    JsonObject sliderObj = new JsonObject();

                    sliderObj.addProperty(option.getAsSlider().getMax().getTranslationKey(), option.getAsSlider().getMaxValue());
                    sliderObj.addProperty(option.getAsSlider().getMin().getTranslationKey(), option.getAsSlider().getMinValue());
                    sliderObj.addProperty(option.getAsSlider().get().getTranslationKey(), option.getAsSlider().getValue());

                    jsonOption.add(option.getTranslationKey(), sliderObj);
                }
                case STRING -> jsonOption.addProperty(option.getTranslationKey(), option.getAsString().getValue());
                case OBJECT -> {
                    JsonObject jsonObject = new JsonObject();
                    jsonOption.add(option.getTranslationKey(), this.saveOptions(option.getAsObject(), jsonObject));
                }
            }
        }

        return jsonOption;
    }

    private void readOptions(File[] configFiles) {
        for (File file : configFiles) {
            try {
                FileReader reader = new FileReader(file);
                JsonObject configFile = GSON.fromJson(reader, JsonElement.class).getAsJsonObject();
                for (int i = 0; i < configFile.keySet().size(); i++) {
                    String key = configFile.keySet().toArray()[i].toString();
                    JsonArray category = configFile.get(key).getAsJsonArray();
                    for (JsonElement optionElement : category) {
                        this.readOptions(this.options, optionElement);
                    }
                }
            } catch (FileNotFoundException ingored) {}
        }
    }

    private void readOptions(ArrayList<ConfigOption> options, JsonElement optionElement) {
        int optionIndex = 0;
        for (ConfigOption configOption : options) {
            for (AbstractOption option : configOption.getOptions()) {
                JsonObject optionObject = optionElement.getAsJsonObject();
                for (String optionTranslatableKey : optionObject.keySet()) {
                    JsonElement element = optionObject.get(optionTranslatableKey);
                    if (element.isJsonPrimitive()) {
                        JsonPrimitive primitive = element.getAsJsonPrimitive();
                        switch (option.type()) {
                            case BOOLEAN -> {
                                MultiConfigAPI.LOGGER.info(option.getTranslationKey());
                                option.getAsBoolean().setValue(primitive.getAsBoolean());
                            }
                            case STRING -> option.getAsString().setValue(primitive.getAsString());
                            case FLOAT -> option.getAsFloat().setValue(primitive.getAsFloat());
                        }
                    } else if (element.isJsonObject()) {
                        JsonObject object = element.getAsJsonObject();
                        if (object.has("selected") && object.get("selected").isJsonPrimitive() && object.get("selected").getAsJsonPrimitive().isNumber() && option.type() == OptionTypes.CYCLE) {
                            this.readCycle(object, option.getAsCycle());
                        } else if (object.has("min") && object.has("max") && option.type() == OptionTypes.SLIDER) {
                            this.readSlider(object, option.getAsSlider());
                        } else if (option.type() == OptionTypes.OBJECT) {
                            ArrayList<ConfigOption> newOptions = new ArrayList<>();
                            newOptions.add(option.getAsObject());

                            this.readOptions(newOptions, element);
                        }
                    }
                }
            }

            this.options.set(optionIndex, configOption);
            optionIndex++;
        }
    }
    
    private void readCycle(JsonObject object, CycleConfigOption cycle) {
        JsonArray items = object.get("items").getAsJsonArray();
        JsonPrimitive primitive = items.get(0).getAsJsonPrimitive();
        Object item = null;

        for (JsonElement elementItem : items) {
            if (primitive.isString()) item = elementItem.getAsString();
            if (primitive.isNumber()) item = elementItem.getAsFloat();
            if (primitive.isBoolean()) item = elementItem.getAsBoolean();

            if (item != null && !cycle.contains(item)) cycle.addElement(item);
        }

        cycle.setCurrentIndex(object.get("selected").getAsInt());
    }

    private void readSlider(JsonObject object, SliderConfigOption slider) {
        slider.setMinValue(object.get("min").getAsFloat());
        slider.setMaxValue(object.get("max").getAsFloat());
        slider.setValue(object.get("value").getAsFloat());
    }
}
