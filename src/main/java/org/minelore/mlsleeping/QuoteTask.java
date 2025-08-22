package org.minelore.mlsleeping;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class QuoteTask extends BukkitRunnable {
    private final Plugin plugin;
    private final MLSleeping mlSleeping;

    public QuoteTask(Plugin plugin, MLSleeping mlSleeping) {
        this.plugin = plugin;
        this.mlSleeping = mlSleeping;
    }

    @Override
    public void run() {
        long time = Objects.requireNonNull(Bukkit.getWorld("world")).getTime();
        if (time == 23500) {
            mlSleeping.sendMessage("%mlsleep_quote%");
        }
    }

    public void start() {
        this.runTaskTimer(plugin, 0L, 1L);
    }
}
