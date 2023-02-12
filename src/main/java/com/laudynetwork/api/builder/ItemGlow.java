package com.laudynetwork.api.builder;

import lombok.SneakyThrows;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class ItemGlow {

    @SneakyThrows
    public ItemGlow(Plugin plugin) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception ignored) {
        }

        NamespacedKey key = new NamespacedKey(plugin, plugin.getDescription().getName());
        Glow glow = new Glow(key);
        Enchantment.registerEnchantment(glow);
    }

    public static class Glow extends Enchantment {

        public Glow(NamespacedKey i) {
            super(i);
        }

        @NotNull
        @Override
        public String getName() {
            return "";
        }

        @Override
        public int getMaxLevel() {
            return 0;
        }

        @Override
        public int getStartLevel() {
            return 0;
        }

        @NotNull
        @Override
        public EnchantmentTarget getItemTarget() {
            return EnchantmentTarget.ARMOR;
        }

        @Override
        public boolean isTreasure() {
            return false;
        }

        @Override
        public boolean isCursed() {
            return false;
        }

        @Override
        public boolean conflictsWith(@NotNull Enchantment enchantment) {
            return false;
        }

        @Override
        public boolean canEnchantItem(@NotNull ItemStack itemStack) {
            return false;
        }

    }

}
