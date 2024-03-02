package com.github.voxxin;

import com.github.voxxin.api.config.option.BooleanConfigOption;
import com.github.voxxin.api.config.option.IntegerConfigOption;
import com.github.voxxin.api.config.option.OptionTypes;
import com.github.voxxin.api.config.option.StringConfigOption;

public interface Option {
    OptionTypes type();

    BooleanConfigOption getAsBoolean();

    IntegerConfigOption getAsInt();

    StringConfigOption getAsString();
}
