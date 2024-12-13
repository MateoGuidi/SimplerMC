package mateoguidi.simplermc.listeners;

import mateoguidi.simplermc.utils.TranslationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static mateoguidi.simplermc.utils.utils.prefix;

public class daytimeMessageListener {

    private final TranslationManager tm;
    private boolean isDay;

    public daytimeMessageListener(JavaPlugin plugin, TranslationManager manager) {
        tm = manager;
        World world = Bukkit.getWorlds().get(0);
        if (world != null) {
            long time = world.getTime();
            isDay = (time >= 0 && time < 12300);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                // Send a message everytime the world getting night or day
                World world = Bukkit.getWorlds().get(0);
                if (world == null) return;
                long time = world.getTime();
                if (time >= 0 && time < 12300) {
                    if (!isDay) {
                        isDay = true;
                        Bukkit.broadcastMessage(prefix + ChatColor.YELLOW + tm.getTranslation("itsDay"));
                    }
                } else if (time >= 12300 && time < 23850) {
                    if (isDay) {
                        isDay = false;
                        Bukkit.broadcastMessage(prefix + ChatColor.BLUE + tm.getTranslation("itsNight"));
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 100);
    }
}