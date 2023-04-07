package com.laudynetwork.mlgrush.game;

import com.laudynetwork.database.mysql.MySQL;
import com.laudynetwork.database.mysql.SQLWrapper;
import com.laudynetwork.database.mysql.utils.Insert;
import com.laudynetwork.database.mysql.utils.Row;
import com.laudynetwork.database.mysql.utils.Select;
import com.laudynetwork.mlgrush.MLG_Rush;

public class PlayerStats {
    private final MySQL sql;
    private final PlayerManager pm;
    private int kills;
    private int deaths;
    private int bedsDestroyed;
    private int timePlayed;
    private int roundsWon;
    private int roundsPlayed;

    public PlayerStats(PlayerManager pm, MySQL sql) {
        this.pm = pm;
        this.sql = sql;

        //Document document = MongoManager.getInstance().getDatabase().getCollection("mlgRush").find(Filters.eq("_id", pm.player.getUniqueId().toString())).first();

        if (sql.rowExist(new Select("minecraft_mlgrush_stats", "uuid", pm.player.getUniqueId().toString()))) {
            Row result = sql.rowSelect(new Select()).getRows().get(0);

            this.kills = (int) result.get("kills");
            this.deaths = (int) result.get("deaths");
            this.bedsDestroyed = (int) result.get("bedsDestroyed");
            this.timePlayed = (int) result.get("timePlayed");
            this.roundsWon = (int) result.get("roundsWon");
            this.roundsPlayed = (int) result.get("roundsPlayed");
            pm.color = (String) result.get("selectedColor");
            pm.invOrder = SQLWrapper.fromStringToList((String) result.get("inventoryOrder"));
        } else {
            this.kills = 0;
            this.deaths = 0;
            this.bedsDestroyed = 0;
            this.timePlayed = 0;
            this.roundsWon = 0;
            this.roundsPlayed = 0;
            pm.color = defaultColors.values()[0].name();
        }

        if (MLG_Rush.get().getGame().getOpponent(pm.player) != null) {
            if (MLG_Rush.get().getGame().getOpponent(pm.player).color.equals(pm.color)) {
                for (defaultColors color : defaultColors.values()) {
                    if (color.name().equals(pm.color)) {
                        pm.color = defaultColors.getOpposite(defaultColors.valueOf(pm.color));
                        break;
                    }
                }
                if (MLG_Rush.get().getGame().getOpponent(pm.player).color.equals(pm.color)) {
                    pm.color = defaultColors.values()[0].name();
                }
            }
        }

        pm.generateBedMaterial();
        pm.generateInv();
    }

    public void saveDataToDatabase() {
        syncFromPM();

        /*Document document = new Document();
        document.append("_id", pm.player.getUniqueId().toString());
        document.append("kills", kills);
        document.append("deaths", deaths);
        document.append("bedsDestroyed", bedsDestroyed);
        document.append("timePlayed", timePlayed);
        document.append("roundsWon", roundsWon);
        document.append("roundsPlayed", roundsPlayed);
        document.append("selectedColor", pm.color);
        document.append("inventoryOrder", pm.invOrder);*/

        /*sqlConnection.insert("minecraft_mlgrush_stats",
                new SQLConnection.DataColumn("uuid", pm.player.getUniqueId().toString()),
                new SQLConnection.DataColumn("kills", kills),
                new SQLConnection.DataColumn("deaths", deaths),
                new SQLConnection.DataColumn("bedsDestroyed", bedsDestroyed),
                new SQLConnection.DataColumn("timePlayed", timePlayed),
                new SQLConnection.DataColumn("roundsWon", roundsWon),
                new SQLConnection.DataColumn("roundsPlayed", roundsPlayed),
                new SQLConnection.DataColumn("selectedColor", pm.color),
                new SQLConnection.DataColumn("inventoryOrder", SQLWrapper.fromListToString(pm.invOrder))
        );*/

        sql.tableInsert(new Insert("minecraft_mlgrush_stats",
                "uuid, kills, deaths, bedsDestroyed, timePlayed, roundsWon, roundsPlayed, selectedColor, inventoryOrder",
                pm.player.getUniqueId(), kills, deaths, bedsDestroyed, timePlayed, roundsWon, roundsPlayed, pm.color, SQLWrapper.fromListToString(pm.invOrder)));
    }

    public void syncFromPM() {
        this.kills += pm.kills;
        this.deaths += pm.deaths;
        this.bedsDestroyed += pm.bedsDestroyed;
        this.timePlayed += MLG_Rush.get().getGame().timer.getTime() * 1000;
        this.roundsWon += pm.roundsWon;
        this.roundsPlayed += pm.roundsPlayed;
    }

    private enum defaultColors {
        RED,
        BLUE;

        public static String getOpposite(defaultColors color) {
            if (color == defaultColors.values()[0]) {
                return defaultColors.values()[1].name();
            } else {
                return defaultColors.values()[0].name();
            }
        }
    }
}
