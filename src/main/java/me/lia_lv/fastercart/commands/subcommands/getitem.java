package me.lia_lv.fastercart.commands.subcommands;

import com.cryptomorin.xseries.XMaterial;
import me.lia_lv.fastercart.FasterCart;
import me.lia_lv.fastercart.commands.LiaCmd;
import me.lia_lv.fastercart.commands.Perms;
import me.lia_lv.fastercart.utils.LiaUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class getitem implements LiaCmd {

    @Override
    public String getPerm() {
        return Perms.FASTERCART_COMMAND_GETITEM.getPermission();
    }

    @Override
    public HashMap<Integer, LinkedHashMap<String, String>> getArguments() {
        HashMap<Integer, LinkedHashMap<String, String>> argList = new HashMap<>();
        LinkedHashMap<String, String> firstArgs = new LinkedHashMap<>();
        firstArgs.put("<integer>", this.getPerm());
        argList.put(1, firstArgs);

        return argList;
    }

    @Override
    public List<String> getDescriptionMsg() {
        return FasterCart.getInstance().getLocaleManager().getGetitemCommandDescriptions();
    }

    @Override
    public boolean execute(FasterCart plugin, CommandSender sender, @Nullable String[] args) {
        if (args == null || args.length >= 3) {
            for (String str : this.getDescriptionMsg()) {
                sender.sendMessage(str);
            }
            return true;
        }

        String accelItemName = plugin.getConfigManager().getCartAccelerationItemName();
        Optional<XMaterial> accelItemXMat = XMaterial.matchXMaterial(accelItemName);
        ItemStack accelItemStack;
        if (accelItemXMat.isPresent() && accelItemXMat.get().parseMaterial() != null) {
           accelItemStack = new ItemStack(accelItemXMat.get().parseMaterial());
        } else {
            accelItemStack = new ItemStack(Material.LEVER);
        }
        if (!plugin.getConfigManager().isCartAccelItemNameIgnore()) {
            ItemMeta meta = accelItemStack.getItemMeta();
            meta.setDisplayName(plugin.getConfigManager().getCartAccelerationItemName());
            accelItemStack.setItemMeta(meta);
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
            if (amount > 64) {
                String invalidAmountErrorMsg = plugin.getLocaleManager().getInvalidAmountError();
                sender.sendMessage(plugin.getConfigManager().getPrefix() + LiaUtils.replacePlaceholders(invalidAmountErrorMsg, "[amount_placeholder]", "64"));
                return true;
            }
        } catch (NumberFormatException ex) {
            sender.sendMessage(plugin.getConfigManager().getPrefix() + plugin.getLocaleManager().getInvalidArgumentError());
            return true;
        }

        if (args.length == 1) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(plugin.getLocaleManager().getConsoleNoSelectTargetError());
                return true;
            }
            Player p = (Player) sender;
            p.getInventory().addItem(accelItemStack);
            String getItemMsg = plugin.getLocaleManager().getGetItemMessage();
            p.sendMessage(plugin.getConfigManager().getPrefix() + LiaUtils.replacePlaceholders(getItemMsg, "[amount_placeholder]", String.valueOf(amount)));
        } else {
            Player targetP = Bukkit.getPlayer(args[1]);
            if (targetP == null) {
                sender.sendMessage(plugin.getConfigManager().getPrefix() + plugin.getLocaleManager().getPlayerNotFoundError());
                return true;
            }
            targetP.getInventory().addItem(accelItemStack);
            String getItemOthersMsg = plugin.getLocaleManager().getGetitemOthersMessage();
            HashMap<String, String> placeholderList = new HashMap<>();
            placeholderList.put("[amount_placeholder]", String.valueOf(amount));
            placeholderList.put("[player_placeholder]", targetP.getName());
            targetP.sendMessage(plugin.getConfigManager().getPrefix() + LiaUtils.replacePlaceholders(getItemOthersMsg, placeholderList));
        }

        return true;
    }


}
