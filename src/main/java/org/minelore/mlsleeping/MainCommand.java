package org.minelore.mlsleeping;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        if (command.getName().equalsIgnoreCase("sleep")) {
            if (!sender.hasPermission("sleep")) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("" +
                        " <red>У вас недостаточно прав для использования этой команды!"
                ));
                return true;
            }
//            if(args.length != 1) {
//                sender.sendMessage(MiniMessage.miniMessage().deserialize("" +
//                        " <yellow>Использование команды: /hotnether <enable | disable | status>"
//                ));
//                return true;
//            }
//            switch (args[0].toLowerCase(Locale.ROOT)) {
//                case "enable":
//                    hotNether.enablePlugin();
//                    sender.sendMessage(MiniMessage.miniMessage().deserialize("" +
//                            " <green>Плагин активирован!"
//                    ));
//                    return true;
//                case "disable":
//                    hotNether.disablePlugin();
//                    sender.sendMessage(MiniMessage.miniMessage().deserialize("" +
//                            " <red>Плагин деактивирован!"
//                    ));
//                    return true;
//                case "status":
//                    sender.sendMessage(MiniMessage.miniMessage().deserialize("" +
//                            " Состояние плагина: " + (hotNether.isEnable() ? "<green>Активен" : "<red>Неактивен")
//                    ));
//                    return true;
//                default:
//                    sender.sendMessage(MiniMessage.miniMessage().deserialize("" +
//                            " <yellow>Использование команды: /hotnether <enable | disable | status>"
//                    ));
//                    return true;
        } else return false;

        // костыль
        return false;
    }
}
