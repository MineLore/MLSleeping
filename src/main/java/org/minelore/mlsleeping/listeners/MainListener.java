package org.minelore.mlsleeping.listeners;

import dev.geco.gsit.api.event.PlayerPoseEvent;
import dev.geco.gsit.api.event.PlayerStopPoseEvent;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.minelore.mlsleeping.managers.VoteManager;

import java.util.Objects;

public class MainListener implements Listener {
    private final VoteManager voteManager;

    public MainListener( VoteManager voteManager) {
        this.voteManager = voteManager;
    }

    @EventHandler
    public void onPlayerSleep(PlayerDeepSleepEvent event) {
        voteManager.addSleepingPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerLeavesBed(PlayerBedLeaveEvent event) {
        voteManager.removeSleepingPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerGetPose(PlayerPoseEvent event) {
        if (event.getPose().getPose() == Pose.SLEEPING &&
            Objects.requireNonNull(Bukkit.getWorld("world")).getTime() >= 12542 &&
            Objects.requireNonNull(Bukkit.getWorld("world")).getTime() <= 23459
        ) {
            voteManager.addSleepingPlayer(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerLeavesPose(PlayerStopPoseEvent event) {
        if (event.getPose().getPose() == Pose.SLEEPING) {
            voteManager.removeSleepingPlayer(event.getPlayer().getUniqueId());
        }
    }
}
