package com.github.voxxin.api.config.option;

import com.github.voxxin.api.config.option.premade.CycleConfigOption;
import com.github.voxxin.api.config.option.premade.SliderConfigOption;

import java.util.ArrayList;

public class ConfigOption {
    private final String translationKey;
    private final ArrayList<AbstractOption> options;

    private ConfigOption(String translationKey, ArrayList<AbstractOption> options) {
        this.translationKey = translationKey;
        this.options = options;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public ArrayList<AbstractOption> getOptions() {
        return options;
    }

    public static class Builder {
        private final String translationKey;
        private ArrayList<AbstractOption> options = new ArrayList<>();

        public Builder(String translationKey) {
            this.translationKey = translationKey;
        }

        public Builder addBoolean(BooleanConfigOption bool) {
            this.options.add(bool);
            return this;
        }

        public Builder addString(StringConfigOption string) {
            this.options.add(string);
            return this;
        }

        public Builder addFloat(FloatConfigOption flt) {
            this.options.add(flt);
            return this;
        }

        public Builder addSlider(SliderConfigOption slider) {
            this.options.add(slider);
            return this;
        }

        public Builder addCycle(CycleConfigOption cycle) {
            this.options.add(cycle);
            return this;
        }

        public Builder addConfigOption(String translationKey, ConfigOption option) {
            //this.extendedOptions.put(translationKey, option);
            return this;
        }

        public ConfigOption build() {
            return new ConfigOption(this.translationKey, this.options);
        }
    }
}
