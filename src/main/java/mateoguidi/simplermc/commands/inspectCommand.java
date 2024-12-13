package mateoguidi.simplermc.commands;

import mateoguidi.simplermc.utils.TranslationManager;
import mateoguidi.simplermc.utils.utils;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class inspectCommand implements CommandExecutor {
    private final TranslationManager tm;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Check if the command sender is not a Player
        if (!(sender instanceof Player)) {
            utils.error(sender, tm.getTranslation("onlyplayers"));
            return true;
        }

        //Get item in hand of command sender
        Player player = (Player) sender;
        ItemStack item = player.getItemInHand();

        //Check if item is not null or AIR
        if (item == null || item.getType().equals(Material.AIR)) {
            utils.error(sender, tm.getTranslation("noiteminhand"));
            return true;
        }

        //Get item metadata
        ItemMeta meta = item.getItemMeta();
        String customName = meta != null && meta.hasDisplayName() ? ChatColor.ITALIC + meta.getDisplayName() : null;
        String formattedName = utils.formatMaterialName(item.getType().name());
        String whichNameToShow = customName != null ? customName : formattedName;
        String amount = "";

        //Create lines for every aspect of item
        StringBuilder hoverTextBuilder = new StringBuilder();

        if (customName != null) {
            hoverTextBuilder.append(ChatColor.YELLOW).append(tm.getTranslation("name")).append(ChatColor.RESET).append(customName).append("\n");
        }

        hoverTextBuilder.append(ChatColor.AQUA).append(tm.getTranslation("type")).append(ChatColor.RESET).append(formattedName).append("\n");

        if (item.getAmount() > 1) {
            amount = " x" + item.getAmount();
            hoverTextBuilder.append(ChatColor.GREEN).append(tm.getTranslation("amount")).append(ChatColor.RESET).append(item.getAmount()).append("\n");
        }

        if (item.getEnchantments() != null && !item.getEnchantments().isEmpty()) {
            hoverTextBuilder.append(ChatColor.LIGHT_PURPLE).append(tm.getTranslation("enchantments"));
            for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();
                String enchantmentName = utils.getEnchantmentName(enchantment.getName(), tm.getLanguage());
                hoverTextBuilder.append("  - ").append(ChatColor.GOLD).append(enchantmentName).append(" ").append(utils.toRoman(level)).append("\n");
            }
        }

        NBTTagCompound nbt = CraftItemStack.asNMSCopy(item).getTag();
        if (nbt != null) {
            if (nbt.hasKey("AttributeModifiers")) {
                NBTTagList attributes = nbt.getList("AttributeModifiers", 10);
                for (int i = 0; i < attributes.size(); i++) {
                    NBTTagCompound attribute = attributes.get(i);
                    String attributeName = attribute.getString("AttributeName");
                    if (utils.attributeNamesEn.containsKey(attributeName)) {
                        attributeName = utils.getAttributeName(attributeName, tm.getLanguage());
                    }
                    int value = (int) attribute.getDouble("Amount");

                    hoverTextBuilder.append(ChatColor.BLUE).append(attributeName).append(": ").append(value).append("\n");
                }
            }
            if (nbt.hasKey("Unbreakable")) {
                hoverTextBuilder.append(ChatColor.BLUE).append(tm.getTranslation("unbreakable")).append("\n");
            }
        }

        hoverTextBuilder.append(ChatColor.GRAY).append("minecraft:").append(item.getType().toString().toLowerCase());

        //Create the message
        TextComponent message = new TextComponent(utils.prefix + player.getName() + tm.getTranslation("own"));
        TextComponent itemComponent = new TextComponent(net.md_5.bungee.api.ChatColor.AQUA + "[" + whichNameToShow + ChatColor.AQUA + amount + "]");
        itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverTextBuilder.toString()).create()));
        message.addExtra(itemComponent);

        //Send the message to all players
        player.spigot().sendMessage(message);
        return true;
    }


    public inspectCommand(TranslationManager manager) {
        tm = manager;
    }
}
