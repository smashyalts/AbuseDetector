package net.craftsupport.abusedetector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public final class AbuseDetector extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
public void onInventoryEvent (InventoryCreativeEvent event) throws IOException {
        if (event.getViewers().get(0) instanceof Player player && !player.hasPermission("AbuseDetector.bypass") && !event.getCursor().getType().equals(Material.AIR)) {
            if (event.getCursor().hasItemMeta()) {
                String message = player.getDisplayName() + " has taken: " + event.getCursor().getType() + " x " + event.getCursor().getAmount() + " NBT: " + event.getCursor().getItemMeta() + " from the creative mode menu";
                log(message);
                for (Player onlineplayer: Bukkit.getOnlinePlayers()) {
                    if (onlineplayer.hasPermission("AbuseDetector.Alert")) {
                        player.sendMessage(message);
            }
                }
                }
            String message = player.getDisplayName() + " has taken: " + event.getCursor().getType() + " x " + event.getCursor().getAmount() + " from the creative mode menu";
            log(message);
            for (Player onlineplayer: Bukkit.getOnlinePlayers()) {
                if (onlineplayer.hasPermission("AbuseDetector.Alert")) {
                    player.sendMessage(message);
                }
            }

}}

    public void log(String message) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.getDataFolder().getPath() + "/LOGS", true));
            writer.write(message);
            writer.newLine();
            writer.close();
        }
        catch (IOException e) {
            this.getLogger().info("An error occurred: " + e.getMessage());
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
