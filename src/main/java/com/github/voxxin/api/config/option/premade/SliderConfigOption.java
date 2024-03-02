package com.github.voxxin.api.config.option.premade;

import com.github.voxxin.api.config.option.AbstractOption;
import com.github.voxxin.api.config.option.ConfigOption;
import com.github.voxxin.api.config.option.FloatConfigOption;
import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class SliderConfigOption extends AbstractOption {
    private FloatConfigOption minValue;
    private FloatConfigOption maxValue;
    private FloatConfigOption currentValue;

    public SliderConfigOption(@NotNull String translatableKey, float minValue, float maxValue, float defaultValue) {
        super(translatableKey);

        this.currentValue = new FloatConfigOption("value", defaultValue);
        this.minValue = new FloatConfigOption("min", minValue);
        this.maxValue = new FloatConfigOption("max", maxValue);
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.SLIDER;
    }

    public void setValue(float value) {
        this.currentValue.setValue(value);
    }

    public float getValue() {
        return this.currentValue.getValue();
    }

    public FloatConfigOption get() {
        return this.currentValue;
    }

    public void setMinValue(float value) {
        this.minValue.setValue(value);
    }

    public float getMinValue() {
        return this.minValue.getValue();
    }
    public FloatConfigOption getMin() {
        return this.minValue;
    }

    public void setMaxValue(float value) {
        this.maxValue.setValue(value);
    }

    public float getMaxValue() {
        return this.maxValue.getValue();
    }
    public FloatConfigOption getMax() {
        return this.maxValue;
    }

}
