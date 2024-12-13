package mateoguidi.simplermc.listeners;

import mateoguidi.simplermc.SimplerMC;
import mateoguidi.simplermc.utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class playerBowedListener implements Listener {

    private static SimplerMC plugin;

    public playerBowedListener(SimplerMC plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        //Check if reason of damage is an arrow
        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            //Check if the shooter is a player
            if (arrow.getShooter() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                double health = 0;
                String name;
                // Get name of entity damaged
                if (event.getEntity() instanceof Player) {
                    Player damagedPlayer = (Player) event.getEntity();
                    health = Math.max(0, damagedPlayer.getHealth() - event.getFinalDamage());
                    name = damagedPlayer.getDisplayName();
                } else {
                    LivingEntity damagedEntity = (LivingEntity) event.getEntity();
                    health = Math.max(0, damagedEntity.getHealth() - event.getFinalDamage());
                    name = damagedEntity.getName();
                }
                //Format the message and send it to action bar of player damager
                String healthMessage = formatHealthMessage(name, health);
                ActionBar ab = new ActionBar(healthMessage);
                ab.sendToPlayer(shooter);
            }
        }
    }

    private String formatHealthMessage(String playerName, double health) {
        //Change color if health is low
        ChatColor color = ChatColor.GREEN;
        if (health <= 10) {
            color = ChatColor.YELLOW;
        }
        if (health <= 5) {
            color = ChatColor.RED;
        }
        return ChatColor.WHITE + playerName + ChatColor.DARK_GRAY + " » " + color + String.format("%.1f", health) + "❤";
    }
}
