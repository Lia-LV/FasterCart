package me.lia_lv.fastercart;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class FasterCart extends JavaPlugin {

    protected static FasterCart instance;
    public String consolePrefix;
    private BukkitAudiences adventure;
    public boolean isPluginLoaded = false;
    protected PlatformManager platformManager;
    private PlatformManager.Platforms serverPlatform;

    public FasterCart() {
        this.consolePrefix = ChatColor.translateAlternateColorCodes('&', "&b&l[FasterBoat] ") + ChatColor.RESET;
    }

    public @NotNull BukkitAudiences getAdventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public static FasterCart getInstance() {
        return instance;
    }

    public void consoleLogger(String str) {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(this.consolePrefix + ChatColor.translateAlternateColorCodes('&', str));
    }

    private double getServerVersion() {
        String version = this.getServer().getBukkitVersion().split("-")[0];
        String ver1 = version.split("\\.")[0] + "." + version.split("\\.")[1];
        return Double.parseDouble(ver1);
    }

    public PlatformManager getPlatformManager() {
        if (platformManager == null) {
            this.platformManager = new PlatformManager();
        }
        return this.platformManager;
    }

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        instance = this;
        double serverVersion = this.getServerVersion();

        if (this.getPlatformManager().isCraftBukkit()) {
            this.consoleLogger("&cThis plugin doesn't work with CraftBukkit!");
            this.consoleLogger("&cPlease change the server implementation to Spigot or Forked(ex. Paper).");
            this.consoleLogger("&cPlugin will be disabled.");
            this.setEnabled(false);
            return;
        } else if (this.platformManager.isPaper()) {
            this.serverPlatform = PlatformManager.Platforms.PAPER;
        } else if (this.platformManager.isSpigot()) {
            this.serverPlatform = PlatformManager.Platforms.SPIGOT;
        }

        if (serverVersion <= 1.12) {
            this.consoleLogger("&cFasterBoat doesn't support versions below 1.13!");
            this.consoleLogger("&cPlease upgrade the server to 1.13 or higher.");
            this.consoleLogger("&cPlugin will be disabled.");
            this.setEnabled(false);
            return;
        }

        this.consoleLogger("&eServer Info : &f&l" + this.serverPlatform.friendlyName + " " + serverVersion);


        this.isPluginLoaded = true;
        this.consoleLogger("&ePlugin has been enabled successfully.");
    }

    @Override
    public void onDisable() {
        this.closeAdventureAPI();

        this.consoleLogger("&ePlugin has been disabled successfully.");
    }

    private void closeAdventureAPI() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }


}
