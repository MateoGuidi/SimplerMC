package mateoguidi.simplermc.commands;

import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class listCommand implements CommandExecutor {
    private final TranslationManager tm;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //Get all players and sort them by permissions
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        players.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        StringJoiner strOps = new StringJoiner(", ");
        StringJoiner strGuests = new StringJoiner(", ");
        for (Player player : players) {
            if (player.isOp()) {
                strOps.add(ChatColor.GOLD + player.getDisplayName());
            } else {
                strGuests.add(ChatColor.GRAY + player.getDisplayName());
            }
        }

        //Build the message
        String allPlayers = strOps.toString() + (strOps.length() > 0 && strGuests.length() > 0 ? ", " : "") + strGuests.toString();
        commandSender.sendMessage(utils.prefix + tm.getTranslation("list") + ChatColor.GRAY + " (" + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.YELLOW + Bukkit.getMaxPlayers() + ChatColor.GRAY + ")" + ChatColor.WHITE + " :");

        //Send the message to command sender
        commandSender.sendMessage(allPlayers);
        return true;
    }

    public listCommand(TranslationManager manager) {
        tm = manager;
    }
}
