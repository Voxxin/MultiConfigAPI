package com.github.voxxin.api.config.option;

import com.github.voxxin.Option;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractOption implements Option {
    private final String translatableKey;

    public AbstractOption(@NotNull String translatableKey) {
        this.translatableKey = translatableKey;
    }

    public String getTranslationKey() {
        return this.translatableKey;
    }

    @Override
    public BooleanConfigOption getAsBoolean() {
        return (BooleanConfigOption)this;
    }

    @Override
    public IntegerConfigOption getAsInt() {
        return (IntegerConfigOption)this;
    }

    @Override
    public StringConfigOption getAsString() {
        return (StringConfigOption)this;
    }
}
