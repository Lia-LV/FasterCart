package me.lia_lv.fastercart.commands;

import me.lia_lv.fastercart.FasterCart;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

public interface LiaCmd {

    String getPerm();

    boolean execute(FasterCart plugin, CommandSender sender, @Nullable String[] args);
}
