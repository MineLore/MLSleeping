package org.minelore.mlsleeping.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class MessageUtil {
    public void send(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            message = PlaceholderAPI.setPlaceholders(player, message);
            if (message.contains("&")){
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize(message));
            }
        }
    }
    public void sendToPlayer(String message, UUID uuid) {
        Player player = Objects.requireNonNull(Bukkit.getPlayer(uuid));
        message = PlaceholderAPI.setPlaceholders(player, message);
        if (message.contains("&")){
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
        } else {
            player.sendMessage(MiniMessage.miniMessage().deserialize(message));
        }
    }
}
