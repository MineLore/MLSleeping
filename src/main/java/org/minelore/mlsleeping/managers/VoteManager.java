package org.minelore.mlsleeping.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.minelore.mlsleeping.utils.MessageUtil;

import java.util.*;

public class VoteManager {
    private final Map<UUID, Boolean> votedPlayers = new HashMap<>();
    public final Set<UUID> sleepingPlayers = new HashSet<>();

    private boolean isVotingActive = false;
    private final MessageUtil message;
    private final Plugin plugin;
    private final BossBarManager voteBar;

    public VoteManager(MessageUtil message, Plugin plugin, BossBarManager voteBar) {
        this.message = message;
        this.plugin = plugin;
        this.voteBar = voteBar;
    }

    public boolean isVotingActive() {
        return isVotingActive;
    }

    public void setVotingActive(boolean votingActive) {
        isVotingActive = votingActive;
        sleepingPlayers.clear();
    }

    public void addSleepingPlayer(UUID player) {
        if (sleepingPlayers.contains(player)) return;
        sleepingPlayers.add(player);
        if (!isVotingActive) {
            start();
        }
        vote(player, true);
    }
    public void removeSleepingPlayer(UUID player) {
        sleepingPlayers.remove(player);
        votedPlayers.remove(player);
        if (sleepingPlayers.isEmpty() && isVotingActive) {
            isVotingActive = false;
            message.send("Голосование остановлено, нет спящих игроков");
        }
    }

    public boolean isPlayerVoted(UUID player) {
        return votedPlayers.containsKey(player);
    }

    public void start() {
        isVotingActive = true;
        voteBar.create();
        votedPlayers.clear();
        message.send("Началось голосование за пропуск ночи!");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!sleepingPlayers.contains(player.getUniqueId())) {
                message.sendToPlayer("Желаешь ли ты пропустить эту ночь? " +
                        Component.text("<green>[Да] ").clickEvent(ClickEvent.runCommand("/skipnight yes"))
                                .hoverEvent(HoverEvent.showText(Component.text("Проголосовать за пропуск ночи"))) +
                        Component.text("<red>[Нет]").clickEvent(ClickEvent.runCommand("/skipnight no"))
                                .hoverEvent(HoverEvent.showText(Component.text("Проголосовать против пропуска ночи"))),
                        player.getUniqueId());
            }
        }
    }

    public void vote(UUID player, boolean res) {
        votedPlayers.put(player, res);
        message.sendToPlayer("Твой голос учтён. Спасибо за участие в опросе!", player);
        double percentage = (double) plugin.getConfig().getInt("PlayerSleepingPercentage") / 100;
        int votedYes = 0;
        int votedNo = 0;
        for (boolean votedPlayer : votedPlayers.values()) {
            if (votedPlayer) {
                votedYes++;
            } else {
                votedNo++;
            }
        }
        voteBar.update(votedYes);
        if ((double) votedYes / Bukkit.getOnlinePlayers().size() >= percentage) {
            finish(true);
        } else if ((double) votedNo / Bukkit.getOnlinePlayers().size() >= percentage) {
            finish(false);
        }
    }

    public void finish(boolean skip) {
        isVotingActive = false;
        voteBar.remove();
        if (skip) {
            message.send("Голосование завершено. Скоро наступит утро!");
            Objects.requireNonNull(Bukkit.getWorld("world")).setTime(0);
        } else {
            message.send("Голосование завершено в пользу того, что ночь пройдёт как обычно и не будет пропущена.");
        }
    }
}