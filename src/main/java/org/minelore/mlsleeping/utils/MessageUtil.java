package org.minelore.mlsleeping.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class MessageUtil {
    public void send(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    PlaceholderAPI.setPlaceholders(player, message)));
        }
    }
    public void sendToPlayer(String message, UUID uuid) {
        Player player = Objects.requireNonNull(Bukkit.getPlayer(uuid));
        player.sendMessage(MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(player, message)));
    }
}
