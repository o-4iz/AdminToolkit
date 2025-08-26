package _4iz.AdminToolkit.Commands;

import _4iz.AdminToolkit.Main;
import litebans.api.*;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Check implements CommandExecutor {

    private final Main plugin;

    public Check(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1 || !args[0].equalsIgnoreCase("check")) {
            sender.sendMessage(
                    plugin.getConfig().getString("error-use-command",
                            "§4Использование: /at check <on | off | yes | no> [игрок]"));
            return true;
        }
// /at args[0] = check args[1] = <on | off | yes | no> args[2] = [player]
        // Проверка количества аргументов < 3
        if (args.length < 2) {
            sender.sendMessage(plugin.getConfig().getString("error-use-command",
                    "§4Использование: /at check <on | off | yes | no> [игрок]"));
            return true;
        }

        Player target = getTargetPlayer(sender, args);
        if (target == null) {
            return true;
        }

        if (args[1].equalsIgnoreCase("onDiscord")) {
// Вызов метода isSelfTarget
            if (isSelfTarget(sender, target)) {
                sender.sendMessage(plugin.getConfig().getString(
                        "on-my-own", "§cВы не можете использовать эту команду на самом себе"));
                return true;
            }
// Вызов метода handleFreeze
            if (handleFreeze(sender, target)) return true;
            // Отключение урона, коллизии, подбор предметов
            plugin.disablePlayerDamage(target);
// Сообщения ----------------------------------------------------------------------------
            plugin.frozenPlayer(target);
            target.playSound(target.getLocation(),
                    plugin.getConfig().getString("sound-settings.type-sounds", "entity.experience_orb.pickup"),
                    plugin.getConfig().getInt("sound-settings.volume", 1),
                    plugin.getConfig().getInt("sound-settings.height", 1));

            sender.sendMessage(plugin.getConfig().getString(
                    "messages.freezing", "§aИгрок заморожен!"));

            target.sendTitle(
                    plugin.getConfig().getString("title-settings.sand-title", "§cПРОВЕРКА"),
                    plugin.getConfig().getString("title-settings.send-title-next", "§7Смотрите в чат!"),
                    plugin.getConfig().getInt("title-settings.appearance", 10),
                    plugin.getConfig().getInt("title-settings.show", 100),
                    plugin.getConfig().getInt("title-settings.disappearance", 20)
            );

            List<String> messageLines = plugin.getConfig().getStringList(
                    "messages.manualDiscord");

            // Обработчик сообщения (чтобы сообщение было не в 1 строку)
            for (String line : messageLines) {
                if (line != null && !line.trim().isEmpty()) {
                    String formattedLine = ChatColor.translateAlternateColorCodes('&', line);
                    target.sendMessage(formattedLine);
                } else {
                    target.sendMessage("");
                }
            }
// Обработчик команды onAnyDesk
        } else if (args[1].equalsIgnoreCase("onAnyDesk")) {

            // Вызов метода isSelfTarget
            if (isSelfTarget(sender, target)) {
                sender.sendMessage(plugin.getConfig().getString(
                        "on-my-own", "§cВы не можете использовать эту команду на самом себе"));
                return true;
            }
            // Вызов метода handleFreeze
            if (handleFreeze(sender, target)) return true;
            // Отключение урона, коллизии, подбор предметов
            plugin.disablePlayerDamage(target);
            plugin.frozenPlayer(target);

            // Звуки -----------------------------------------------------------------------------------
            target.playSound(target.getLocation(),
                    plugin.getConfig().getString("sound-settings.type-sounds", "entity.experience_orb.pickup"),
                    plugin.getConfig().getInt("sound-settings.volume", 1),
                    plugin.getConfig().getInt("sound-settings.height", 1)
            );
// Сообщения ----------------------------------------------------------------------------

            sender.sendMessage(plugin.getConfig().getString(
                    "messages.freezing", "§aИгрок заморожен!"));

            target.sendTitle(
                    plugin.getConfig().getString("title-settings.sand-title", "§cПРОВЕРКА"),
                    plugin.getConfig().getString("title-settings.send-title-next", "§7Смотрите в чат!"),
                    plugin.getConfig().getInt("title-settings.appearance", 10),
                    plugin.getConfig().getInt("title-settings.show", 100),
                    plugin.getConfig().getInt("title-settings.disappearance", 20)
            );

            List<String> messageLines = plugin.getConfig().getStringList("messages.manualonAnyDesk");

            // Обработчик сообщения (чтобы сообщение было не в 1 строку)
            for (String line : messageLines) {
                if (line != null && !line.trim().isEmpty()) {
                    String formattedLine = ChatColor.translateAlternateColorCodes('&', line)
                            .replace("{player}", target.getName())
                            .replace("{sender}", sender.getName());
                    target.sendMessage(formattedLine);
                } else {
                    target.sendMessage("");
                }
            }
// Обработчик команды off
        } else if (args[1].equalsIgnoreCase("off")) {
            // Вызов метода isSelfTarget
            if (isSelfTarget(sender, target)) {
                sender.sendMessage(plugin.getConfig().getString(
                        "on-my-own", "§cВы не можете использовать эту команду на самом себе"));
                return true;
            }
            // Вызов метода handleFreeze
            if (handleUnFreeze(sender, target)) return true;
            // Включение урона, коллизии, подбор предметов
            plugin.enablePlayerDamage(target);
            // Разморозка игрока
            plugin.unfrozenPlayer(target);
            // Сообщения ------------------------------------------------------
            sender.sendMessage(plugin.getConfig().getString(
                            "unfreezing", "§aВы разморозили игрока: {player}")
                    .replace("{player}", target.getName()));
// Обработчик команды yes
        } else if (args[1].equalsIgnoreCase("yes")) {
            // Вызов метода isSelfTarget
            if (isSelfTarget(sender, target)) {
                sender.sendMessage(plugin.getConfig().getString(
                        "on-my-own", "§cВы не можете использовать эту команду на самом себе"));
                return true;
            }
            // Вызов метода handleFreeze
            if (handleUnFreeze(sender, target)) return true;
            // Включение урона, коллизии, подбор предметов
            plugin.enablePlayerDamage(target);
            // Разморозка игрока
            plugin.unfrozenPlayer(target);
            // Сообщения -------------------------------------------------------
            sender.sendMessage(plugin.getConfig().getString(
                            "unfreezing", "§aВы разморозили игрока: {player}")
                    .replace("{player}", target.getName()));

            List<String> messageLines = plugin.getConfig().getStringList(
                    "messages.verification-completed");
            // Обработчик сообщения (чтобы сообщение было не в 1 строку)
            for (String line : messageLines) {
                if (line != null && !line.trim().isEmpty()) {
                    String formattedLine = ChatColor.translateAlternateColorCodes('&', line);
                    target.sendMessage(formattedLine);
                } else {
                    target.sendMessage("");
                }
            }
// Обработчик команды no
        } else if (args[1].equalsIgnoreCase("no")) {
            // Вызов метода isSelfTarget
            if (isSelfTarget(sender, target)) {
                sender.sendMessage(plugin.getConfig().getString(
                        "on-my-own", "§cВы не можете использовать эту команду на самом себе"));
                return true;
            }
            // Вызов метода handleFreeze
            if (handleUnFreeze(sender, target)) return true;
            // Включение урона, коллизии, подбор предметов
            plugin.enablePlayerDamage(target);
            // Разморозка игрока
            plugin.unfrozenPlayer(target);
            // Сообщения ------------------------------------------------------------------
            sender.sendMessage(plugin.getConfig().getString(
                            "ban-admin-messages", "§aВы заблокировали игрока: {player}")
                    .replace("{player}", target.getName()));
            // Блокировка игрока
            target.kickPlayer("§cВы заблокированы навсегда");
            Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(),
                    plugin.getConfig().getString(
                            "ban-reason", "По результатам проверки"), null, null);
        } else {
            sender.sendMessage(plugin.getConfig().getString(
                    "error-use-command", "§4Использование: /at check <on | off | yes | no> [игрок]"));
        }
        return true;
    }
    // Проверка аргумента с ником
    public Player getTargetPlayer(CommandSender sender, String[] args) {
        if (args.length >= 3) {
            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                sender.sendMessage(plugin.getConfig().getString(
                        "target-player", "§cИгрок не найден или offline!"));
                return null;
            }
            return target;
        } else if (sender instanceof Player) {
            return (Player) sender;
        } else {
            sender.sendMessage(
                    "§cКонсоль должна указать игрока: /at check <onDiscord | onAnydesk | off | yes | no> <игрок>");
        }
        return null;
    }
    // Проверка находится ли игрок на проверке
    private boolean handleFreeze(CommandSender sender, Player target) {
        if (plugin.isFrozen(target)) {
            sender.sendMessage(plugin.getConfig().getString(
                    "on-verification", "§cИгрок уже находится на проверке!").replace("{player}", target.getName()));
            return true;
        }
        plugin.isFrozen(target);
        return false;
    }
    // Проверка находится ли игрок на проверке
    private boolean handleUnFreeze(CommandSender sender, Player target) {
        if (!plugin.isFrozen(target)) {
            sender.sendMessage(plugin.getConfig().getString(
                    "not-on-verification", "§cИгрок не находится на проверке!").replace("{player}", target.getName()));
            return true;
        }
        return false;
    }
    // Проверка, чтобы админ сам себя не вызвал на проверку
    private boolean isSelfTarget(CommandSender sender, Player target) {
        // Консоль не может быть самой собой
        if (!(sender instanceof Player)) {
            return false;
        }

        Player senderPlayer = (Player) sender;
        return senderPlayer.getUniqueId().equals(target.getUniqueId());
    }
}