package com.chownnick.worldBorderFirstJoin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Objects;

public final class WorldBorderFirstJoin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoinPlayerJoin(PlayerJoinEvent event) {
        var P = event.getPlayer();
        if (!P.hasPlayedBefore()) {

            BukkitRunnable r = new BukkitRunnable() {

                    @Override
                    public void run() {

                        // Literal definition of "x"
                        int x = 250;

                        // Send announcement to players about changing world boarder
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage("A new player has joined (" + P.getName() + "), the world border will grow " + x + " blocks!");
                        }

                        // Add X to current world boarder
                        Objects.requireNonNull(Bukkit.getWorld("world"))
                                .getWorldBorder().setSize(
                                        Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder().getSize()
                                                + x);
                    }
                };
            // Check if server is running Folia and run the scheduler differently.
            if (Bukkit.getVersionMessage().contains("Folia")) {
                // Run if Folia
                Bukkit.getGlobalRegionScheduler().execute(this, r);
            } else {
                // Run if spigot/paper
                Bukkit.getScheduler().getMainThreadExecutor(this).execute(r);
            }
        }
    }
}