package com.github.voxxin.api.config.option.premade;

import com.github.voxxin.api.config.option.AbstractOption;
import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class SliderConfigOption extends AbstractOption {
    private float minValue;
    private float maxValue;
    private float currentValue;

    public SliderConfigOption(@NotNull String translatableKey, float minValue, float maxValue, float defaultValue) {
        super(translatableKey);

        this.currentValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void setValue(float value) {
        this.currentValue = value;
    }

    public float getValue() {
        return this.currentValue;
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.SLIDER;
    }

    public void setMinValue(float value) {
        this.minValue = value;
    }

    public float getMinValue() {
        return this.minValue;
    }

    public void setMaxValue(float value) {
        this.maxValue = value;
    }

    public float getMaxValue() {
        return this.maxValue;
    }
}
