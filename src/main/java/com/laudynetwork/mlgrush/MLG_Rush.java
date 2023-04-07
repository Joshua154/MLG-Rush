package com.laudynetwork.mlgrush;

//import com.comphenix.protocol.PacketType;
//import com.comphenix.protocol.ProtocolLibrary;
//import com.comphenix.protocol.ProtocolManager;
//import com.comphenix.protocol.events.ListenerPriority;
//import com.comphenix.protocol.events.PacketAdapter;
//import com.comphenix.protocol.events.PacketEvent;

import com.laudynetwork.database.mysql.MySQL;
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
    //public Map<Player, LanguageKey> playerLanguages = new HashMap<>();
    //@Getter
    //private final Map<LanguageKey, Translation> translations = new HashMap<>();
    Game game;
    /*private MessageBackend messageBackend;
    @Getter
    private SQLConnection sqlConnection;*/
    private MySQL sql;

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
        this.sql = new MySQL();

        sql.setHost("localhost");
        sql.setUser("root");
        sql.setPassword("uZkg8vBL2psZ4M9NWZ93");
        sql.setDb("laudynetwork");

        sql.connect();

        /*for(LanguageKey languageKey : LanguageKey.values()) {
            translations.put(languageKey, new Translation("62f196dd49346b5832da6bac", languageKey));
        }

        for(Document doc : MongoManager.getInstance().getDatabase().getCollection("colors").find()){
            colors.put(doc.get("_id").toString(), doc.get("colorCode").toString());
        }*/

        colors.put("mainColor", "<#CCCCCC>");
        colors.put("highlight", "<#FF4F4F>");
        colors.put("MLGRush-Prefix", "<bold><#333AFF>M<#FFFFFF>L<#EA302E>G<#999999>-<#f2f2f2>Rush");
        colors.put("serverAddress", "laudynetwork.com");

        /*SQLConnection dbConnection = NetworkUtils.getINSTANCE().getDbConnection();
        this.messageBackend = new MessageBackend(dbConnection, "mlg-rush");
        this.sqlConnection = messageBackend.getConnection();*/

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new BlockBreakListener(), this);
        manager.registerEvents(new BlockPlaceListener(), this);
        manager.registerEvents(new DeathListener(), this);
        manager.registerEvents(new FoodLevelChange(), this);
        manager.registerEvents(new InteractListener(), this);
        manager.registerEvents(new ItemDropListener(), this);
        manager.registerEvents(new JoinListener(sql), this);
        manager.registerEvents(new LeaveListener(sql), this);
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

        /*ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_SOUND) {
            @Override
            public void onPacketSending(PacketEvent event) {
                super.onPacketSending(event);

                System.out.println(event.getPacket());
            }
        });*/

        game = new Game(sql);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sql.close();
    }

    public Game getGame() {
        return game;
    }
}
