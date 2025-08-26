package org.minelore.mlsleeping;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomQuoteExpansion extends PlaceholderExpansion {

    private final List<String> quotes;

    public RandomQuoteExpansion(JavaPlugin plugin) {
        this.quotes = plugin.getConfig().getStringList("quotes");
    }

    @Override public boolean persist() { return true; }          // оставляем expansion зарегистрированным
    @Override public boolean canRegister() { return true; }
    @Override public @NotNull String getAuthor() { return "CaHeK2009"; }
    @Override public @NotNull String getIdentifier() { return "mlsleep"; }
    @Override public @NotNull String getVersion() { return "0.9.0"; }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (identifier.equalsIgnoreCase("quote")) {
            if (quotes == null || quotes.isEmpty()) return "";
            int idx = ThreadLocalRandom.current().nextInt(quotes.size());
            return quotes.get(idx);
        }
//        Префикс перед каждым сообщением
//        else if (identifier.equalsIgnoreCase("prefix")) {
//            return "[<blue>MLSleep</blue>] ";
//        }
        return null; // не обрабатываем
    }
}
