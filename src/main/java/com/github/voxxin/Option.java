package com.github.voxxin;

import com.github.voxxin.api.config.option.*;
import com.github.voxxin.api.config.option.premade.CycleConfigOption;
import com.github.voxxin.api.config.option.enums.OptionTypes;
import com.github.voxxin.api.config.option.premade.SliderConfigOption;

public interface Option {
    OptionTypes type();
    BooleanConfigOption getAsBoolean();
    FloatConfigOption getAsFloat();
    StringConfigOption getAsString();
    <T> CycleConfigOption<T> getAsCycle();
    SliderConfigOption getAsSlider();
    ConfigOption getAsObject();
    ArrayConfigOption getAsArray();
}
