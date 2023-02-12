package com.laudynetwork.mlgrush.game;

import com.laudynetwork.mlgrush.MLG_Rush;

public class PlayerStats {
    private final PlayerManager pm;
    private int kills;
    private int deaths;
    private int bedsDestroyed;
    private int timePlayed;
    private int roundsWon;
    private int roundsPlayed;

    public PlayerStats(PlayerManager pm) {
        this.pm = pm;

        //Document document = MongoManager.getInstance().getDatabase().getCollection("mlgRush").find(Filters.eq("_id", pm.player.getUniqueId().toString())).first();

        String document = null;

        if (document != null) {
            /*this.kills = document.getInteger("kills");
            this.deaths = document.getInteger("deaths");
            this.bedsDestroyed = document.getInteger("bedsDestroyed");
            this.timePlayed = document.getInteger("timePlayed");
            this.roundsWon = document.getInteger("roundsWon");
            this.roundsPlayed = document.getInteger("roundsPlayed");
            pm.color = document.getString("selectedColor");
            pm.invOrder = document.getList("inventoryOrder", String.class);*/
        } else {
            this.kills = 0;
            this.deaths = 0;
            this.bedsDestroyed = 0;
            this.timePlayed = 0;
            this.roundsWon = 0;
            this.roundsPlayed = 0;
            if (pm.player.getName() == "LaudyTV") {
                pm.color = "magenta";
            } else if (pm.player.getName() == "DreamMon") {
                pm.color = "black";
            } else if (pm.player.getName() == "TimetraveIIer") {
                pm.color = "light_gray";
            } else {
                pm.color = defaultColors.values()[0].name();
            }
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
        document.append("inventoryOrder", pm.invOrder);

        MongoManager.getInstance().getDatabase().getCollection("mlgRush").replaceOne(Filters.eq("_id", pm.player.getUniqueId().toString()), document, new ReplaceOptions().upsert(true));
*/
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
