package mateoguidi.simplermc.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        // Change the message of "Player joined the server!" to simpler way
        Player player = e.getPlayer();
        e.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "] " + player.getDisplayName() + ChatColor.GRAY + " (" + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.YELLOW + Bukkit.getMaxPlayers() + ChatColor.GRAY + ")");
    }
}
