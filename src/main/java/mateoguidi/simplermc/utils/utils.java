package mateoguidi.simplermc.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class utils {

    public static TranslationManager tm = null;
    public static final String prefix = ChatColor.GOLD + "SMC " + ChatColor.GRAY + "| " + ChatColor.WHITE;
    public static final Map<String, String> enchantmentNamesEn = new HashMap<>();
    public static final Map<String, String> enchantmentNamesFr = new HashMap<>();
    public static final Map<String, String> attributeNamesEn = new HashMap<>();
    public static final Map<String, String> attributeNamesFr = new HashMap<>();

    //Fill enchantmentNames with all enchantments equivalents
    static {
        // Attributes in English
        attributeNamesEn.put("generic.maxHealth", "Max Health");
        attributeNamesEn.put("generic.movementSpeed", "Movement Speed");
        attributeNamesEn.put("generic.attackDamage", "Attack Damage");
        attributeNamesEn.put("generic.followRange", "Follow Range");
        attributeNamesEn.put("generic.knockbackResistance", "Knockback Resistance");
        attributeNamesEn.put("generic.armor", "Armor");
        attributeNamesEn.put("generic.armorToughness", "Armor Toughness");
        attributeNamesEn.put("generic.luck", "Luck");
        attributeNamesEn.put("generic.flyingSpeed", "Flying Speed");
        attributeNamesEn.put("generic.health", "Health");
        attributeNamesEn.put("generic.attackSpeed", "Attack Speed");
        attributeNamesEn.put("generic.reachDistance", "Reach Distance");

        // Attributes in French
        attributeNamesFr.put("generic.maxHealth", "Santé maximale");
        attributeNamesFr.put("generic.movementSpeed", "Vitesse de déplacement");
        attributeNamesFr.put("generic.attackDamage", "Dégâts d'attaque");
        attributeNamesFr.put("generic.followRange", "Portée de suivi");
        attributeNamesFr.put("generic.knockbackResistance", "Résistance au recul");
        attributeNamesFr.put("generic.armor", "Armure");
        attributeNamesFr.put("generic.armorToughness", "Robustesse de l'armure");
        attributeNamesFr.put("generic.luck", "Chance");
        attributeNamesFr.put("generic.flyingSpeed", "Vitesse de vol");
        attributeNamesFr.put("generic.health", "Santé");
        attributeNamesFr.put("generic.attackSpeed", "Vitesse d'attaque");
        attributeNamesFr.put("generic.reachDistance", "Distance de portée");

        // Enchantments in English
        enchantmentNamesEn.put("DAMAGE_ALL", "Sharpness");
        enchantmentNamesEn.put("DAMAGE_UNDEAD", "Smite");
        enchantmentNamesEn.put("DAMAGE_ARTHROPODS", "Bane of Arthropods");
        enchantmentNamesEn.put("KNOCKBACK", "Knockback");
        enchantmentNamesEn.put("FIRE_ASPECT", "Fire Aspect");
        enchantmentNamesEn.put("LOOT_BONUS_MOBS", "Looting");
        // Tools
        enchantmentNamesEn.put("DURABILITY", "Unbreaking");
        enchantmentNamesEn.put("LOOT_BONUS_BLOCKS", "Fortune");
        enchantmentNamesEn.put("SILK_TOUCH", "Silk Touch");
        enchantmentNamesEn.put("DIG_SPEED", "Efficiency");
        // Armors
        enchantmentNamesEn.put("PROTECTION_ENVIRONMENTAL", "Protection");
        enchantmentNamesEn.put("PROTECTION_FIRE", "Fire Protection");
        enchantmentNamesEn.put("PROTECTION_FALL", "Feather Falling");
        enchantmentNamesEn.put("PROTECTION_EXPLOSIONS", "Blast Protection");
        enchantmentNamesEn.put("PROTECTION_PROJECTILE", "Projectile Protection");
        enchantmentNamesEn.put("OXYGEN", "Respiration");
        enchantmentNamesEn.put("DEPTH_STRIDER", "Depth Strider");
        enchantmentNamesEn.put("AQUA_AFFINITY", "Aqua Affinity");
        // Bow
        enchantmentNamesEn.put("ARROW_DAMAGE", "Power");
        enchantmentNamesEn.put("ARROW_KNOCKBACK", "Punch");
        enchantmentNamesEn.put("ARROW_FIRE", "Flame");

        // Enchantments in French
        enchantmentNamesFr.put("DAMAGE_ALL", "Tranchant");
        enchantmentNamesFr.put("DAMAGE_UNDEAD", "Châtiment");
        enchantmentNamesFr.put("DAMAGE_ARTHROPODS", "Fléau des arthropodes");
        enchantmentNamesFr.put("KNOCKBACK", "Recul");
        enchantmentNamesFr.put("FIRE_ASPECT", "Aspect de feu");
        enchantmentNamesFr.put("LOOT_BONUS_MOBS", "Butin");
        // Tools
        enchantmentNamesFr.put("DURABILITY", "Solidité");
        enchantmentNamesFr.put("LOOT_BONUS_BLOCKS", "Fortune");
        enchantmentNamesFr.put("SILK_TOUCH", "Toucher de soie");
        enchantmentNamesFr.put("DIG_SPEED", "Efficacité");
        // Armors
        enchantmentNamesFr.put("PROTECTION_ENVIRONMENTAL", "Protection");
        enchantmentNamesFr.put("PROTECTION_FIRE", "Protection contre le feu");
        enchantmentNamesFr.put("PROTECTION_FALL", "Chute amortie");
        enchantmentNamesFr.put("PROTECTION_EXPLOSIONS", "Protection contre les explosions");
        enchantmentNamesFr.put("PROTECTION_PROJECTILE", "Protection contre les projectiles");
        enchantmentNamesFr.put("OXYGEN", "Respiration");
        enchantmentNamesFr.put("DEPTH_STRIDER", "Aptitude aquatique");
        enchantmentNamesFr.put("AQUA_AFFINITY", "Affinité aquatique");
        // Bow
        enchantmentNamesFr.put("ARROW_DAMAGE", "Puissance");
        enchantmentNamesFr.put("ARROW_KNOCKBACK", "Impact");
        enchantmentNamesFr.put("ARROW_FIRE", "Flamme");
    }

    // Get an enchantment by given name
    public static String getEnchantmentName(String internalName, String language) {
        if ("fr".equalsIgnoreCase(language)) {
            return enchantmentNamesFr.getOrDefault(internalName, internalName);
        }
        return enchantmentNamesEn.getOrDefault(internalName, internalName);
    }

    // Get an attribute by given name
    public static String getAttributeName(String internalName, String language) {
        if ("fr".equalsIgnoreCase(language)) {
            return attributeNamesFr.getOrDefault(internalName, internalName);
        }
        return attributeNamesEn.getOrDefault(internalName, internalName);
    }

    // Get id of material from given material
    public static String formatMaterialName(String material) {
        String name = material.toLowerCase();
        String[] words = name.split("_");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return formattedName.toString().trim();
    }

    // Convert number to Roman number from 1 to 10
    public static String toRoman(int number) {
        String[] romanNumerals = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        return (number >= 1 && number <= 10) ? romanNumerals[number] : String.valueOf(number);
    }

    //Check if a player is OP
    public static Boolean isAdmin(CommandSender sender) {
        if (!sender.isOp()) {
            error(sender, tm.getTranslation("onlyadmin"));
            return false;
        }
        return true;
    }

    //Check if a given args tab length is enough for number given
    public static Boolean checkNbArgs(String[] args, int numberToGet, String message, CommandSender sender) {
        if (args.length < numberToGet) {
            error(sender, tm.getTranslation("usage") + message);
            return false;
        } else {
            return true;
        }
    }

    // Send an error message to player
    public static void error(CommandSender sender, String message) {
        sender.sendMessage(prefix + ChatColor.RED + message);
    }

    //Get player's ping
    static Class<?> CPClass;
    static String serverName = Bukkit.getServer().getClass().getPackage().getName(), serverVersion = serverName.substring(serverName.lastIndexOf(".") + 1, serverName.length());
    public static int getPing(Player p) {
        try {
            CPClass = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftPlayer");
            Object CraftPlayer = CPClass.cast(p);

            Method getHandle = CraftPlayer.getClass().getMethod("getHandle", new Class[0]);
            Object EntityPlayer = getHandle.invoke(CraftPlayer, new Object[0]);

            Field ping = EntityPlayer.getClass().getDeclaredField("ping");

            return ping.getInt(EntityPlayer);
        } catch (Exception e) {
        }
        return 0;
    }

}
