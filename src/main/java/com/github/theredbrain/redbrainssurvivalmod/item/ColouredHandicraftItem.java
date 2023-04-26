package com.github.theredbrain.redbrainssurvivalmod.item;

import net.minecraft.item.Item;

import java.util.List;

public class ColouredHandicraftItem extends HandicraftItem {
    public final int colour;
    public ColouredHandicraftItem(int colour, List<Item> products, int maxCraftTime, Settings settings) {
        super(products, maxCraftTime, settings);
        this.colour = colour;
    }

    public int getColour() {
        return colour;
    }
}
