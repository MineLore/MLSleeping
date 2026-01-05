package org.minelore.mlsleeping.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.minelore.mlsleeping.managers.VoteManager;
import org.minelore.mlsleeping.utils.MessageUtil;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class SkipNightCommand implements CommandExecutor {
    private final MessageUtil message;
    private final VoteManager voteManager;

    private final Set<UUID> notActivePlayers;

    public SkipNightCommand(MessageUtil message, VoteManager voteManager, Set<UUID> notActivePlayers) {
        this.message = message;
        this.voteManager = voteManager;
        this.notActivePlayers = notActivePlayers;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        if (command.getName().equalsIgnoreCase("skipnight")) {
            UUID uuid;
            if (sender instanceof Player player) {
                uuid = player.getUniqueId();
            } else {
                sender.sendMessage("Ты нелюдь!");
                return true;
            }
            if (!sender.hasPermission("skipnight")) {
                message.sendToPlayer("<red>У вас недостаточно прав для использования этой команды!", uuid);
                return true;
            }
            if(args.length != 1) {
                message.sendToPlayer("<yellow>Использование команды: /skipnight <yes | no>", uuid);
                return true;
            }
            if (voteManager.isPlayerVoted(uuid)) {
                message.sendToPlayer("<red>Вы уже проголосовали", uuid);
                return true;
            }
            if (!voteManager.isVotingActive()) {
                message.sendToPlayer("<red>Сейчас не проводится голосование!", uuid);
                return true;
            }
            if (notActivePlayers.contains(player.getUniqueId())) {
                message.sendToPlayer("<red>Вы не участвуете в голосовании!", uuid);
                return true;
            }
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "yes":
                    voteManager.vote(uuid, true);
                    return true;
                case "no":
                    voteManager.vote(uuid, false);
                    return true;
                default:
                    message.sendToPlayer("<yellow>Использование команды: /skipnight <yes | no>", uuid);
                    return true;
            }
        }
        return false;
    }
}
