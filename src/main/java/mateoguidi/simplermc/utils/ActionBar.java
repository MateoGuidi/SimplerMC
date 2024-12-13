package mateoguidi.simplermc.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {

    private final PacketPlayOutChat packet;

    //Create line of text above the hotbar
    public ActionBar(String text) {
        this.packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
    }

    //Send the text to a specific player
    public void sendToPlayer(Player p) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    //Send the text to all players
    public void sendToAll() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            ;
        }
    }

}
