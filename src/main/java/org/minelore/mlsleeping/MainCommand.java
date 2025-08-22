package org.minelore.mlsleeping;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class MainCommand implements CommandExecutor {
    private final MLSleeping mlSleeping;

    public MainCommand(MLSleeping mlSleeping) {
        this.mlSleeping = mlSleeping;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        if (command.getName().equalsIgnoreCase("sleep")) {
            if (!sender.hasPermission("sleep")) {
                mlSleeping.sendMessage("<red>У вас недостаточно прав для использования этой команды!");
                return true;
            }
            if(args.length != 1) {
                mlSleeping.sendMessage("<yellow>Использование команды: /sleep <yes | no>");
                return true;
            }
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "yes":
                    // робим
                    return true;
                case "no":
                    // робим
                    return true;
                default:
                    mlSleeping.sendMessage("<yellow>Использование команды: /sleep <yes | no>");
                    return true;
            }
        } else return false;
    }
}
