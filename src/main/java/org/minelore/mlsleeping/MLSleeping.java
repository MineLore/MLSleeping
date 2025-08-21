package org.minelore.mlsleeping;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MLSleeping extends JavaPlugin {

    @Override
    public void onEnable() {
        loadCommands();

        QuoteTask quoteTask = new QuoteTask(this);
        quoteTask.start();
    }

    private void loadCommands() {
        Objects.requireNonNull(getCommand("sleep")).setExecutor(new MainCommand());
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
