package com.laudynetwork.mlgrush.game;

import com.laudynetwork.api.chatutils.HexColor;
import com.laudynetwork.mlgrush.MLG_Rush;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class CountDown extends com.laudynetwork.api.countdown.CountDown {
    private final World world;
    private final String[] specials = {
            "#00ff001",
            "#59a60d2",
            "#b34d1a3",
            "#a6260d4",
            "#9900005"
    };

    public CountDown(int from, World world) {
        super(MLG_Rush.get(), from, -1, false, true, 0, 1, 2, 3, 4, 5);
        this.world = world;
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onLeave() {

    }

    @Override
    public void tick(int i) {

    }

    @Override
    public void tickSpecial(int i) {
        if (i == 0) {
            MLG_Rush.get().getGame().startMatch();
        } else {
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld() == world).forEach(player -> {
                player.playSound(player.getLocation(), "minecraft:block.note_block.xylophone", 100, 1);
                player.sendTitle(ChatColor.BOLD + HexColor.translate(specials[i - 1]), "sort Your Inventory", 0, 20, 0);
            });
        }

    }
}
