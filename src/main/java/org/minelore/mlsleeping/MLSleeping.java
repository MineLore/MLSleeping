package org.minelore.mlsleeping;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.minelore.mlsleeping.commands.SkipNightCommand;
import org.minelore.mlsleeping.listeners.MainListener;
import org.minelore.mlsleeping.managers.BossBarManager;
import org.minelore.mlsleeping.managers.VoteManager;
import org.minelore.mlsleeping.tasks.QuoteTask;
import org.minelore.mlsleeping.utils.MessageUtil;

import java.util.Objects;

public final class MLSleeping extends JavaPlugin {
    private RandomQuoteExpansion expansion;
    private final MessageUtil messageUtil;

    private VoteManager voteManager;

    public MLSleeping(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        loadCommands();

        QuoteTask quoteTask = new QuoteTask(this, messageUtil);
        quoteTask.start();

        BossBarManager bossBarManager = new BossBarManager(this);
        voteManager = new VoteManager(messageUtil, this, bossBarManager);

        getServer().getPluginManager().registerEvents(new MainListener(messageUtil, voteManager), this);

        expansion = new RandomQuoteExpansion(this);
        if (!expansion.register()) {
            getLogger().warning("Не смогли зарегистрировать RandomQuoteExpansion.");
        } else {
            getLogger().info("RandomQuoteExpansion зарегистрирован.");
        }
    }

    private void loadCommands() {
        Objects.requireNonNull(getCommand("skipnight")).setExecutor(new SkipNightCommand(messageUtil, voteManager));
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        if (expansion != null) expansion.unregister();
    }
}
