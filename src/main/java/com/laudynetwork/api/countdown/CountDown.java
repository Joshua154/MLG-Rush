package com.laudynetwork.api.countdown;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Set;

@Getter
public abstract class CountDown implements Runnable {

    private final Plugin plugin;
    private final int countFrom, countTo;
    private final boolean async, updateExpBar;
    private final Set<Integer> specialValues;
    private int state, taskID;

    public CountDown(Plugin plugin, int countFrom, int countTo, boolean async, boolean updateExpBar,
                     Integer... specialValues) {
        this.plugin = plugin;
        this.countFrom = countFrom;
        this.countTo = countTo;
        this.async = async;
        this.updateExpBar = updateExpBar;
        this.specialValues = Sets.newHashSet(specialValues);
        this.start();
    }

    public abstract void onEnter();

    public abstract void onLeave();

    public void start() {
        onEnter();
        this.state = this.countFrom;
        if (this.async)
            this.taskID =
                    plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 20L, 20L).getTaskId();
        else this.taskID = plugin.getServer().getScheduler().runTaskTimer(plugin, this, 20L, 20L).getTaskId();
    }

    public void reset() {
        this.state = this.countFrom;
    }

    public void setTimer(int i) {
        if (i < this.state) this.state = i;
    }

    public abstract void tick(int tick);

    public abstract void tickSpecial(int tickSpecial);

    public void run() {
        if (this.updateExpBar) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (this.state >= 0) player.setLevel(this.state);
                if ((float) this.state / (float) this.countFrom >= 0)
                    player.setExp((float) this.state / (float) this.countFrom);
            });
        }

        this.tick(this.state);
        if (this.specialValues.contains(this.state)) this.tickSpecial(this.state);

        --this.state;
        if (this.state <= this.countTo) {
            if (this.async) Bukkit.getScheduler().runTask(plugin, this::onLeave);
            else onLeave();

            plugin.getServer().getScheduler().cancelTask(this.taskID);
        }
    }

    public boolean isAsync() {
        return this.async;
    }

    public void stop() {
        plugin.getServer().getScheduler().cancelTask(this.taskID);
    }

    public boolean isUpdateExpBar() {
        return this.updateExpBar;
    }

}
