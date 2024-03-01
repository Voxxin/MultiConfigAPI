package com.github.voxxin.api.config.option;

import net.minecraft.network.chat.Component;

public class BooleanConfigOption extends AbstractOption {
    private Boolean optionValue;

    public BooleanConfigOption(String translatableKey, Boolean defaultValue) {
        super(translatableKey);
        this.optionValue = defaultValue;
    }

    public void toggleValue() {
        this.optionValue = !optionValue;
    }

    public void setValue(Boolean value) {
        this.optionValue = value;
    }

    public boolean getValue() {
        return this.optionValue;
    }
}
