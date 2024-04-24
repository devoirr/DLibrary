package me.kervand.library.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Made for building ItemStack objects.
 */
public class ItemBuilder {

    private final ItemStack stack;
    private final ItemMeta meta;

    public ItemBuilder(ItemStack stack) {
        this.stack = stack.clone();
        meta = this.stack.getItemMeta();
    }

    public ItemBuilder(ItemBuilder builder) {
        stack = builder.stack.clone();
        meta = builder.meta.clone();
    }

    public ItemBuilder(Material material) {
        stack = new ItemStack(material);
        meta = stack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        stack = new ItemStack(material, amount);
        meta = stack.getItemMeta();
    }

    public ItemBuilder setName(Component component) {
        meta.displayName(component);
        return this;
    }

    public ItemBuilder setLore(List<Component> componentList) {
        meta.lore(componentList);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag itemFlag) {
        stack.addItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        stack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment) {
        stack.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStack build() {
        stack.setItemMeta(meta);
        return stack;
    }
}
