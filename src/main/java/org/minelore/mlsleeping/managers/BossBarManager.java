package org.minelore.mlsleeping.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BossBarManager {
    private final BossBar voteBar = BossBar.bossBar(
            Component.text("Прогресс голосования за пропуск ночи: ?/?").color(NamedTextColor.BLUE),
            1.0f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);
    private final Plugin plugin;

    public BossBarManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void create() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showBossBar(voteBar);
        }
    }

    public void update(int quantity) {
        double required = (double) plugin.getConfig().getInt("playerSleepingPercentage") / 100 * Bukkit.getOnlinePlayers().size();
        required = Math.ceil(required);
        voteBar.progress((float) (quantity / required));
        voteBar.name(Component.text("Прогресс голосования за пропуск ночи: " + quantity + "/" + (int)required)
                .color(NamedTextColor.BLUE));
    }

    public void remove() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.hideBossBar(voteBar);
        }
    }
}
