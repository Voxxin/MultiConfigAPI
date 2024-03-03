package com.github.voxxin;

import com.github.voxxin.api.config.ConfigManager;
import com.github.voxxin.api.config.option.ArrayConfigOption;
import com.github.voxxin.api.config.option.BooleanConfigOption;
import com.github.voxxin.api.config.option.ConfigOption;
import com.github.voxxin.api.config.option.premade.CycleConfigOption;
import com.github.voxxin.api.config.option.premade.SliderConfigOption;
import com.github.voxxin.api.config.option.StringConfigOption;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Environment(EnvType.CLIENT)
public class MultiConfigAPI implements ClientModInitializer {
    private static final String MODID = "multiconfigapi";
    private static final String MODNAME = "Multi-Config-API";
    public static final Logger LOGGER = LogManager.getLogger(MODNAME);

    @Override
    public void onInitializeClient() {
        ConfigManager manager = new ConfigManager(MODID);
        BooleanConfigOption toggleCape = new BooleanConfigOption("multiconfigapi.general.boolean", false);
        StringConfigOption whateverString = new StringConfigOption("multiconfigapi.general.string", "this should not appear");
        SliderConfigOption soundSlider = new SliderConfigOption("multiconfigapi.general.slider", 0, 2, 1);
        CycleConfigOption<String> cycle = new CycleConfigOption<>("multiconfig.general.cycle");
        cycle.addElement("Test 000");
        cycle.addElement("Test 123");
        cycle.addElement("Test 456");
        cycle.addElement("Test 789");

        /*
         * People add their options, then they have to load the config.
         * addOption();
         * runOptions(); (This will either, create, or load & update the options.)
         */

        ConfigOption subOption = new ConfigOption.Builder("another.option")
                .addBoolean(new BooleanConfigOption("test", true))
                .build();

        ConfigOption configOption = new ConfigOption.Builder("awesome.category")
                .addBoolean(toggleCape)
                .addString(whateverString)
                .addSlider(soundSlider)
                .addCycle(cycle)
                .addConfigOption(subOption)
                .build();

        manager.addOption(configOption);
        manager.runOptions();

    }
}
