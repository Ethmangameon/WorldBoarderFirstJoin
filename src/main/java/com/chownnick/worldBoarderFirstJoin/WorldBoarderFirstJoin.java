package com.chownnick.worldBoarderFirstJoin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class WorldBoarderFirstJoin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoinPlayerJoin(PlayerJoinEvent event) {
        var P = event.getPlayer();
        if (!P.hasPlayedBefore()) {
            getLogger().info("Player " + event.getPlayer().getName() + " joined the world for the first time, adding 250 to world boarder!");
            if (Bukkit.getVersionMessage().contains("Folia")) {
                BukkitRunnable r = new BukkitRunnable() {

                    @Override
                    public void run() {

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say A new player has joined, the world boarder will grow 250 blocks!");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder add 250");

                    }
                };
                Bukkit.getGlobalRegionScheduler().execute(this, r);
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say A new player has joined, the world boarder will grow 250 blocks!");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder add 250");
            }
        }
    }
}