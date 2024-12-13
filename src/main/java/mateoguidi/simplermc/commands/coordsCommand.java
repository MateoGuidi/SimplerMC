package mateoguidi.simplermc.commands;

import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class coordsCommand implements CommandExecutor {
    private final TranslationManager tm;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        String dimension = "";
        ChatColor ccDimension = ChatColor.WHITE;

        // Set the World to show and his color
        switch (p.getLocation().getWorld().getEnvironment()) {
            case NORMAL:
                dimension = "overworld";
                ccDimension = ChatColor.GREEN;
                break;
            case NETHER:
                dimension = "nether";
                ccDimension = ChatColor.DARK_RED;
                break;
            case THE_END:
                dimension = "end";
                ccDimension = ChatColor.LIGHT_PURPLE;
                break;
        }
        //Send XYZ of command sender + his dimension to all players
        Bukkit.broadcastMessage(utils.prefix + ChatColor.WHITE + p.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.GOLD + "X: " + ChatColor.YELLOW + p.getLocation().getBlockX() + ChatColor.GOLD + " Y: " + ChatColor.YELLOW + p.getLocation().getBlockY() + ChatColor.GOLD + " Z: " + ChatColor.YELLOW + p.getLocation().getBlockZ() + ChatColor.WHITE + tm.getTranslation("inDimension") + ccDimension + tm.getTranslation(dimension));
        return true;
    }

    public coordsCommand(TranslationManager manager) {
        tm = manager;
    }
}
