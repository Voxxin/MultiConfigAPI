package com.github.voxxin.api.config.option;

import org.jetbrains.annotations.NotNull;

public class IntegerConfigOption extends AbstractOption {
    private int optionValue;

    public IntegerConfigOption(@NotNull String translatableKey, int value) {
        super(translatableKey);
    }

    public void setValue(int value) {
        this.optionValue = value;
    }

    public int getValue() {
        return this.optionValue;
    }
}
