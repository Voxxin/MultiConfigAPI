package com.github.voxxin.api.config.option;

import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ArrayOption<T> extends AbstractOption {
    private final ArrayList<T> arrayElements = new ArrayList<>();
    private Class<?> clazz;

    public ArrayOption(@NotNull String translatableKey) {
        super(translatableKey);
    }

    public void addElement(T element) {
        if (!isValid(element)) return;
        arrayElements.add(element);
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

    public Class<?> arrayType() {
        return this.clazz;
    }

    public Boolean arrayType(Class<?> clazz) {
        return this.clazz == clazz;
    }


    private boolean isValid(T element) {
        if (element instanceof Boolean) clazz = Boolean.class;
        if (element instanceof Float) clazz = Float.class;
        if (element instanceof String) clazz = String.class;
        return element instanceof Boolean || element instanceof Float || element instanceof String;
    }
}
