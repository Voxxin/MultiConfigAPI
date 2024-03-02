package com.github.voxxin.api.config.option;

import java.util.ArrayList;

// todo fix this, implement methods
public abstract class CycleConfigOption extends AbstractOption {

    private final ArrayList<AbstractOption> cyclableOptions;
    private int index = 0;

    public CycleConfigOption(String translatableKey) {
        this(translatableKey, new ArrayList<>());
    }

    public CycleConfigOption(String translatableKey, ArrayList<AbstractOption> cyclableOptions) {
        super(translatableKey);
        this.cyclableOptions = cyclableOptions;
    }

    public void addOption(AbstractOption option) {
        this.cyclableOptions.add(option);
    }

    public ArrayList<AbstractOption> getOptions() {
        return this.cyclableOptions;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    /**
     * Returns you the current option of a specified index.
     */
    public AbstractOption getOption(int index) {
        return this.cyclableOptions.get(index);
    }

    /**
     * Returns you the current option in the index.
     */
    public AbstractOption getOption() {
        return this.cyclableOptions.get(index);
    }

    /**
     * Gets you the next value in the cycle.
     * Once the end is reached, it breaks/nulls.
     */
    public AbstractOption getNext() {
        if (this.cyclableOptions.size() <= this.index) return null;
        AbstractOption option = this.getOption(this.index);
        this.index++;

        return option;
    }

    /**
     * Gets you the next value in the cycle.
     * Once the end is reached, it wraps back to the start.
     */
    public AbstractOption getNextWrap() {
        this.index = this.cyclableOptions.size() > this.index ? index : 0;
        AbstractOption option = this.getOption(this.index);
        this.index++;

        return option;
    }

}
