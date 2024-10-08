package com.github.voxxin.api.config.option.premade;

import com.github.voxxin.api.config.option.AbstractOption;
import com.github.voxxin.api.config.option.NumberConfigOption;
import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class SliderConfigOption extends AbstractOption {
    private final NumberConfigOption minValue;
    private final NumberConfigOption maxValue;
    private final NumberConfigOption currentValue;

    public SliderConfigOption(@NotNull String translatableKey, long minValue, long maxValue, long defaultValue) {
        super(translatableKey);

        this.currentValue = new NumberConfigOption("value", defaultValue);
        this.minValue = new NumberConfigOption("min", minValue);
        this.maxValue = new NumberConfigOption("max", maxValue);
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.SLIDER;
    }

    public void setValue(long value) {
        this.currentValue.setValue(value);
    }

    public long getValue() {
        return this.currentValue.getValueAsLong();
    }

    public NumberConfigOption get() {
        return this.currentValue;
    }

    public void setMinValue(long value) {
        this.minValue.setValue(value);
    }

    public long getMinValue() {
        return this.minValue.getValueAsLong();
    }
    public NumberConfigOption getMin() {
        return this.minValue;
    }

    public void setMaxValue(long value) {
        this.maxValue.setValue(value);
    }

    public long getMaxValue() {
        return this.maxValue.getValueAsLong();
    }
    public NumberConfigOption getMax() {
        return this.maxValue;
    }

}
