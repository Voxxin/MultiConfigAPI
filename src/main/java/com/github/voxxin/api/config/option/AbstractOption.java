package com.github.voxxin.api.config.option;

import com.github.voxxin.Option;
import com.github.voxxin.api.config.option.premade.CycleConfigOption;
import com.github.voxxin.api.config.option.premade.SliderConfigOption;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractOption implements Option {
    private final String translatableKey;

    public AbstractOption(@NotNull String translatableKey) {
        this.translatableKey = translatableKey;
    }

    public String getTranslationKey() {
        return this.translatableKey;
    }

    /**
     * Converts AbstractOption to {@link BooleanConfigOption}
     *
     * @return {@link BooleanConfigOption}
     * @throws IllegalStateException if config option is not a boolean
     */
    @Override
    public BooleanConfigOption getAsBoolean() {
        if (!(this instanceof BooleanConfigOption)) throw new IllegalStateException("Not a boolean: " + this);
        return (BooleanConfigOption)this;
    }

    /**
     * Converts AbstractOption to {@link FloatConfigOption}
     *
     * @return {@link FloatConfigOption}
     * @throws IllegalStateException if config option is not a float
     */
    @Override
    public FloatConfigOption getAsFloat() {
        if (!(this instanceof FloatConfigOption)) throw new IllegalStateException("Not a float: " + this);
        return (FloatConfigOption)this;
    }

    /**
     * Converts AbstractOption to {@link StringConfigOption}
     *
     * @return {@link StringConfigOption}
     * @throws IllegalStateException if config option is not a string
     */
    @Override
    public StringConfigOption getAsString() {
        if (!(this instanceof StringConfigOption)) throw new IllegalStateException("Not a string: " + this);
        return (StringConfigOption)this;
    }

    /**
     * Converts AbstractOption to {@link CycleConfigOption}
     *
     * @return {@link CycleConfigOption}
     * @throws IllegalStateException if config option is not a cycle
     */
    @Override
    public <T> CycleConfigOption<T> getAsCycle() {
        if (!(this instanceof CycleConfigOption)) throw new IllegalStateException("Not a cycle: " + this);
        return (CycleConfigOption<T>)this;
    }

    /**
     * Converts AbstractOption to {@link SliderConfigOption}
     *
     * @return {@link SliderConfigOption}
     * @throws IllegalStateException if config option is not a slider
     */
    @Override
    public SliderConfigOption getAsSlider() {
        if (!(this instanceof SliderConfigOption)) throw new IllegalStateException("Not a slider: " + this);
        return (SliderConfigOption)this;
    }

    @Override
    public ConfigOption getAsObject() {
        if (!(this instanceof ConfigOption)) throw new IllegalStateException("Not an object: " + this);
        return (ConfigOption)this;
    }
}
