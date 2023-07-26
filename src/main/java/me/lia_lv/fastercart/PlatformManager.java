package me.lia_lv.fastercart;

public class PlatformManager {

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
