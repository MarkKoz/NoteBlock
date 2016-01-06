package com.mark.nbgui.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;

public enum Instrument {
    PIANO,
    BASSDRUM,
    SNARE,
    CLICKS,
    BASSGUITAR;

    private static Instrument[] values = Instrument.values();

    public static Instrument get(Block block) {
        if (block == null) {
            return Instrument.PIANO;
        }
        Material material = block.getMaterial();
        int id = 0;
        if (material == Material.rock) {
            id = 1;
        } else if (material == Material.sand) {
            id = 2;
        } else if (material == Material.glass) {
            id = 3;
        } else if (material == Material.wood) {
            id = 4;
        }
        return Instrument.values[id];
    }

    public int id() {
        return this.ordinal();
    }

    public String translate() {
        switch (this) {
            case PIANO:
                return I18n.format("nbgui.string.instrument.piano");
            case BASSDRUM:
                return I18n.format("nbgui.string.instrument.bassdrum");
            case SNARE:
                return I18n.format("nbgui.string.instrument.snaredrum");
            case CLICKS:
                return I18n.format("nbgui.string.instrument.clicks");
            case BASSGUITAR:
                return I18n.format("nbgui.string.instrument.doublebass");
        }
        return "";
    }
}