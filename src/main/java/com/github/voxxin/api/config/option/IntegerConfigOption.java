package com.github.voxxin.api.config.option;

import org.jetbrains.annotations.NotNull;

public class IntegerConfigOption extends AbstractOption {
    private int optionValue;

    public IntegerConfigOption(@NotNull String translatableKey, int defaultValue) {
        super(translatableKey);
        this.optionValue = defaultValue;
    }

    public void setValue(int value) {
        this.optionValue = value;
    }

    @Override
    public int getValueAsInt() {
        return this.optionValue;
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.INTEGER;
    }
}
