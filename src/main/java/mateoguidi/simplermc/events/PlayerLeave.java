package mateoguidi.simplermc.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    void onPlayerLeave(PlayerQuitEvent e) {
        // Change the message of "Player leave the server!" to simpler way
        Player player = e.getPlayer();
        e.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "] " + player.getDisplayName() + ChatColor.GRAY + " (" + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.YELLOW + Bukkit.getMaxPlayers() + ChatColor.GRAY + ")");
    }
}
