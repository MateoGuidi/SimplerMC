package mateoguidi.simplermc.events;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        //Change all player messages
        String message = event.getMessage();
        Player sender = event.getPlayer();
        String playername = sender.getDisplayName();
        // If a player got mentionned, add a @ before his name and send his a notification sound
        for (Player recipient : event.getRecipients()) {
            if (message.contains(recipient.getName())) {
                message = message.replaceAll("(?i)\\b" + recipient.getName() + "\\b", ChatColor.AQUA + "@" + recipient.getName() + ChatColor.WHITE);
                recipient.playSound(recipient.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
            }
        }
        // Send the player message with differences if the player is OP
        if (sender.isOp()) {
            event.setFormat(ChatColor.GOLD + playername + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + message);
        } else {
            event.setFormat(ChatColor.WHITE + playername + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + message);
        }

    }
}
