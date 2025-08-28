package _4iz.AdminToolkit.TabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckTabCompleter implements TabCompleter {
    private void addMatches(List<String> list, String input, String value) {
        if (value.toLowerCase().startsWith(input.toLowerCase(Locale.ROOT))) {
            list.add(value);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {

            addMatches(completions, args[0], "check");


        }else if (args.length == 2 && args[0].equalsIgnoreCase("check")) {

            String input = args[1].toLowerCase();

            addMatches(completions, args[1], "onDiscord");
            addMatches(completions, args[1], "onAnydesk");
            addMatches(completions, args[1], "off");
            addMatches(completions, args[1], "yes");
            addMatches(completions, args[1], "no");

        } else if (args.length == 3 && args[0].equalsIgnoreCase("check")) {

            String partialName = args[2].toLowerCase();

            for (Player player : Bukkit.getOnlinePlayers()){
                if (player.getName().toLowerCase().startsWith(partialName)) {
                    completions.add(player.getName());
                }
            }

        }
        completions.sort(String::compareToIgnoreCase);
        return completions;
    }
}
