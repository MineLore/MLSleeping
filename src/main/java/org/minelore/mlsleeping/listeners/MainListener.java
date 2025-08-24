package org.minelore.mlsleeping.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.minelore.mlsleeping.managers.VoteManager;
import org.minelore.mlsleeping.utils.MessageUtil;

public class MainListener implements Listener {
    private final MessageUtil message;
    private final VoteManager voteManager;

    public MainListener(MessageUtil message, VoteManager voteManager) {
        this.message = message;
        this.voteManager = voteManager;
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event) {
        if (event.getSkipReason() == TimeSkipEvent.SkipReason.NIGHT_SKIP ||
                event.getSkipReason() == TimeSkipEvent.SkipReason.COMMAND) {
            message.send("%mlsleep_quote%");
        }
    }

    @EventHandler
    public void onPlayerSleep(PlayerBedLeaveEvent event) {
        voteManager.addSleepingPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerLeavesBed(PlayerBedLeaveEvent event) {
        voteManager.removeSleepingPlayer(event.getPlayer().getUniqueId());
    }
}
