package com.laudynetwork.mlgrush.actionbar;

import com.laudynetwork.api.chatutils.HexColor;
import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import com.laudynetwork.mlgrush.game.PlayerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerGameInfo {
    final String mainColor = MLG_Rush.get().getColors().get("mainColor");
    final String highlight = MLG_Rush.get().getColors().get("highlight");
    Game game = MLG_Rush.get().getGame();
    PlayerManager playerManager;
    String message;


    public PlayerGameInfo(Player player) {
        playerManager = game.getPlayer(player);
        this.message = "Time: <time>";
        run();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (game.isRunning) {
                    Component timerComponent = MiniMessage.miniMessage().deserialize(mainColor + message,
                            Placeholder.component("time", Component.text(highlight + game.getTime() + mainColor))
                    );


                    playerManager.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, HexColor.asTextComponent("test"));

                    //playerManager.getPlayer().sendMessage(timerComponent);
                }
            }
        }.runTaskTimer(MLG_Rush.get(), 0, 1L);
    }
}
