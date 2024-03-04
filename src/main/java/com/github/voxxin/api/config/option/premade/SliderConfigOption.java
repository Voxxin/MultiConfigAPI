package com.github.voxxin.api.config.option.premade;

import com.github.voxxin.api.config.option.AbstractOption;
import com.github.voxxin.api.config.option.NumberConfigOption;
import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class SliderConfigOption extends AbstractOption {
    private NumberConfigOption minValue;
    private NumberConfigOption maxValue;
    private NumberConfigOption currentValue;

    public SliderConfigOption(@NotNull String translatableKey, float minValue, float maxValue, float defaultValue) {
        super(translatableKey);

        this.currentValue = new NumberConfigOption("value", defaultValue);
        this.minValue = new NumberConfigOption("min", minValue);
        this.maxValue = new NumberConfigOption("max", maxValue);
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.SLIDER;
    }

    public void setValue(float value) {
        this.currentValue.setValue(value);
    }

    public float getValue() {
        return this.currentValue.getValueAsFloat();
    }

    public NumberConfigOption get() {
        return this.currentValue;
    }

    public void setMinValue(float value) {
        this.minValue.setValue(value);
    }

    public float getMinValue() {
        return this.minValue.getValueAsFloat();
    }
    public NumberConfigOption getMin() {
        return this.minValue;
    }

    public void setMaxValue(float value) {
        this.maxValue.setValue(value);
    }

    public float getMaxValue() {
        return this.maxValue.getValueAsFloat();
    }
    public NumberConfigOption getMax() {
        return this.maxValue;
    }

}
