package com.outofmana.coralpreserver;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

final class ReloadCommand implements CommandExecutor, TabCompleter {
    private final CoralPreserverPlugin plugin;

    ReloadCommand(CoralPreserverPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) return false;
        if (!sender.hasPermission("coralpreserver.reload")) {
            sender.sendMessage("§cYou do not have permission to reload CoralPreserver.");
            return true;
        }

        plugin.reloadSettings();
        sender.sendMessage("§aCoralPreserver configuration reloaded.");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                 @NotNull String alias, @NotNull String[] args) {
        if (!sender.hasPermission("coralpreserver.reload")) return List.of();
        if (args.length == 1 && "reload".startsWith(args[0].toLowerCase(Locale.ROOT))) {
            return List.of("reload");
        }
        return List.of();
    }
}
