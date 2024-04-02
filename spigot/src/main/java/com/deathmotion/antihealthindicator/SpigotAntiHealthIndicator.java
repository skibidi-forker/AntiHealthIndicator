package com.deathmotion.antihealthindicator;

import com.deathmotion.antihealthindicator.enums.ConfigOption;
import com.deathmotion.antihealthindicator.managers.ConfigManager;
import com.deathmotion.antihealthindicator.scheduler.SchedulerAdapter;
import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

@Getter
public class SpigotAntiHealthIndicator extends AHIPlatform<JavaPlugin> {
    private JavaPlugin plugin;

    private ConfigManager configManager;

    @Getter
    public class AHIPlugin extends JavaPlugin {
        private SpigotAntiHealthIndicator ahi;

        public void onLoad() {
            ahi = new SpigotAntiHealthIndicator();
            plugin = this;
            ahi.commonOnLoad();

            PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
            PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                    .checkForUpdates(false)
                    .bStats(true);

            PacketEvents.getAPI().load();
        }

        @Override
        public void onEnable() {
            new SchedulerAdapter();

            ahi.commonOnEnable();

            configManager = new ConfigManager(this);
            enableBStats();
        }

        @Override
        public void onDisable() {
            ahi.commonOnDisable();
        }

    }

    @Override
    public JavaPlugin getPlatform() {
        return this.plugin;
    }

    @Override
    public boolean hasPermission(UUID sender, String permission) {
        CommandSender commandSender = Bukkit.getPlayer(sender);
        if (commandSender == null) return false;

        return commandSender.hasPermission(permission);
    }

    @Override
    public boolean getConfigurationOption(ConfigOption option) {
        return this.configManager.getConfigurationOption(option);
    }

    @Override
    public String getPluginVersion() {
        return this.plugin.getDescription().getVersion();
    }

    private void enableBStats() {
        try {
            new Metrics(this.plugin, 20803);
        } catch (Exception e) {
            this.plugin.getLogger().warning("Something went wrong while enabling bStats.\n" + e.getMessage());
        }
    }
}