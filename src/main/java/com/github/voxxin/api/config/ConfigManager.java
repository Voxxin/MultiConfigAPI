package com.github.voxxin.api.config;

import com.github.voxxin.api.config.option.AbstractOption;
import com.github.voxxin.api.config.option.ArrayConfigOption;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File configDirectory;
    private String configName = "config";

    private final ArrayList<ConfigOption> options = new ArrayList<>();

    /**
     * @param configDirectory where the config file will be stored.
     * @param configName can be empty/null, defaults to 'config.json'.
     */
    public ConfigManager(@NotNull File configDirectory, @Nullable String configName) {
        this.configDirectory = configDirectory;
        if (configName != null && !configName.isEmpty()) this.configName = configName;
    }

    public ConfigManager(@NotNull File configDirectory) {
        this(configDirectory, null);
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
     * @throws RuntimeException The options file must save correctly.
     */
    public void saveOptions() {
        JsonObject buildConfig = new JsonObject();

        for (ConfigOption copyOfOption : (ArrayList<ConfigOption>) this.options.clone()) {
            JsonArray buildingCategory = new JsonArray();
            JsonObject jsonOption = new JsonObject();

            buildingCategory.add(this.saveOptions(copyOfOption, jsonOption));
            buildConfig.add(copyOfOption.getTranslationKey(), buildingCategory);
        }

        String modConfigJson = GSON.toJson(buildConfig);
        File modConfigFile = new File(this.configDirectory + "/" + this.configName + ".json");

        try {
            FileWriter writer = new FileWriter(modConfigFile);
            writer.write(modConfigJson);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonObject saveOptions(ConfigOption copyOfOption, JsonObject jsonOption) {
        for (AbstractOption option : copyOfOption.getOptions()) {
            switch (option.type()) {
                case ARRAY -> {
                    jsonOption.add(option.getTranslationKey(), GSON.toJsonTree(option.getAsArray().getElements()).getAsJsonArray());
                }
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
                JsonObject configFile = GSON.fromJson(reader, JsonObject.class);
                configFile.entrySet().forEach(entry -> {
                    JsonArray category = entry.getValue().getAsJsonArray();
                    category.forEach(optionElement -> this.readOptions(options, optionElement));
                });
            } catch (FileNotFoundException ignored) {
            }
        }
    }

    private void readOptions(ArrayList<ConfigOption> options, JsonElement optionElement) {
        options.forEach(configOption -> {
            configOption.getOptions().forEach(option -> {
                JsonObject optionObject = optionElement.getAsJsonObject();
                JsonElement element = optionObject.get(option.getTranslationKey());
                if (element != null) this.processOption(option, element);
            });
        });
    }

    private void processOption(AbstractOption option, JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            switch (option.type()) {
                case BOOLEAN -> option.getAsBoolean().setValue(primitive.getAsBoolean());
                case STRING -> option.getAsString().setValue(primitive.getAsString());
                case FLOAT -> option.getAsFloat().setValue(primitive.getAsFloat());
            }
        } else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            if (option.type() == OptionTypes.CYCLE && object.has("selected") && object.get("selected").isJsonPrimitive() && object.get("selected").getAsJsonPrimitive().isNumber()) {
                this.readCycle(object, option.getAsCycle());
            } else if (option.type() == OptionTypes.SLIDER && object.has("min") && object.has("max")) {
                this.readSlider(object, option.getAsSlider());
            } else if (option.type() == OptionTypes.OBJECT) {
                this.readOptions(new ArrayList<>(Collections.singletonList(option.getAsObject())), element);
            } else {
                System.out.println(object);
            }
        } else if (element.isJsonArray()) {
            this.readArray(element.getAsJsonArray(), option.getAsArray());
        }
    }

    private void readArray(JsonArray object, ArrayConfigOption baseArray) {
        for (JsonElement elementItem : object) {

            Object item = null;
            JsonPrimitive primitive = elementItem.getAsJsonPrimitive();

            if (primitive.isString()) item = primitive.getAsString();
            else if (primitive.isNumber()) item = primitive.getAsFloat();
            else if (primitive.isBoolean()) item = primitive.getAsBoolean();

            if (item != null && !baseArray.contains(item)) baseArray.addElement(item);
        }
    }

    private void readCycle(JsonObject object, CycleConfigOption cycle) {
        JsonArray items = object.getAsJsonArray("items");
        int selectedIndex = object.get("selected").getAsInt();

        for (JsonElement elementItem : items) {
            Object item = null;
            JsonPrimitive primitive = elementItem.getAsJsonPrimitive();

            if (primitive.isString()) item = primitive.getAsString();
            else if (primitive.isNumber()) item = primitive.getAsFloat();
            else if (primitive.isBoolean()) item = primitive.getAsBoolean();

            if (item != null && !cycle.contains(item)) cycle.addElement(item);
        }

        cycle.setCurrentIndex(selectedIndex);
    }

    private void readSlider(JsonObject object, SliderConfigOption slider) {
        slider.setMinValue(object.get("min").getAsFloat());
        slider.setMaxValue(object.get("max").getAsFloat());
        slider.setValue(object.get("value").getAsFloat());
    }
}
