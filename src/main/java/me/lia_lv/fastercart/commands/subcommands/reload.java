package me.lia_lv.fastercart.commands.subcommands;

import me.lia_lv.fastercart.FasterCart;
import me.lia_lv.fastercart.commands.LiaCmd;
import me.lia_lv.fastercart.commands.Perms;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class reload implements LiaCmd {

    @Override
    public String getPerm() {
        return Perms.FASTERCART_COMMAND_ADMIN_RELOAD.getPermission();
    }

    @Override
    public HashMap<Integer, LinkedHashMap<String, String>> getArguments() {
        return null;
    }

    @Override
    public List<String> getDescriptionMsg() {
        return FasterCart.getInstance().getLocaleManager().getReloadCommandDescriptions();
    }

    @Override
    public boolean execute(FasterCart plugin, CommandSender sender, @Nullable String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String prefix = plugin.getConfigManager().getPrefix();

            if (plugin.loadConfig(true)) {
                p.sendMessage(prefix + plugin.getLocaleManager().getConfigFileReloaded());
                p.sendMessage(prefix + plugin.getLocaleManager().getLocaleFileReloaded());
            } else {
                p.sendMessage(prefix + plugin.getLocaleManager().getReloadError());
            }
        } else {
            plugin.loadConfig(true);
        }

        return true;
    }
}
