package com.github.voxxin.api.config.option;

import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class NumberConfigOption extends AbstractOption {
    private float optionValue;

    public NumberConfigOption(@NotNull String translatableKey, float defaultValue) {
        super(translatableKey);
        this.optionValue = defaultValue;
    }

    public void setValue(float value) {
        this.optionValue = value;
    }

    public int getValueAsInteger() {
        return (int)this.optionValue;
    }

    public float getValueAsFloat() {
        return this.optionValue;
    }

    public double getValueAsDouble() {
        return this.optionValue;
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.NUMBER;
    }
}
