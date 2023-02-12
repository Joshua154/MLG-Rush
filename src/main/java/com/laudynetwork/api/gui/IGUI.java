package com.laudynetwork.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface IGUI extends InventoryHolder {

    void onClick(Player player, int slot, ItemStack clickedItem, ClickType clickType);

}
