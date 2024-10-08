package com.github.voxxin.api.config.option;

import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class NumberConfigOption extends AbstractOption {
    private long optionValue;

    public NumberConfigOption(@NotNull String translatableKey, long defaultValue) {
        super(translatableKey);
        this.optionValue = defaultValue;
    }

    public void setValue(long value) {
        this.optionValue = value;
    }

    public int getValueAsInteger() {
        return (int)this.optionValue;
    }

    public float getValueAsFloat() {
        return (float)this.optionValue;
    }

    public long getValueAsLong() {
        return this.optionValue;
    }

    public double getValueAsDouble() {
        return (double)this.optionValue;
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.NUMBER;
    }
}
