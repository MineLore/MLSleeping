package org.minelore.mlsleeping.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.minelore.mlsleeping.managers.VoteManager;
import org.minelore.mlsleeping.utils.MessageUtil;

import java.util.Objects;

public class TimeTask extends BukkitRunnable {
    private final MessageUtil message;
    private final VoteManager voteManager;

    public TimeTask(MessageUtil message, VoteManager voteManager) {
        this.message = message;
        this.voteManager = voteManager;
    }

    @Override
    public void run() {
        long time = Objects.requireNonNull(Bukkit.getWorld("world")).getTime();
        if (time == 0) {
            message.send("%mlsleep_quote%");
            voteManager.setVotingActive(false);
        }
    }
}
