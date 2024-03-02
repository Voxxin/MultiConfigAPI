package com.github.voxxin;

import com.github.voxxin.api.config.ConfigManager;
import com.github.voxxin.api.config.option.BooleanConfigOption;
import com.github.voxxin.api.config.option.ConfigOption;
import com.github.voxxin.api.config.option.OptionTypes;
import com.github.voxxin.api.config.option.SliderConfigOption;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;


@Environment(EnvType.CLIENT)
public class MultiConfigAPI implements ClientModInitializer {
    private static final String MODID = "multiconfigapi";
    private static final String MODNAME = "Multi-Config-API";
    public static final Logger LOGGER = LogManager.getLogger(MODNAME);
    public static final String MODVERSION = Objects.requireNonNull(FabricLoader.getInstance().getModContainer(MODID).orElse(null)).getMetadata().getVersion().getFriendlyString();


    @Override
    public void onInitializeClient() {
        ConfigManager manager = new ConfigManager(MODID);
        BooleanConfigOption toggleCape = new BooleanConfigOption("multiconfigapi.general.boolean", false);
        SliderConfigOption soundSlider = new SliderConfigOption("multiconfigapi.general.slider", 0, 2, 1);

        /**
         * People add their options, then they have to load the config.
         * addOption();
         * runOptions(); (This will either, create, or load & update the options.)
         */

        manager.addOption(toggleCape);
        manager.addOption(soundSlider);
        manager.runOptions();

        ConfigOption configOption = new ConfigOption.Builder().addBoolean(toggleCape).build();
        configOption.getOptions().forEach((translation, option) -> {
            if (option.type() == OptionTypes.BOOLEAN) {
                BooleanConfigOption bOption = (BooleanConfigOption)option;
                System.out.println(option);
            }
        });

        /*
            ConfigManager manager = new ConfigManager(String MODID);
            ||
            ConfigManager manager = new ConfigManager(String MODID, File configDirectory);

            ConfigManager.SubFolder folder = new ConfigManager.SubFolder(String name);

            folder.addOption()
        */
    }
}
