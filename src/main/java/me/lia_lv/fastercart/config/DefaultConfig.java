package me.lia_lv.fastercart.config;

import com.osiris.dyml.exceptions.*;
import me.lia_lv.fastercart.FasterCart;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.io.IOException;

public class DefaultConfig {

    private final FasterCart plugin;
    private String PREFIX;
    private String LOCALE = "EN";
    private Material CART_ACCELERATION_ITEM_TYPE;
    private String CART_ACCELERATION_ITEM_NAME;
    private String CART_ACCELERATION_ITEM_HAND_TO_DETECT;
    private double ACCELERATION_PER_ITEM_AMOUNT;
    private int MAXIMUM_AMOUNT_OF_ITEM_APPLIED;

    public DefaultConfig(FasterCart plugin) {
        this.plugin = plugin;
    }

    public String getLocale() {
        return LOCALE;
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', PREFIX + "&r ");
    }

    public Material getCartAccelerationItemType() {
        return CART_ACCELERATION_ITEM_TYPE;
    }

    public String getCartAccelerationItemName() {
        return ChatColor.translateAlternateColorCodes('&', CART_ACCELERATION_ITEM_NAME);
    }

    public boolean isCartAccelItemNameIgnore() {
        return CART_ACCELERATION_ITEM_NAME.equalsIgnoreCase("DEFAULT_NAME");
    }

    public String getHandToDetect() {
        return CART_ACCELERATION_ITEM_HAND_TO_DETECT;
    }

    public double getAccelerationPerItemAmount() {
        return ACCELERATION_PER_ITEM_AMOUNT;
    }

    public int getMaximumAmountOfItemApplied() {
        return MAXIMUM_AMOUNT_OF_ITEM_APPLIED;
    }

    public void load() throws NotLoadedException, YamlReaderException, IOException, IllegalKeyException, DuplicateKeyException, IllegalListException, YamlWriterException {
        Configuration config = new Configuration(FasterCart.getInstance(), "config.yml");

        LOCALE = config.get("Locale", "EN", 0, "Plugin language settings.", "Locale file name must be \"Locale_OO\"! (ex. Locale_EN)").toUpperCase();
        PREFIX = config.get("Prefix", "&9[FasterCart]", 1);
        config.addCommentsOnly("Cart-acceleration-item", 1,
                "Cart speed increases when the player holds the item with the name below.",
                "If item name is \"DEFAULT_NAME\", consider only the item type.",
                "Item name is CASE SENSITIVE!");
        CART_ACCELERATION_ITEM_TYPE = config.get("Cart-acceleration-item.Type", Material.LEVER, 0);
        CART_ACCELERATION_ITEM_NAME = config.get("Cart-acceleration-item.Name", "&9&lCart Accelerator", 0);
        CART_ACCELERATION_ITEM_HAND_TO_DETECT = config.get("Cart-acceleration-item.Hand-To-Detect", "OFF_HAND", 0, "Set the hand to detect the item.", "Must be either \"MAIN_HAND\" or \"OFF_HAND\"!");
        ACCELERATION_PER_ITEM_AMOUNT = config.get("Acceleration-per-item-amount", 0.01, 1, "Cart speed to be increased per amount of acceleration item the player is holding.");
        MAXIMUM_AMOUNT_OF_ITEM_APPLIED = config.get("Maximum-amount-of-item-applied", 64, 0, "Holding above this value does not increase boat speed.");


        config.save(true);
    }


}
