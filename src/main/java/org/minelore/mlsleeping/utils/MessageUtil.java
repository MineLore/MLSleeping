package org.minelore.mlsleeping.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class MessageUtil {
    public void send(Object message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (message instanceof String str) {
                str = PlaceholderAPI.setPlaceholders(player, str);
                if (str.contains("&")){
                    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(str));
                } else {
                    player.sendMessage(MiniMessage.miniMessage().deserialize(str));
                }
            } else if (message instanceof Component comp) {
                if (comp.contains(Component.text("&"))){
                    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(LegacyComponentSerializer.legacyAmpersand().serialize(comp)));
                } else {
                    player.sendMessage(MiniMessage.miniMessage().deserialize(MiniMessage.miniMessage().serialize(comp)));
                }
            }
        }
    }
    public void sendToPlayer(Object message, UUID uuid) {
        Player player = Objects.requireNonNull(Bukkit.getPlayer(uuid));
        if (message instanceof String str) {
            str = PlaceholderAPI.setPlaceholders(player, str);
            if (str.contains("&")){
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(str));
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize(str));
            }
        } else if (message instanceof Component comp){
            if (comp.contains(Component.text("&"))){
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(LegacyComponentSerializer.legacyAmpersand().serialize(comp)));
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize(MiniMessage.miniMessage().serialize(comp)));
            }
        }
    }
}
