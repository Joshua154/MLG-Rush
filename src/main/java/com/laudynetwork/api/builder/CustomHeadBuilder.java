package com.laudynetwork.api.builder;

import com.laudynetwork.api.gameprofile.TextureFetcher;
import com.laudynetwork.api.gameprofile.UUIDFetcher;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class CustomHeadBuilder {

    private final SkullMeta skullMeta;
    private final ItemStack itemStack;

    public CustomHeadBuilder() {
        this.itemStack = new ItemStack(Material.PLAYER_HEAD);
        this.skullMeta = (SkullMeta) itemStack.getItemMeta();
    }

    public CustomHeadBuilder setDisplayName(String displayName) {
        this.skullMeta.setDisplayName(displayName);
        return this;
    }

    public CustomHeadBuilder setLore(String... lore) {
        this.skullMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public CustomHeadBuilder addItemFlags(ItemFlag... itemFlags) {
        this.skullMeta.addItemFlags(itemFlags);
        return this;
    }

    public CustomHeadBuilder addEnchant(Enchantment enchantment, int level, boolean levelRestriction) {
        this.skullMeta.addEnchant(enchantment, level, levelRestriction);
        return this;
    }

    public CustomHeadBuilder setLocalizedName(String localizedName) {
        this.skullMeta.setLocalizedName(localizedName);
        return this;
    }

    public CustomHeadBuilder setCustomModelData(int data) {
        this.skullMeta.setCustomModelData(data);
        return this;
    }

    public CustomHeadBuilder setUnbreakable(boolean unbreakable) {
        this.skullMeta.setUnbreakable(unbreakable);
        return this;
    }


    public CustomHeadBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public CustomHeadBuilder setType(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public CustomHeadBuilder addAttributeModifiers(Attribute attribute, AttributeModifier attributeModifier) {
        this.skullMeta.addAttributeModifier(attribute, attributeModifier);
        return this;
    }

    @SneakyThrows
    public CustomHeadBuilder setSkullOwner(@NotNull String url) {
        String finalUrl = "";
        try {
            UUID uuid = UUID.fromString(url);
            finalUrl = TextureFetcher.getSkinUrl(uuid.toString());
        } catch (Exception ignored) {
        }
        if (url.length() <= 16) {
            UUID uuid = UUIDFetcher.getUUID(url);
            if (uuid == null)
                finalUrl = "https://textures.minecraft.net/texture/" +
                        "647cf0f3b9ec9df2485a9cd4795b60a391c8e6ebac96354de06e3357a9a88607";
            else finalUrl = TextureFetcher.getSkinUrl(uuid.toString());
        } else if (finalUrl.isEmpty()) {
            finalUrl = "https://textures.minecraft.net/texture/" + url;
        }

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", finalUrl).getBytes());
        gameProfile.getProperties().put("textures", new Property("textures", new String(data)));
        try {
            Field field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, gameProfile);
            field.setAccessible(false);
        } catch (Exception ignored) {
        }
        return this;
    }


    public ItemStack build() {
        this.itemStack.setItemMeta(skullMeta);
        return this.itemStack;
    }
}
