package net.landania.homesystem.spigot.itembuilder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(ItemStack ItemStack) {
        itemStack = ItemStack;
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount, byte durability) {
        itemStack = new ItemStack(material, amount, durability);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean setUnbreakable) {
        itemMeta.setUnbreakable(setUnbreakable);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLore(List loreList) {
        itemMeta.setLore(loreList);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int enchantmentLevel, boolean ignoreLevelRestrictions) {
        itemMeta.addEnchant(enchantment, enchantmentLevel, ignoreLevelRestrictions);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        itemMeta.addItemFlags(itemFlag);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}