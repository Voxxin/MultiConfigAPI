package com.github.voxxin.api.config.option;

import net.minecraft.network.chat.Component;

public class BooleanConfigOption extends EmptyOption {
    private Boolean optionValue;

    public BooleanConfigOption(Component component, Boolean defaultValue) {
        super(component);
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
