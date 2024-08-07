package com.github.voxxin.api.config.option.premade;

import com.github.voxxin.api.config.option.ArrayConfigOption;
import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

public class CycleConfigOption<T> extends ArrayConfigOption<T> {
    private int index = 0;

    public CycleConfigOption(@NotNull String translatableKey) {
        super(translatableKey);
    }

    public boolean hasNext() {
        return this.index < this.size();
    }

    /**
     * @return {@link T} or if it is past the size limit it returns null
     */
    public T getNext() {
        if (this.index >= this.size()) return null;
        return this.getElement(this.index++);
    }

    /**
     * Returns the next element, or if it's past the size limit wrap around to 0
     * @return {@link T}
     */
    public T getNextWrap() {
        if ((this.index + 1) > this.size()) this.index = 0;
        return this.getNext();
    }

    public int getCurrentIndex() {
        return this.index;
    }

    public T getCurrentElement() {
        return this.getElement(this.index);
    }

    public void setCurrentIndex(int currentIndex) {
        if (currentIndex > this.size()) currentIndex = 0;
        this.index = currentIndex;
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.CYCLE;
    }
}
