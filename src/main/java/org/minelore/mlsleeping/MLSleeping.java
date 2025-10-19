package org.minelore.mlsleeping;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.minelore.mlsleeping.commands.SkipNightCommand;
import org.minelore.mlsleeping.listeners.MainListener;
import org.minelore.mlsleeping.managers.BossBarManager;
import org.minelore.mlsleeping.managers.VoteManager;
import org.minelore.mlsleeping.tasks.TimeTask;
import org.minelore.mlsleeping.utils.MessageUtil;

import java.util.Objects;

public final class MLSleeping extends JavaPlugin {
    private RandomQuoteExpansion expansion;
    private VoteManager voteManager;
    private MessageUtil messageUtil;
    private BukkitTask timeTask;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        messageUtil = new MessageUtil();

        BossBarManager bossBarManager = new BossBarManager(this);
        voteManager = new VoteManager(messageUtil, this, bossBarManager);

        getServer().getPluginManager().registerEvents(new MainListener(voteManager), this);

        timeTask = new TimeTask(messageUtil, voteManager).runTaskTimer(this, 0L, 0L);

        loadCommands();

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
        if (timeTask != null) timeTask.cancel();
        HandlerList.unregisterAll();
        if (expansion != null) expansion.unregister();
    }
}
