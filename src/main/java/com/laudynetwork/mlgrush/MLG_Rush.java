package com.laudynetwork.mlgrush;

import com.laudynetwork.mlgrush.game.Game;
import com.laudynetwork.mlgrush.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class MLG_Rush extends JavaPlugin {
    private static MLG_Rush instance;
    private final Map<String, String> colors = new HashMap<>();
    Game game;

    public static MLG_Rush get() {
        return instance;
    }

    public Map<String, String> getColors() {
        return colors;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        colors.put("mainColor", "#CCCCCC");
        colors.put("highlight", "#FF4F4F");
        colors.put("MLGRush-Prefix", "&x333AFF&lM&xFFFFFF&lL&xEA302E&lG&x999999-&xf2f2f2&lRush");
        colors.put("serverAddress", "laudynetwork.com");


        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new BlockBreakListener(), this);
        manager.registerEvents(new BlockPlaceListener(), this);
        manager.registerEvents(new DeathListener(), this);
        manager.registerEvents(new FoodLevelChange(), this);
//        manager.registerEvents(new InteractListener(), this);
        manager.registerEvents(new ItemDropListener(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LeaveListener(), this);
        manager.registerEvents(new MoveListener(), this);


        //setup World
        World world = Bukkit.getWorlds().get(0);
        world.setTime(18000);
        //world.setTime(6000);
        world.setClearWeatherDuration(9999);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DISABLE_RAIDS, true);
        world.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, false);
        world.setDifficulty(Difficulty.HARD);

        game = new Game();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Game getGame() {
        return game;
    }
}
