package com.github.voxxin.api.config.option;

import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class FloatConfigOption extends AbstractOption {
    private float optionValue;

    public FloatConfigOption(@NotNull String translatableKey, float defaultValue) {
        super(translatableKey);
        this.optionValue = defaultValue;
    }

    public void setValue(float value) {
        this.optionValue = value;
    }

    public float getValue() {
        return this.optionValue;
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.FLOAT;
    }
}
