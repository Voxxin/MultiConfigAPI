package com.github.voxxin.api.config.option;

import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ArrayOption<T> extends AbstractOption {
    private final ArrayList<T> arrayElements = new ArrayList<>();

    public ArrayOption(@NotNull String translatableKey) {
        super(translatableKey);
    }

    public void addElement(T element) {
        if (!isValid(element)) return;
        this.arrayElements.add(element);
    }

    public ArrayList<T> getElements() {
        return this.arrayElements;
    }

    public T getElement(int index) {
        return this.arrayElements.get(index);
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.ARRAY;
    }

    private boolean isValid(T element) {
        return element instanceof Boolean || element instanceof Float || element instanceof String;
    }
}
