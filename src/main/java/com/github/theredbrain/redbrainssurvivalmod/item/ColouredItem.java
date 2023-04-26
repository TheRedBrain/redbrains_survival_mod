package com.github.theredbrain.redbrainssurvivalmod.item;

import net.minecraft.item.Item;

public class ColouredItem extends Item {
    public final int colour;
    public ColouredItem(int colour, Settings settings) {
        super(settings);
        this.colour = colour;
    }

    public int getColour() {
        return colour;
    }
}
