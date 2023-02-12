package com.laudynetwork.mlgrush.timer;

import com.laudynetwork.mlgrush.MLG_Rush;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {
    private boolean running;
    private int time;
    private TimerMode timerMode;

    public Timer(boolean running, int time, TimerMode timerMode) {
        this.running = running;
        this.time = time;
        this.timerMode = timerMode;

        run();
    }


    //Running Logic
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    //Time
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isRunning()) {
                    return;
                }
                /*Bukkit.getOnlinePlayers().forEach(player -> {
                    player.stopSound(Sound.ENTITY_PLAYER_ATTACK_CRIT);
                    player.stopSound(Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK);
                    player.stopSound(Sound.ENTITY_PLAYER_ATTACK_NODAMAGE);
                    player.stopSound(Sound.ENTITY_PLAYER_ATTACK_WEAK);
                    player.stopSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP);
                    player.stopSound(Sound.ENTITY_PLAYER_ATTACK_STRONG);
                });*/
                setTime(getTime() + 1);
            }
        }.runTaskTimer(MLG_Rush.get(), 20, 20);
    }
}

