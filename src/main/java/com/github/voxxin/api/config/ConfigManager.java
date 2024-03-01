package com.github.voxxin.api.config;

import com.github.voxxin.MultiConfigAPI;
import com.github.voxxin.api.config.option.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String modId;
    private final File configDirectory;

    private ArrayList<? super AbstractOption> options = new ArrayList<>();

    public ConfigManager(String modId, File configDirectory) {
        this.modId = modId;
        this.configDirectory = configDirectory;
    }

    public ConfigManager(String modId) {
        this(modId, FabricLoader.getInstance().getConfigDir().resolve(modId).toFile());
    }

    public File getConfigDirectory() {
        return this.configDirectory;
    }

    public void runOptions() {
        this.configDirectory.mkdirs();
        File[] configFiles = this.configDirectory.listFiles();

        if (configFiles == null || configFiles.length == 0) {
            try {
                FileWriter writer = new FileWriter(this.configDirectory + "/config.json");
                writer.write("{}");
                writer.close();
                MultiConfigAPI.LOGGER.info(String.format("Created config file '%s'", this.configDirectory));
                this.createOptions();
            } catch (IOException ignored) {
                MultiConfigAPI.LOGGER.error("Could not write config file");
            }
        } else readOptions(configFiles);

    }

    private void createOptions() {

    }

    /**
     * addOption is the base of our config.
     * It sets the values to be initiated, or updated later on.
     */

    public void addOption(AbstractOption option) {
        options.add(option);
    }

    private void readOptions(File[] configFiles) {
        for (File file : configFiles) {
            try {
                FileReader reader = new FileReader(file);
                JsonObject element = gson.fromJson(reader, JsonElement.class).getAsJsonObject();
                JsonObject categories = element.getAsJsonObject("categories");
                for (int i = 0; i < categories.keySet().size(); i++) {
                    String key = categories.keySet().toArray()[i].toString();
                    JsonArray category = categories.get(key).getAsJsonArray();
                    for (JsonElement optionElement : category) {
                        JsonObject optionObject = optionElement.getAsJsonObject();
                        String translationKey = optionObject.get("key").getAsString();
                        for (int indexOfOption = 0; options.size() > indexOfOption; indexOfOption++) {
                            if (((AbstractOption) options.get(indexOfOption)).getTranslationKey().equals(translationKey)) {
                                AbstractOption option = (AbstractOption) options.get(indexOfOption);

                                switch (OptionTypes.valueOf(optionObject.get("type").getAsString().toUpperCase())) {
                                    case BOOLEAN -> ((BooleanConfigOption) option).setValue(optionObject.get("value").getAsBoolean());
                                    case CYCLE -> {
                                        List<JsonElement> valuesJsonArray = optionObject.get("values").getAsJsonArray().asList();
                                        for (int indexOfValue = 0; valuesJsonArray.size() > indexOfValue; indexOfValue++) {
//                                            ((CycleConfigOption)option).addOption(new AbstractOption(valuesJsonArray.get(indexOfValue).getAsString()));
                                            if (valuesJsonArray.get(indexOfValue).getAsString().equals(optionObject.get("value").getAsString())) {
                                                ((CycleConfigOption) option).setIndex(indexOfValue);
                                            }
                                        }
                                    }
                                    case INTEGER -> ((IntegerConfigOption) option).setValue(optionObject.get("value").getAsInt());
                                    case SLIDER -> {
                                        ((SliderConfigOption) option).setValue(optionObject.get("value").getAsFloat());
                                        ((SliderConfigOption) option).setMaxValue(optionObject.get("max").getAsFloat());
                                        ((SliderConfigOption) option).setMinValue(optionObject.get("min").getAsFloat());
                                    }
                                    case STRING -> ((StringConfigOption) option).setValue(optionObject.get("value").getAsString());
                                }

                                options.set(indexOfOption, option);
                                break;
                            }
                        }
                    }
                }
            } catch (FileNotFoundException ingored) {}
        }
    }
}
