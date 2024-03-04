package com.github.voxxin.api.config.option;

import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class BooleanConfigOption extends AbstractOption {
    private boolean optionValue;

    public BooleanConfigOption(@NotNull String translatableKey, boolean defaultValue) {
        super(translatableKey);
        this.optionValue = defaultValue;
    }

    public void toggleValue() {
        this.optionValue = !optionValue;
    }

    public void setValue(boolean value) {
        this.optionValue = value;
    }

    public boolean getValue() {
        return this.optionValue;
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.BOOLEAN;
    }
}
