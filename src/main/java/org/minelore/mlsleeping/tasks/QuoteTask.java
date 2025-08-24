package org.minelore.mlsleeping.tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.minelore.mlsleeping.utils.MessageUtil;

import java.util.Objects;

public class QuoteTask extends BukkitRunnable {
    private final Plugin plugin;
    private final MessageUtil message;

    public QuoteTask(Plugin plugin, MessageUtil message) {
        this.plugin = plugin;
        this.message = message;
    }

    @Override
    public void run() {
        long time = Objects.requireNonNull(Bukkit.getWorld("world")).getTime();
        if (time == 23500) {
            message.send("%mlsleep_quote%");
        }
    }

    public void start() {
        this.runTaskTimer(plugin, 0L, 1L);
    }
}
