package _4iz.AdminToolkit;

import _4iz.AdminToolkit.Commands.AdminChatCommand;
import _4iz.AdminToolkit.Commands.CheckCommand;
import _4iz.AdminToolkit.Listener.CheckListener;
import _4iz.AdminToolkit.TabCompleter.AdminChatCompleter;
import _4iz.AdminToolkit.TabCompleter.CheckTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {

    private final Set<UUID> frozenPlayers = new HashSet<>();

    @Override
    public void onEnable() {

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new CheckListener(this), this);

        Objects.requireNonNull(getCommand(
                "admintoolkit")).setExecutor(
                new AdminChatCommand(this));

        Objects.requireNonNull(getCommand(
                "admintoolkit")).setExecutor(
                        new CheckCommand(this));

        Objects.requireNonNull(getCommand(
                "admintoolkit")).setTabCompleter(
                        new CheckTabCompleter());

        Objects.requireNonNull(getCommand(
                "admintoolchat")).setExecutor(
                        new AdminChatCommand(this));

        Objects.requireNonNull(getCommand(
                "admintoolchat")).setTabCompleter(
                        new AdminChatCompleter());


        getLogger().info("==========================");
        getLogger().info("Плагин " + this.getName() + " включен!");
        getLogger().info("Версия: " + this.getDescription().getVersion());
        getLogger().info("==========================");

    }

    @Override
    public void onDisable() {

        reloadConfig();
        saveConfig();

        getLogger().info("==========================");
        getLogger().info("Плагин " + this.getName() + " выключен!");
        getLogger().info("==========================");

    }

    public boolean isFrozen(Player player) {
        return frozenPlayers.contains(player.getUniqueId());
    }

    public void frozenPlayer(Player player) {
        frozenPlayers.add(player.getUniqueId());
        getLogger().info(
                "Заморожен: " + player.getName() + " UUID: " + player.getUniqueId());
    }

    public void unfrozenPlayer(Player player) {
        boolean removed = frozenPlayers.remove(player.getUniqueId());
        getLogger().info("Разморозка " + player.getName() +
                " UUID: " + player.getUniqueId() +
                " Успешно: " + removed +
                " Осталось в Set: " + frozenPlayers.size());
    }

    public Set<UUID> getFrozenPlayers(Player target) {
        return new HashSet<>(frozenPlayers);
    }

    public void disablePlayerDamage(Player player) {
        player.setInvulnerable(true);
        player.setCanPickupItems(false);
        player.setCollidable(false);
    }
    public void enablePlayerDamage(Player player) {

        player.setInvulnerable(false);
        player.setCanPickupItems(true);
        player.setCollidable(true);
    }

}
