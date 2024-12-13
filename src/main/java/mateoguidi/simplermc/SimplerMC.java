package mateoguidi.simplermc;

import mateoguidi.simplermc.commands.*;
import mateoguidi.simplermc.events.PlayerJoin;
import mateoguidi.simplermc.events.PlayerLeave;
import mateoguidi.simplermc.listeners.daytimeMessageListener;
import mateoguidi.simplermc.listeners.playerBowedListener;
import mateoguidi.simplermc.events.PlayerChat;
import mateoguidi.simplermc.listeners.playerDeathListener;
import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimplerMC extends JavaPlugin {
    public TranslationManager manager = new TranslationManager(getDataFolder());
    @Override
    public void onEnable() {
        System.out.println(manager.getTranslation("enabled"));
        utils.tm = manager;
        manager.listAllMessages();
        // Admin Commands
        getCommand("smc").setExecutor(new testCommand(manager));
        getCommand("broadcast").setExecutor(new broadcastCommand(manager));
        // Public Commands
        getCommand("vote").setExecutor(new voteCommand(this, manager));
        getCommand("list").setExecutor(new listCommand(manager));
        getCommand("coords").setExecutor(new coordsCommand(manager));
        getCommand("inspect").setExecutor(new inspectCommand(manager));
        // Private Commands
        getCommand("ping").setExecutor(new pingCommand(manager));
        getCommand("msg").setExecutor(new messageCommand(manager));
        // Listeners
        new playerDeathListener(this, manager);
        new playerBowedListener(this);
        new daytimeMessageListener(this, manager);
        // Events
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
    }

    @Override
    public void onDisable() {
        System.out.println(manager.getTranslation("disabled"));
    }
}