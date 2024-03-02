package com.github.voxxin;

import com.github.voxxin.api.config.option.BooleanConfigOption;
import com.github.voxxin.api.config.option.premade.CycleConfigOption;
import com.github.voxxin.api.config.option.FloatConfigOption;
import com.github.voxxin.api.config.option.enums.OptionTypes;
import com.github.voxxin.api.config.option.premade.SliderConfigOption;
import com.github.voxxin.api.config.option.StringConfigOption;

public interface Option {
    OptionTypes type();
    BooleanConfigOption getAsBoolean();
    FloatConfigOption getAsFloat();
    StringConfigOption getAsString();
    CycleConfigOption getAsCycle();
    SliderConfigOption getAsSlider();
}
