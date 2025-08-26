package _4iz.AdminToolkit.Commands;

import _4iz.AdminToolkit.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AdminChat implements CommandExecutor {

    private final Main plugin;

    public AdminChat(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!command.getName().equalsIgnoreCase("admintoolchat")) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Из консоли нет доступа");
            return true;
        }

        if (!player.hasPermission("admintoolkit.chat")) {
            sender.sendMessage(Objects.requireNonNull(
                    plugin.getConfig().getString("no-permission")));
            return true;
        }

        if (strings.length == 0) {
            player.sendMessage(Objects.requireNonNull(
                    plugin.getConfig().getString("chat-admin-use")));
            return true;
        }

        String message = String.join(" ", strings);
        String formattedMessage = Objects.requireNonNull(
                        plugin.getConfig().getString("chat-format"))
                .replace("%player%", player.getName())
                .replace("%message%", message);

        // Отправляем сообщение всем игрокам с разрешением
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("admintoolchat.use")) {
                onlinePlayer.sendMessage(formattedMessage);
            }
        }




        return true;
    }
}