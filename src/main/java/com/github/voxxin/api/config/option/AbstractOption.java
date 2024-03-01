package com.github.voxxin.api.config.option;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class AbstractOption {
    private final String translatableKey;


    public AbstractOption(@NotNull String translatableKey) {
        this.translatableKey = translatableKey;
    }

    public String getTranslationKey() {
        return this.translatableKey;
    }
}
