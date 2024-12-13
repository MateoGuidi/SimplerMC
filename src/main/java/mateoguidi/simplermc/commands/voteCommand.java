package mateoguidi.simplermc.commands;

import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class voteCommand implements CommandExecutor {

    private final TranslationManager tm;
    private boolean isVoteActive = false;
    private final Map<Player, Boolean> votes = new HashMap<>();
    private String currentQuestion = "";
    private final JavaPlugin plugin;

    public voteCommand(JavaPlugin plugin, TranslationManager manager) {
        this.plugin = plugin;
        tm = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Check if the command contains a question
        if (!utils.checkNbArgs(args, 1, tm.getTranslation("usageVote"), sender)) return true;

        //Handle answer buttons
        if (args.length == 1 && (args[0].equalsIgnoreCase("oui") || args[0].equalsIgnoreCase("non"))) {
            handleVote(sender, args[0].equalsIgnoreCase("oui"));
            return true;
        }

        //Check if a vote is already started, otherwise starts a question
        if (isVoteActive) {
            utils.error(sender, tm.getTranslation("alreadyQuestion"));
        } else {
            startVote(sender, String.join(" ", args));
        }

        return true;
    }

    //Handle vote command
    private void handleVote(CommandSender sender, boolean isYesVote) {

        Player player = (Player) sender;
        if (!isVoteActive) {
            utils.error(player, tm.getTranslation("novoteyet"));
            return;
        }

        if (votes.containsKey(player)) {
            utils.error(player, tm.getTranslation("alreadyvoted"));
            return;
        }

        ChatColor answerColor = isYesVote ? ChatColor.GREEN : ChatColor.RED;
        votes.put(player, isYesVote);
        player.sendMessage(utils.prefix + tm.getTranslation("yourvote") + answerColor + "\"" + (isYesVote ? tm.getTranslation("true") : tm.getTranslation("false")) + "\"" + ChatColor.WHITE + tm.getTranslation("beenregistered"));
    }

    //Start the vote with a question
    private void startVote(CommandSender sender, String question) {
        currentQuestion = question;
        isVoteActive = true;
        votes.clear();

        Bukkit.broadcastMessage(utils.prefix + tm.getTranslation("questionstarted") + ChatColor.YELLOW + "\"" + currentQuestion + "\"");

        TextComponent voteMessage = createVoteMessage();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(voteMessage);
        }

        Bukkit.getScheduler().runTaskLater(plugin, this::endVote, 20 * 20);
    }

    //Create the message to vote YES/NO
    private TextComponent createVoteMessage() {
        TextComponent voteMessage = new TextComponent(utils.prefix + ChatColor.AQUA + tm.getTranslation("clickforanswer"));

        TextComponent yesOption = createVoteOption(ChatColor.GREEN, tm.getTranslation("true"), "/vote oui", tm.getTranslation("clickforyes"));
        TextComponent noOption = createVoteOption(ChatColor.RED, tm.getTranslation("false"), "/vote non", tm.getTranslation("clickforno"));

        voteMessage.addExtra(yesOption);
        voteMessage.addExtra(" ");
        voteMessage.addExtra(noOption);

        return voteMessage;
    }

    //Create clickable button
    private TextComponent createVoteOption(ChatColor color, String label, String command, String hoverText) {
        TextComponent option = new TextComponent(color + "[" + label + "]");
        option.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        option.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).color(color).create()));
        return option;
    }

    //Handle the end vote and send a message to all players with the vote result
    private void endVote() {
        isVoteActive = false;
        long yesVotes = votes.values().stream().filter(Boolean::booleanValue).count();
        long noVotes = votes.size() - yesVotes;

        Bukkit.broadcastMessage(utils.prefix + tm.getTranslation("results") + ChatColor.YELLOW + "\"" + currentQuestion + "\"");
        if (votes.isEmpty()) {
            Bukkit.broadcastMessage(utils.prefix + ChatColor.GRAY + tm.getTranslation("noonevoted"));
        } else {
            Bukkit.broadcastMessage(utils.prefix + ChatColor.GREEN + tm.getTranslation("finalyes") + yesVotes + " votes");
            Bukkit.broadcastMessage(utils.prefix + ChatColor.RED + tm.getTranslation("finalno") + noVotes + tm.getTranslation("votes"));
        }
    }
}