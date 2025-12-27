package net.komisarzprog.minecart_tweaks;

import net.minecraft.item.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecartFuelHelper {
    private static final Map<Item, Integer> FUEL_MAP = new HashMap<>();
    private static int itemSmeltTime = 200;

    static
    {

        FUEL_MAP.put(Items.LAVA_BUCKET, itemSmeltTime * 100);
        FUEL_MAP.put(Items.COAL_BLOCK, itemSmeltTime * 80);
        FUEL_MAP.put(Items.BLAZE_ROD, itemSmeltTime * 12);
        FUEL_MAP.put(Items.COAL, itemSmeltTime * 8);
        FUEL_MAP.put(Items.CHARCOAL, itemSmeltTime * 8);
        FUEL_MAP.put(Items.BAMBOO_MOSAIC, itemSmeltTime * 3/2);
        FUEL_MAP.put(Items.BAMBOO_MOSAIC_STAIRS, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.BAMBOO_MOSAIC_SLAB, itemSmeltTime * 3 / 4);
        FUEL_MAP.put(Items.NOTE_BLOCK, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.BOOKSHELF, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.CHISELED_BOOKSHELF, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.LECTERN, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.JUKEBOX, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.CHEST, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.TRAPPED_CHEST, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.CRAFTING_TABLE, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.DAYLIGHT_DETECTOR, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.BOW, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.FISHING_ROD, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.LADDER, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.WOODEN_SHOVEL, itemSmeltTime);
        FUEL_MAP.put(Items.WOODEN_SWORD, itemSmeltTime);
        FUEL_MAP.put(Items.WOODEN_HOE, itemSmeltTime);
        FUEL_MAP.put(Items.WOODEN_AXE, itemSmeltTime);
        FUEL_MAP.put(Items.WOODEN_PICKAXE, itemSmeltTime);
        FUEL_MAP.put(Items.STICK, itemSmeltTime / 2);
        FUEL_MAP.put(Items.BOWL, itemSmeltTime / 2);
        FUEL_MAP.put(Items.DRIED_KELP_BLOCK, 1 + itemSmeltTime * 20);
        FUEL_MAP.put(Items.CROSSBOW, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.BAMBOO, itemSmeltTime / 4);
        FUEL_MAP.put(Items.DEAD_BUSH, itemSmeltTime / 2);
        FUEL_MAP.put(Items.SHORT_DRY_GRASS, itemSmeltTime / 2);
        FUEL_MAP.put(Items.TALL_DRY_GRASS, itemSmeltTime / 2);
        FUEL_MAP.put(Items.SCAFFOLDING, itemSmeltTime / 4);
        FUEL_MAP.put(Items.LOOM, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.BARREL, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.CARTOGRAPHY_TABLE, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.FLETCHING_TABLE, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.SMITHING_TABLE, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.COMPOSTER, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.AZALEA, itemSmeltTime / 2);
        FUEL_MAP.put(Items.FLOWERING_AZALEA, itemSmeltTime / 2);
        FUEL_MAP.put(Items.MANGROVE_ROOTS, itemSmeltTime * 3 / 2);
        FUEL_MAP.put(Items.LEAF_LITTER, itemSmeltTime / 2);
    }

    private static final TagKey<Item>[] WOOD_TAGS = new TagKey[]
    {
            ItemTags.LOGS,
            ItemTags.PLANKS,
            ItemTags.WOODEN_BUTTONS,
            ItemTags.WOODEN_DOORS,
            ItemTags.WOODEN_FENCES,
            ItemTags.WOODEN_SLABS,
            ItemTags.WOODEN_SHELVES,
            ItemTags.WOODEN_STAIRS,
            ItemTags.WOODEN_PRESSURE_PLATES,
            ItemTags.WOODEN_TRAPDOORS,
            ItemTags.FENCE_GATES,
            ItemTags.SIGNS,
            ItemTags.HANGING_SIGNS,
            ItemTags.BOATS,
            ItemTags.SAPLINGS
    };

    public static boolean isFuel(ItemStack stack)
    {
        if(stack.isEmpty()) return false;

        if(FUEL_MAP.containsKey(stack.getItem())) return true;

        for(TagKey<Item> tag : WOOD_TAGS)
        {
            if(stack.isIn(tag) && !stack.isIn(ItemTags.NON_FLAMMABLE_WOOD)) return true;
        }

        if(stack.isIn(ItemTags.BAMBOO_BLOCKS)) return true;
        if(stack.isIn(ItemTags.BANNERS)) return true;
        if(stack.isIn(ItemTags.WOOL)) return true;
        if(stack.isIn(ItemTags.WOOL_CARPETS)) return true;

        return false;
    }

    public static int getFuelTicks(ItemStack stack)
    {
        if(stack.isEmpty()) return 0;

        if(isFuel(stack))
        {
            Integer value = FUEL_MAP.get(stack.getItem());
            if(value != null) return value;

            if(stack.isIn(ItemTags.LOGS) ||
               stack.isIn(ItemTags.BAMBOO_BLOCKS) ||
               stack.isIn(ItemTags.PLANKS) ||
               stack.isIn(ItemTags.WOODEN_STAIRS) ||
               stack.isIn(ItemTags.WOODEN_TRAPDOORS) ||
               stack.isIn(ItemTags.WOODEN_PRESSURE_PLATES) ||
               stack.isIn(ItemTags.WOODEN_SHELVES) ||
               stack.isIn(ItemTags.WOODEN_FENCES) ||
               stack.isIn(ItemTags.FENCE_GATES) ||
               stack.isIn(ItemTags.BANNERS)) return itemSmeltTime * 3/2;

            if(stack.isIn(ItemTags.WOODEN_DOORS) ||
               stack.isIn(ItemTags.SIGNS)) return itemSmeltTime;

            if(stack.isIn(ItemTags.WOOL) ||
               stack.isIn(ItemTags.WOODEN_BUTTONS) ||
               stack.isIn(ItemTags.SAPLINGS)) return itemSmeltTime / 2;

            if(stack.isIn(ItemTags.WOODEN_SLABS)) return itemSmeltTime * 3/4;

            if(stack.isIn(ItemTags.HANGING_SIGNS)) return  itemSmeltTime * 4;

            if(stack.isIn(ItemTags.BOATS)) return itemSmeltTime * 6;

            if(stack.isIn(ItemTags.WOOL_CARPETS)) return 1 + itemSmeltTime / 3;
        }

        return 0;
    }
}
