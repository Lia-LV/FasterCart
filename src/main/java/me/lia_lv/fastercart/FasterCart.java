package me.lia_lv.fastercart;

import com.osiris.dyml.exceptions.*;
import me.lia_lv.fastercart.commands.CommandManager;
import me.lia_lv.fastercart.config.DefaultConfig;
import me.lia_lv.fastercart.languages.LocaleConfig;
import me.lia_lv.fastercart.listener.VehicleExitListener;
import me.lia_lv.fastercart.listener.VehicleMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class FasterCart extends JavaPlugin {

    protected static FasterCart instance;
    public String consolePrefix;
    public boolean isPluginLoaded = false;
    protected PlatformManager platformManager;
    private PlatformManager.Platforms serverPlatform;
    protected DefaultConfig configManager;
    protected LocaleConfig localeManager;
    protected CommandManager commandManager;

    public FasterCart() {
        this.consolePrefix = ChatColor.translateAlternateColorCodes('&', "&b&l[FasterCart] ") + ChatColor.RESET;
    }

    public static FasterCart getInstance() {
        return instance;
    }

    public void consoleLogger(String str) {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(this.consolePrefix + ChatColor.translateAlternateColorCodes('&', str));
    }

    public PlatformManager getPlatformManager() {
        if (platformManager == null) {
            this.platformManager = new PlatformManager(this);
        }
        return this.platformManager;
    }

    public CommandManager getCommandManager() {
        if (commandManager == null) {
            this.commandManager = new CommandManager(this);
        }
        return this.commandManager;
    }

    public DefaultConfig getConfigManager() {
        if (configManager == null) {
            this.configManager = new DefaultConfig(this);
        }
        return this.configManager;
    }

    public LocaleConfig getLocaleManager() {
        if (localeManager == null) {
            this.localeManager = new LocaleConfig(this);
        }
        return this.localeManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        double serverVersion = this.getPlatformManager().getServerVersion();

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

        this.getCommand("fastercart").setExecutor(this.getCommandManager());
        //this.getCommand("fastercart").setTabCompleter(this.getTabCompleter());

        this.loadConfig(this.isPluginLoaded);
        this.consoleLogger("&eConfig files loaded.");

        this.registerListeners();
        this.consoleLogger("&eListener enabled.");

        this.isPluginLoaded = true;
        this.consoleLogger("&ePlugin has been enabled successfully.");
    }

    private void registerListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        pluginManager.registerEvents(new VehicleMoveListener(this), this);
        pluginManager.registerEvents(new VehicleExitListener(), this);
    }

    public boolean loadConfig(boolean reload) {
        try {
            this.getConfigManager().load();
            this.getLocaleManager().load();
        } catch (NotLoadedException | YamlReaderException | IOException | IllegalKeyException | DuplicateKeyException | IllegalListException | YamlWriterException ex) {
            ex.printStackTrace();
            this.consoleLogger("&cThere was a problem loading the config files.");
            this.consoleLogger("&cPlease check there if there are any modifications.");
            this.consoleLogger("&cIf nothing has been modified, please contact the developer.");
            return false;
        }

        if (reload) {
            this.consoleLogger(this.getLocaleManager().getConfigFileReloaded());
            this.consoleLogger(this.getLocaleManager().getLocaleFileReloaded());
        }

        return true;
    }

    @Override
    public void onDisable() {
        this.consoleLogger("&ePlugin has been disabled successfully.");
    }


}
