package me.lia_lv.fastercart.listener;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class VehicleExitListener implements Listener {

    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        if (!(event.getVehicle() instanceof Minecart) || !(event.getExited() instanceof Player)) {
            return;
        }

        Minecart cart = (Minecart) event.getVehicle();

        if (cart.getMaxSpeed() != 0.4) {
            cart.setMaxSpeed(0.4);
        }
    }


}
