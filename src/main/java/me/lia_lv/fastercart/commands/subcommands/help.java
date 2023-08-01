package me.lia_lv.fastercart.commands.subcommands;

import me.lia_lv.fastercart.FasterCart;
import me.lia_lv.fastercart.commands.LiaCmd;
import me.lia_lv.fastercart.commands.Perms;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

public class help implements LiaCmd {

    @Override
    public String getPerm() {
        return Perms.FASTERCART_COMMAND_HELP.getPermission();
    }

    @Override
    public boolean execute(FasterCart plugin, CommandSender sender, @Nullable String[] args) {
        sender.sendMessage("help command executed.");
        return true;
    }
}
