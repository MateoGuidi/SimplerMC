package mateoguidi.simplermc.commands;

import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static mateoguidi.simplermc.utils.utils.getPing;

public class pingCommand implements CommandExecutor {
    private final TranslationManager tm;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        //Check if command sender if a Player
        if (commandSender instanceof Player) {
            //Get ping of playe
            Player p = (Player) commandSender;
            int ping = getPing(p);
            //Set the color if ping is high
            ChatColor ms = ChatColor.RED;
            if (ping < 60) {
                ms = ChatColor.GREEN;
            } else if (ping > 60 && ping < 120) {
                ms = ChatColor.YELLOW;
            }
            //Send the message to command sender
            p.sendMessage(utils.prefix + tm.getTranslation("ping") + ms + ping + "ms" + ChatColor.WHITE + ".");
        }
        return true;
    }

    public pingCommand(TranslationManager manager) {
        tm = manager;
    }
}
