package com.github.voxxin.api.config.option;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class EmptyOption {
    private Component displayText;

    public EmptyOption(@NotNull String translatableTextComponent) {
        this.displayText = Component.translatable(translatableTextComponent);
    }

    public EmptyOption(@NotNull Component textComponent) {
        this.displayText = textComponent;
    }

    public Component getDisplayText() {
        return this.displayText;
    }

    public void setDisplayText(@NotNull Component displayText) {
        this.displayText = displayText;
    }

    public void setDisplayText(@NotNull String displayText) {
        if (!displayText.isEmpty()) this.displayText = Component.literal(displayText);
        else this.displayText = Component.empty();
    }
}
