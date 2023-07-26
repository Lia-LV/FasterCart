package me.lia_lv.fastercart;

import org.bukkit.plugin.java.JavaPlugin;

public class FasterCart extends JavaPlugin {

    protected static FasterCart instance;

    public static FasterCart getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
