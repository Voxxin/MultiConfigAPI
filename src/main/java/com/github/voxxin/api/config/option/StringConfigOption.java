package com.github.voxxin.api.config.option;

import org.jetbrains.annotations.NotNull;

public class StringConfigOption extends AbstractOption {
    private String optionValue = "";

    public StringConfigOption(@NotNull String translatableKey, String value) {
        super(translatableKey);
    }

    public void setValue(String value) {
        this.optionValue = value;
    }

    public String getValue() {
        return this.optionValue;
    }
}
