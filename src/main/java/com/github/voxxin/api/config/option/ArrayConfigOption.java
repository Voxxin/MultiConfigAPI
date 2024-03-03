package com.github.voxxin.api.config.option;

import com.github.voxxin.api.config.option.enums.OptionTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ArrayConfigOption<T> extends AbstractOption {
    private final ArrayList<T> arrayElements = new ArrayList<>();

    public ArrayConfigOption(@NotNull String translatableKey) {
        super(translatableKey);
    }

    /**
     * Adds provided element to array
     * @throws RuntimeException if the element type is not a supported one
     */
    public void addElement(T element) {
        if (!isValid(element)) throw new RuntimeException("Non supported element type for: " + element);
        this.arrayElements.add(element);
    }

    public ArrayList<T> getElements() {
        return this.arrayElements;
    }

    public T getElement(int index) {
        return this.arrayElements.get(index);
    }

    public boolean isEmpty() {
        return this.arrayElements.isEmpty();
    }

    public int size() {
        return this.arrayElements.size();
    }

    public boolean contains(T element) {
        return this.arrayElements.contains(element);
    }

    @Override
    public OptionTypes type() {
        return OptionTypes.ARRAY;
    }

    private boolean isValid(T element) {
        return element instanceof Boolean ||
               element instanceof Float ||
               element instanceof String;
    }
}
