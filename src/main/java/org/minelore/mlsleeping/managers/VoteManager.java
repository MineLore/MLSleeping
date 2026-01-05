package org.minelore.mlsleeping.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.minelore.mlsleeping.utils.MessageUtil;

import java.util.*;

public class VoteManager {
    private final Map<UUID, Boolean> votedPlayers = new HashMap<>();
    public final Set<UUID> sleepingPlayers = new HashSet<>();
    private final Set<UUID> notActivePlayers;

    private boolean isVotingActive = false;
    private final MessageUtil message;
    private final Plugin plugin;
    private final BossBarManager voteBar;

    public VoteManager(Set<UUID> notActivePlayers, MessageUtil message, Plugin plugin, BossBarManager voteBar) {
        this.notActivePlayers = notActivePlayers;
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
        if (sleepingPlayers.contains(player) || notActivePlayers.contains(player) ||
            Objects.requireNonNull(Bukkit.getPlayer(player)).getGameMode() == GameMode.CREATIVE) return;

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
            voteBar.remove();
        }
    }

    public boolean isPlayerVoted(UUID player) {
        return votedPlayers.containsKey(player);
    }

    public void start() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR ||
                    PlaceholderAPI.setPlaceholders(player, "%afkplus_afk%").equalsIgnoreCase("true")
            ) {
                notActivePlayers.add(player.getUniqueId());
            }
        }
        isVotingActive = true;
        voteBar.create();
        votedPlayers.clear();
        message.send("Началось голосование за пропуск ночи!");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!sleepingPlayers.contains(player.getUniqueId()) && !notActivePlayers.contains(player.getUniqueId())) {
                message.sendToPlayer(Component.text("Желаешь ли ты пропустить эту ночь? ").append(
                        Component.text("[Да] ").clickEvent(ClickEvent.runCommand("/skipnight yes")).color(NamedTextColor.GREEN)
                                .hoverEvent(HoverEvent.showText(Component.text("Проголосовать за пропуск ночи")))).append(
                        Component.text("[Нет]").clickEvent(ClickEvent.runCommand("/skipnight no")).color(NamedTextColor.RED)
                                .hoverEvent(HoverEvent.showText(Component.text("Проголосовать против пропуска ночи")))),
                        player.getUniqueId());
            }
        }
    }

    public void vote(UUID uuid, boolean res) {
        votedPlayers.put(uuid, res);
        message.sendToPlayer("Твой голос учтён. Спасибо за участие в опросе!", uuid);
        double percentage = (double) plugin.getConfig().getInt("playerSleepingPercentage") / 100;
        int votedYes = 0;
        int votedNo = 0;
        for (boolean votedPlayer : votedPlayers.values()) {
            if (votedPlayer) {
                votedYes++;
            } else {
                votedNo++;
            }
        }
        int activePlayers = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!notActivePlayers.contains(player.getUniqueId())) {
                activePlayers++;
            }
        }
        voteBar.update(votedYes, activePlayers);
        if ((double) votedYes / activePlayers >= percentage) {
            finish(true);
        } else if ((double) votedNo / activePlayers >= percentage) {
            finish(false);
        }
    }

    public void finish(boolean skip) {
        isVotingActive = false;
        voteBar.remove();
        notActivePlayers.clear();
        if (skip) {
            message.send("Голосование завершено. Скоро наступит утро!");
            Objects.requireNonNull(Bukkit.getWorld("world")).setTime(0);
        } else {
            message.send("Голосование завершено в пользу того, что ночь пройдёт как обычно и не будет пропущена.");
        }
    }
}