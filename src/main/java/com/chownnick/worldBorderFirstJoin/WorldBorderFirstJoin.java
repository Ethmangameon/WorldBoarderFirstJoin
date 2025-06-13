package com.chownnick.worldBorderFirstJoin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class WorldBorderFirstJoin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveResource("config.yml", /* replace */ false);
    }

    @EventHandler
    public void onJoinPlayerJoin(PlayerJoinEvent event) {
        Player P = event.getPlayer();
        if (!P.hasPlayedBefore()) {

            BukkitRunnable r = new BukkitRunnable() {

                    @Override
                    public void run() {

                        int configSize = getConfig().getInt("AmountToGrow.size");
                        int configMultiplier = getConfig().getInt("AmountToGrow.multiplier");
                        int configSpeed = getConfig().getInt("AmountToGrow.speed");

                        World world = Bukkit.getWorld("world");
                        assert world != null;
                        WorldBorder border = world.getWorldBorder();
                        int borderSize = (int) border.getSize();
                        int sum = borderSize + (configMultiplier * configSize);
                        border.setSize(sum, configSpeed);

                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage("A new player has joined (" + P.getName() + "), the world border will grow " + (configMultiplier * configSize) + " blocks!");
                            p.sendMessage("The world border is now " + sum + " blocks wide!");
                        }
                    }
                };
            if (Bukkit.getVersionMessage().contains("Folia")) {
                Bukkit.getGlobalRegionScheduler().execute(this, r);
            } else {
                Bukkit.getScheduler().getMainThreadExecutor(this).execute(r);
            }
        }
    }
}