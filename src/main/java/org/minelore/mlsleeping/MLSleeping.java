package org.minelore.mlsleeping;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MLSleeping extends JavaPlugin {
    private RandomQuoteExpansion expansion;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        loadCommands();

        QuoteTask quoteTask = new QuoteTask(this, this);
        quoteTask.start();

        expansion = new RandomQuoteExpansion(this);
        if (!expansion.register()) {
            getLogger().warning("Не смогли зарегистрировать RandomQuoteExpansion.");
        } else {
            getLogger().info("RandomQuoteExpansion зарегистрирован.");
        }
    }

    public void sendMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    PlaceholderAPI.setPlaceholders(player, "%mlsleep_prefix%" + message)));
        }
    }

    private void loadCommands() {
        Objects.requireNonNull(getCommand("sleep")).setExecutor(new MainCommand(this));
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        if (expansion != null) expansion.unregister();
    }
}
