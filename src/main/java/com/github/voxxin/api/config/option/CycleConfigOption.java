package com.github.voxxin.api.config.option;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class CycleConfigOption extends EmptyOption {

    private final ArrayList<EmptyOption> cyclableOptions;

    public CycleConfigOption(Component component) {
        this(component, new ArrayList<>());
    }

    public CycleConfigOption(Component component, ArrayList<EmptyOption> cyclableOptions) {
        super(component);
        this.cyclableOptions = cyclableOptions;
    }

    public void addCycleOption(EmptyOption option) {
        this.cyclableOptions.add(option);
    }

    public ArrayList<EmptyOption> getCycles() {
        return this.cyclableOptions;
    }

    public EmptyOption getCycle(int index) {
        return this.cyclableOptions.get(index);
    }
}
