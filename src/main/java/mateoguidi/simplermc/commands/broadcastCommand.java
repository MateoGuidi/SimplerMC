package mateoguidi.simplermc.commands;

import mateoguidi.simplermc.utils.TranslationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static mateoguidi.simplermc.utils.utils.*;

public class broadcastCommand implements CommandExecutor {
    public final TranslationManager tm;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if command sender is OP
        if (!isAdmin(commandSender)) {
            return true;
        }

        // Check if the command contains a message
        if (!checkNbArgs(strings, 1, tm.getTranslation("usageBroadcast"), commandSender)) {
            return true;
        }

        // Build the whole message
        StringBuilder message = new StringBuilder();
        for (String string : strings) {
            message.append("§l").append(string).append(" ");
        }

        // Send the message to all players
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.DARK_RED + "§lAdmin " + ChatColor.GOLD + "§l" + commandSender.getName() + ChatColor.GRAY + " » " + ChatColor.WHITE + message);
        Bukkit.broadcastMessage("");
        return true;
    }

    public broadcastCommand(TranslationManager manager) {
        tm = manager;
    }
}
