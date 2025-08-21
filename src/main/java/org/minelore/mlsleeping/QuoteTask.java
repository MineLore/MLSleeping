package org.minelore.mlsleeping;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class QuoteTask extends BukkitRunnable {
    private final Plugin plugin;

    public QuoteTask(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        long time = Objects.requireNonNull(Bukkit.getWorld("world")).getTime();

    }

    public void start() {
        this.runTaskTimer(plugin, 0L, 20L);
    }
}
