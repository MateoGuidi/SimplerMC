package mateoguidi.simplermc.commands;

import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class messageCommand implements CommandExecutor {
    private final TranslationManager tm;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        //Check if the command contains a player and a message
        if (!utils.checkNbArgs(strings, 2, tm.getTranslation("usageMessage"), commandSender)) {
            return true;
        }

        //Check if the player mentionned is connected
        Player cible = Bukkit.getPlayer(strings[0]);
        if (cible == null || !cible.isOnline()) {
            utils.error(commandSender, tm.getTranslation("notonline"));
            return true;
        }

        //Build the message
        StringBuilder message = new StringBuilder();
        for (int i = 1; i < strings.length; i++) {
            message.append(strings[i]).append(" ");
        }
        String messageFinal = message.toString().trim();

        //Send the messages to both players and makes a notification sound to target player
        commandSender.sendMessage(utils.prefix + ChatColor.BLUE + cible.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.AQUA + "@" + commandSender.getName() + ChatColor.GRAY + " : " + ChatColor.WHITE + messageFinal);
        cible.sendMessage(utils.prefix + ChatColor.AQUA + "@" + commandSender.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.BLUE + cible.getName() + ChatColor.GRAY + " : " + ChatColor.WHITE + messageFinal);
        cible.playSound(cible.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
        return true;
    }

    public messageCommand(TranslationManager manager) {
        tm = manager;
    }
}
