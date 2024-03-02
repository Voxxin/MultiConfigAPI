package com.github.voxxin.api.config.option;

import java.util.HashMap;
import java.util.Map;

public class ConfigOption {
    private final Map<String, AbstractOption> options;

    private ConfigOption(Map<String, AbstractOption> options) {
        this.options = options;
        /*
            ConfigOption configOption = new ConfigOption.Builder().addBoolean("", false).build();
            configOption.getOptions().forEach((option) -> {
                option.setValue(true);
            });
        */
    }

    public Map<String, AbstractOption> getOptions() {
        return options;
    }

    public static class Builder {
        private Map<String, AbstractOption> options;
        private Map<String, ConfigOption> extendedOptions;

        public Builder() {
            this.options = new HashMap<>();
        }

        public Builder addBoolean(BooleanConfigOption bool) {
            this.options.put(bool.getTranslationKey(), bool);
            return this;
        }

        public Builder addString(StringConfigOption string) {
            this.options.put(string.getTranslationKey(), string);
            return this;
        }

        public Builder addFloat(IntegerConfigOption flt) {
            this.options.put(flt.getTranslationKey(), flt);
            return this;
        }

        public Builder addConfigOption(String translationKey, ConfigOption option) {
            this.extendedOptions.put(translationKey, option);
            return this;
        }

        public ConfigOption build() {
            return new ConfigOption(options);
        }
    }
}
