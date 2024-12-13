package mateoguidi.simplermc.listeners;

import mateoguidi.simplermc.SimplerMC;
import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import static org.bukkit.Bukkit.getServer;

public class playerDeathListener implements Listener {

    private final TranslationManager tm;
    private static SimplerMC plugin;

    public playerDeathListener(SimplerMC plugin, TranslationManager manager) {
        this.plugin = plugin;
        tm = manager;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent death) {
        Player killer = death.getEntity().getKiller();
        Player p = death.getEntity();
        Server server = getServer();
        EntityDamageEvent ede = p.getLastDamageCause();

        if (ede == null) return;

        EntityDamageEvent.DamageCause dc = ede.getCause();
        String category;
        String reason;

        // Determine the category and reason of death
        switch (dc) {
            case PROJECTILE:
                if (killer != null) {
                    if (killer == p) {
                        category = tm.getTranslation("suicide");
                        reason = tm.getTranslation("projectile_self");
                    } else {
                        category = tm.getTranslation("pvp");
                        reason = tm.getTranslation("projectile_player").replace("%killer%", killer.getDisplayName());
                    }
                } else {
                    category = tm.getTranslation("pve");
                    reason = tm.getTranslation("projectile_mob");
                }
                break;
            case ENTITY_ATTACK:
                if (killer != null) {
                    category = tm.getTranslation("pvp");
                    reason = tm.getTranslation("entity_attack_player").replace("%killer%", killer.getDisplayName());
                } else {
                    category = tm.getTranslation("pve");
                    reason = tm.getTranslation("entity_attack_mob");
                }
                break;
            case FALL:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("fall");
                break;
            case DROWNING:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("drowning");
                break;
            case LAVA:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("lava");
                break;
            case FIRE:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("fire");
                break;
            case FIRE_TICK:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("fire_tick");
                break;
            case ENTITY_EXPLOSION:
                category = tm.getTranslation("pve");
                reason = tm.getTranslation("entity_explosion");
                break;
            case LIGHTNING:
                category = tm.getTranslation("pve");
                reason = tm.getTranslation("lightning");
                break;
            case MAGIC:
                category = tm.getTranslation("potion");
                reason = tm.getTranslation("magic");
                break;
            case SUFFOCATION:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("suffocation");
                break;
            case STARVATION:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("starvation");
                break;
            case THORNS:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("thorns");
                break;
            case VOID:
                category = tm.getTranslation("suicide");
                reason = tm.getTranslation("void");
                break;
            case WITHER:
                category = tm.getTranslation("pve");
                reason = tm.getTranslation("wither");
                break;
            case SUICIDE:
                category = tm.getTranslation("command");
                reason = tm.getTranslation("suicide_command");
                break;
            default:
                category = tm.getTranslation("suicide");
                reason = ""; // Default reason
                break;
        }

        // Build the death message
        TextComponent deathMessage = new TextComponent(utils.prefix + p.getDisplayName() + tm.getTranslation("isdead") + ChatColor.GRAY + "(" + category + ")");
        BaseComponent[] hoverText = new ComponentBuilder(
                ChatColor.DARK_PURPLE + tm.getTranslation("player") + p.getDisplayName() + "\n")
                .append(ChatColor.GOLD + tm.getTranslation("reason") + reason + "\n")
                .append(ChatColor.RED + tm.getTranslation("deathmsg") + category).create();
        deathMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));

        // Send the death message to all players
        server.spigot().broadcast(deathMessage);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }
}
