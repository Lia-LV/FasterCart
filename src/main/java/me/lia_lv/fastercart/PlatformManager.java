package me.lia_lv.fastercart;

public class PlatformManager {

    private FasterCart plugin;

    public PlatformManager(FasterCart plugin) {
        this.plugin = plugin;
    }

    private Platforms getPlatform() {
        Platforms platform;

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            platform = Platforms.PAPER;
        } catch (ClassNotFoundException ex1) {
            try {
                Class.forName("org.spigotmc.SpigotConfig");
                platform = Platforms.SPIGOT;
            } catch (ClassNotFoundException ex2) {
                platform = Platforms.CRAFTBUKKIT;
            }
        }
        return platform;
    }

    public boolean isPaper() {
        return this.getPlatform().equals(Platforms.PAPER);
    }

    public boolean isSpigot() {
        return this.getPlatform().equals(Platforms.SPIGOT);
    }

    public boolean isCraftBukkit() {
        return this.getPlatform().equals(Platforms.CRAFTBUKKIT);
    }

    public double getServerVersion() {
        String version = this.plugin.getServer().getBukkitVersion().split("-")[0];
        String ver1 = version.split("\\.")[0] + "." + version.split("\\.")[1];
        return Double.parseDouble(ver1);
    }


    public enum Platforms {
        CRAFTBUKKIT("CraftBukkit"),
        SPIGOT("Spigot"),
        PAPER("Paper"),
        ;

        public final String friendlyName;

        Platforms(String friendlyName) {
            this.friendlyName = friendlyName;
        }

    }


}
