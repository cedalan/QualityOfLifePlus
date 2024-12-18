package com.elias.qualityoflifeplus.utils;

import org.bukkit.Material;
import java.util.Arrays;

public class ToolUtils {

    public static boolean isShovel(Material material) {
        return material.name().endsWith("_SHOVEL");
    }
    
    public static boolean isPickaxe(Material material) {
        return material.name().endsWith("_PICKAXE");
    }

    public static boolean isAxe(Material material) {
        return material.name().endsWith("_AXE");
    }

    public static boolean isHoe(Material material) {
        return material.name().endsWith("_HOE");
    }

    public static boolean isSword(Material material) {
        return material.name().endsWith("_SWORD");
    }

    public static String getToolCategory(Material material) {
        if (isShovel(material)) {
            return "Shovel";
        } else if (isPickaxe(material)) {
            return "Pickaxe";
        } else if (isAxe(material)) {
            return "Axe";
        } else if (isHoe(material)) {
            return "Hoe";
        } else if (isSword(material)) {
            return "Sword";
        } else {
            return "Other";
        }
    }

    public static boolean isValidTool(String probableTool) {
        String[] tools = {"Shovel", "Axe", "Pickaxe", "Hoe", "Sword"};
        return Arrays.stream(tools).anyMatch(probableTool::equals);
    }
}
