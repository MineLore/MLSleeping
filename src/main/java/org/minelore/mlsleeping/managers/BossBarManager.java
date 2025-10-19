package org.minelore.mlsleeping.managers;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BossBarManager {
    private final BossBar voteBar = Bukkit.createBossBar("Прогресс голосования за пропуск ночи: 1/1", BarColor.BLUE, BarStyle.SOLID);
    private final Plugin plugin;

    public BossBarManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void create() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            voteBar.addPlayer(player);
        }
        voteBar.setVisible(true);
    }

    public void update(int quantity) {
        double required = (double) plugin.getConfig().getInt("playerSleepingPercentage") / 100 * Bukkit.getOnlinePlayers().size();
        required = Math.ceil(required);
        voteBar.setProgress((double) quantity / required);
        voteBar.setTitle("Прогресс голосования за пропуск ночи: " + quantity + "/" + required);
    }

    public void remove() {
        voteBar.removeAll();
        voteBar.setVisible(false);
    }
}
