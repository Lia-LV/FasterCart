package me.lia_lv.fastercart.listener;

import me.lia_lv.fastercart.FasterCart;
import me.lia_lv.fastercart.config.DefaultConfig;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VehicleMoveListener implements Listener {

    private final FasterCart plugin;

    public VehicleMoveListener(FasterCart plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
        if (!(event.getVehicle() instanceof Minecart)) {
            return;
        }
        Minecart cart = (Minecart) event.getVehicle();

        if (cart.getPassengers().isEmpty() || !(cart.getPassengers().get(0) instanceof Player)) {
            return;
        }
        Player p = (Player) cart.getPassengers().get(0);
        DefaultConfig config = this.plugin.getConfigManager();
        int amountOfPlayerIsHolding = 0;

        if (config.getHandToDetect().equalsIgnoreCase("MAIN_HAND")) {
            ItemStack mainHandItemStack = p.getInventory().getItemInMainHand();
            ItemMeta mainHandItemMeta = mainHandItemStack.getItemMeta();
            if (!mainHandItemStack.getType().equals(config.getCartAccelerationItemType())) {
                return;
            }

            if (!config.getCartAccelerationItemName().equalsIgnoreCase("DEFAULT_NAME")) {
                if (!mainHandItemMeta.getDisplayName().equals(config.getCartAccelerationItemName())) {
                    return;
                }
            }
            amountOfPlayerIsHolding = mainHandItemStack.getAmount();

        } else if (config.getHandToDetect().equalsIgnoreCase("OFF_HAND")) {
            ItemStack offHandItemStack = p.getInventory().getItemInOffHand();
            ItemMeta offHandItemMeta = offHandItemStack.getItemMeta();
            if (!offHandItemStack.getType().equals(config.getCartAccelerationItemType())) {
                return;
            }

            if (!config.getCartAccelerationItemName().equalsIgnoreCase("DEFAULT_NAME")) {
                if (!offHandItemMeta.getDisplayName().equals(config.getCartAccelerationItemName())) {
                    return;
                }
            }

            amountOfPlayerIsHolding = offHandItemStack.getAmount();
        }

        double velocityPerItemAmount = config.getAccelerationPerItemAmount();
        int maximumAmountLimit = config.getMaximumAmountOfItemApplied();
        if (amountOfPlayerIsHolding > maximumAmountLimit) {
            amountOfPlayerIsHolding = maximumAmountLimit;
        }

        double velocity = 0.4 + velocityPerItemAmount * amountOfPlayerIsHolding;

        cart.setMaxSpeed(velocity);
    }


}
