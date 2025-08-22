package org.minelore.mlsleeping;

import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

public class MainListener implements Listener {
    private final MLSleeping mlSleeping;

    public MainListener(MLSleeping mlSleeping) {
        this.mlSleeping = mlSleeping;
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event) {
        if (event.getSkipReason() == TimeSkipEvent.SkipReason.NIGHT_SKIP) {
            mlSleeping.sendMessage("%mlsleep_quote%");
        }
    }

    @EventHandler
    public void onPlayerSleep(PlayerDeepSleepEvent event) {

    }
}
