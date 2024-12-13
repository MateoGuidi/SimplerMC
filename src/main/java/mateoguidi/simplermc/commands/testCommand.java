package mateoguidi.simplermc.commands;

import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class testCommand implements CommandExecutor {
    private final TranslationManager tm;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        //Check if command sender is OP
        if (!utils.isAdmin(commandSender)) {
            return true;
        }

        //Send a message to command sender
        commandSender.sendMessage(utils.prefix + tm.getTranslation("smcis") + ChatColor.GREEN + tm.getTranslation("up") + ChatColor.WHITE + ".");
        return true;
    }

    public testCommand(TranslationManager manager) {
        tm = manager;
    }
}
