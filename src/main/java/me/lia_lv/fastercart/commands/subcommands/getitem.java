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

        Material accelItemType = plugin.getConfigManager().getCartAccelerationItemType();
        XMaterial accelItemXMat = XMaterial.matchXMaterial(accelItemType);
        ItemStack accelItemStack;
        accelItemStack = accelItemXMat.parseItem();

        if (!plugin.getConfigManager().isCartAccelItemNameIgnore()) {
            ItemMeta meta = accelItemStack.getItemMeta();
            String accelItemName = plugin.getConfigManager().getCartAccelerationItemName();
            meta.setDisplayName(accelItemName);
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
            if (!sender.hasPermission(Perms.FASTERCART_COMMAND_GETITEM_OTHERS.getPermission())) {
                sender.sendMessage(plugin.getConfigManager().getPrefix() + plugin.getLocaleManager().getPermissionError());
                return true;
            }

            Player targetP = Bukkit.getPlayer(args[1]);
            if (targetP == null) {
                sender.sendMessage(plugin.getConfigManager().getPrefix() + plugin.getLocaleManager().getPlayerNotFoundError());
                return true;
            }
            targetP.getInventory().addItem(accelItemStack);
            String getItemOthersMsg = plugin.getLocaleManager().getGetitemToOthersMessage();
            HashMap<String, String> placeholderList = new HashMap<>();
            placeholderList.put("[amount_placeholder]", String.valueOf(amount));
            placeholderList.put("[player_placeholder]", targetP.getName());

            String receivedMsg = plugin.getLocaleManager().getGetitemReceivedFromOthersMessage();
            HashMap<String, String> placeholderListForTarget = new HashMap<>();
            placeholderListForTarget.put("[amount_placeholder]", String.valueOf(amount));
            if (sender instanceof ConsoleCommandSender) {
                placeholderListForTarget.put("[sender_placeholder]", "CONSOLE");
            } else if (sender instanceof Player) {
                placeholderListForTarget.put("[sender_placeholder]", sender.getName());
            }

            sender.sendMessage(plugin.getConfigManager().getPrefix() + LiaUtils.replacePlaceholders(getItemOthersMsg, placeholderList));
            targetP.sendMessage(plugin.getConfigManager().getPrefix() + LiaUtils.replacePlaceholders(receivedMsg, placeholderListForTarget));
        }

        return true;
    }


}
